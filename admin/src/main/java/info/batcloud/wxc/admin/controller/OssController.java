package info.batcloud.wxc.admin.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectResult;
import info.batcloud.wxc.admin.controller.form.FolderCreateForm;
import info.batcloud.wxc.admin.controller.form.OssListForm;
import info.batcloud.wxc.core.aliyun.OSSConfig;
import info.batcloud.wxc.core.domain.aliyun.OssFile;
import info.batcloud.wxc.core.domain.aliyun.OssFileType;
import info.batcloud.wxc.core.helper.OssHelper;
import info.batcloud.wxc.core.service.OssService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/oss")
public class OssController {

    @Inject
    private OSSClient ossClient;

    @Inject
    private OSSConfig ossConfig;

    @Inject
    private OssService ossService;

    @GetMapping("/list")
    public Object list(OssListForm request) {
        request.setBucketName(ossConfig.getBucket());
        request.setDelimiter("/");
        request.setMaxKeys(999);
        ObjectListing listing = ossClient.listObjects(request);
        Map<String, Object> model = new HashMap<>();
        List<OssFile> results = new ArrayList<>();
        for (String prefix : listing.getCommonPrefixes()) {
            OssFile of = new OssFile();
            of.setDomain(ossConfig.getDomain());
            of.setType(OssFileType.DIRECTORY);
            of.setKey(prefix);
            results.add(of);
        }
        if (!request.isOnlyDirectory()) {
            for (OSSObjectSummary obj : listing.getObjectSummaries()) {
                if (obj.getKey().equals(request.getPrefix())) {
                    continue;
                }
                OssFile of = new OssFile();
                of.setDomain(ossConfig.getDomain());
                of.setType(OssHelper.determineType(obj.getKey()));
                of.setKey(obj.getKey());
                of.setEtag(obj.getETag());
                of.setSize(obj.getSize());
                String name = ossService.getObjectName(obj.getKey());
                if(name != null) {
                    of.setName(name);
                }
                results.add(of);
            }
        }
        model.put("results", results);
        model.put("nextMarker", listing.getNextMarker());
        model.put("prefix", listing.getPrefix());
        return model;
    }

    @PostMapping("/folder")
//    @Permission(ManagerPermissions.OSS_MANAGE)
    public Object createFolder(FolderCreateForm form) throws UnsupportedEncodingException {
        String path = form.getParent() + "/" + URLEncoder.encode(form.getName(), "utf8");
        path = path.replaceAll("\\/+", "/");
        path = path.endsWith("/") ? path : path + "/";
        if(path.startsWith("/")) {
            path = path.substring(1);
        }
        PutObjectResult rs = ossClient.putObject(ossConfig.getBucket(), path, new ByteArrayInputStream(new byte[0]));
        return 1;
    }

    @PutMapping("/del")
//    @Permission(ManagerPermissions.OSS_MANAGE)
    public Object delete(ObjectDeleteForm form) {
        for (String s : form.getKeys()) {
            ossClient.deleteObject(ossConfig.getBucket(), s);
        }
        return 1;
    }

    public static class ObjectDeleteForm {
        private List<String> keys;

        public List<String> getKeys() {
            return keys;
        }

        public void setKeys(List<String> keys) {
            this.keys = keys;
        }
    }

}
