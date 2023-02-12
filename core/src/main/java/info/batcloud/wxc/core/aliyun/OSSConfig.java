package info.batcloud.wxc.core.aliyun;

public class OSSConfig {

    private static OSSConfig instance = new OSSConfig();

    private OSSConfig() {

    }

    public static OSSConfig getInstance() {
        return instance;
    }

    private String regionId;

    private String endpoint;
    private String bucket;
    private String sharePicFolder;
    private String domain;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSharePicFolder() {
        return sharePicFolder;
    }

    public void setSharePicFolder(String sharePicFolder) {
        this.sharePicFolder = sharePicFolder;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
