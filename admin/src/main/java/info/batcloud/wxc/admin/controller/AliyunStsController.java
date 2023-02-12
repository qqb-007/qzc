package info.batcloud.wxc.admin.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/aliyun/sts")
public class AliyunStsController {

    @Inject
    private Ram ram;

    @Inject
    private OSSConfig ossConfig;

    @GetMapping("/acs")
    public Object acs(HttpServletRequest req) {
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
//            request.setPolicy(role.getPolicy()); // Optional
            AssumeRoleResponse response = client.getAcsResponse(request);
            Map<String, Object> rs = new HashMap<>();
            rs.put("assumeRole", response);
            rs.put("ossConfig", ossConfig);
            rs.put("ossObjectKey", UUID.randomUUID().toString());
            return rs;
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

}
