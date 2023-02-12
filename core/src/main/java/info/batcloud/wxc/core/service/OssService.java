package info.batcloud.wxc.core.service;

import com.aliyun.oss.model.CompleteMultipartUploadResult;

import java.io.File;

public interface OssService {

    String getObjectName(String key);

    CompleteMultipartUploadResult uploadFile(File file, String key) throws Throwable;
}
