package info.batcloud.wxc.core.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import info.batcloud.wxc.core.aliyun.OSSConfig;
import info.batcloud.wxc.core.constants.CacheNameConstants;
import info.batcloud.wxc.core.service.OssService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Service
@CacheConfig(cacheNames = CacheNameConstants.OSS)
public class OssServiceImpl implements OssService {

    @Inject
    private OSSClient ossClient;

    @Inject
    private OSSConfig ossConfig;

    @Cacheable(key = "'OBJECT_META_' + #key")
    public String getObjectName(String key) {
        ObjectMetadata objectMeta = ossClient.getObjectMetadata(ossConfig.getBucket(), key);
        if (objectMeta.getUserMetadata().containsKey("filename")) {
            try {
                return URLDecoder.decode(objectMeta.getUserMetadata().get("filename"), "utf8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public CompleteMultipartUploadResult uploadFile(File file, String key) throws Throwable {
        UploadFileRequest uploadFileRequest = new UploadFileRequest(ossConfig.getBucket(), key);
        // The local file to upload---it must exist.
        uploadFileRequest.setUploadFile(file.getAbsolutePath());
        // Sets the concurrent upload task number to 5.
        uploadFileRequest.setTaskNum(5);
        // Sets the part size to 1MB.
        uploadFileRequest.setPartSize(1024 * 1024 * 1);
        // Enables the checkpoint file. By default it's off.
        uploadFileRequest.setEnableCheckpoint(true);

        UploadFileResult uploadResult = ossClient.uploadFile(uploadFileRequest);

        CompleteMultipartUploadResult result =
                uploadResult.getMultipartUploadResult();
        return result;
    }
}
