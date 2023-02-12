package info.batcloud.wxc.merchant.api.controller;

import com.alipay.api.internal.util.codec.Base64;
import com.aliyun.oss.common.auth.ServiceSignature;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import info.batcloud.wxc.core.aliyun.AccessKey;
import info.batcloud.wxc.core.aliyun.OSSConfig;
import info.batcloud.wxc.core.aliyun.Ram;
import info.batcloud.wxc.core.aliyun.Role;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.OSSImageHelper;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.helper.StoreUserOssHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/aliyun/sts")
@PreAuthorize("isAuthenticated()")
public class AliyunStsController {

    @Inject
    private Ram ram;

    @Inject
    private OSSConfig ossConfig;

    @GetMapping("/acs")
    public Object acs(@RequestParam(required = false, defaultValue = "") String filename) {
        try {
            // Init ACS Client
            DefaultProfile.addEndpoint("", "", "Sts", "sts.aliyuncs.com");
            AccessKey accessKey = ram.getUsers().getAliyunManager().getAccessKey();
            IClientProfile profile = DefaultProfile.getProfile("", accessKey.getId(), accessKey.getSecret());
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            Role role = ram.getRoles().getAliyunFullAccessingRole();
            request.setRoleArn(role.getArn());
            request.setRoleSessionName("session-name");
            AssumeRoleResponse response = client.getAcsResponse(request);
            Map<String, Object> rs = new HashMap<>();
            String objectKey = UUID.randomUUID().toString();
            String userPrefix = StoreUserOssHelper.prefix(SecurityHelper.loginStoreUserId());
            String fileSuffix = filename.substring(filename.lastIndexOf("."));
            String objectName = userPrefix + objectKey + fileSuffix;
            //http://oss-example.oss-cn-hangzhou.aliyuncs.com/oss-api.pdf?OSSAccessKeyId=nz2pc56s936**9l&Expires=1141889120&Signature=vjbyPxybdZaNmGa%2ByT272YEAiv4%3D
            String uploadUrl = "http://%s";
            Map<String, String> formFields = new LinkedHashMap<>();
            // 设置文件名称。
            formFields.put("key", objectName);
            // 设置OSSAccessKeyId。
            formFields.put("OSSAccessKeyId", accessKey.getId());
            String policy = "{\"expiration\": \"2120-01-01T12:00:00.000Z\",\"conditions\": [[\"content-length-range\", 0, 104857600104857600]]}";
            String encodePolicy = new String(Base64.encodeBase64(policy.getBytes()));
            // 设置policy。
            formFields.put("policy", encodePolicy);
            // 生成签名。
            String signature = ServiceSignature.create().computeSignature(accessKey.getSecret(), encodePolicy);
            // 设置签名。
            formFields.put("Signature", signature);
            rs.put("formData", formFields);
            rs.put("objectUrl", OSSImageHelper.toMediumUrl(objectName));
            rs.put("objectKey", objectName);
            rs.put("uploadUrl", "https://" + ossConfig.getBucket() + "." + ossConfig.getEndpoint());
            return BusinessResponse.ok(rs);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

}
