package info.batcloud.wxc.core.domain.aliyun;

import info.batcloud.wxc.core.helper.OSSImageHelper;

public class OssFile {

    private String name;
    private String key;
    private OssFileType type;
    private long size;
    private String etag;

    private String domain;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
        String[] tmps = key.split("/");
        this.name = tmps[tmps.length - 1];
    }

    public String getUrl() {
        return OSSImageHelper.toUrl(getKey());
    }

    public OssFileType getType() {
        return type;
    }

    public void setType(OssFileType type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getLargeUrl() {
        return OSSImageHelper.toLargeUrl(getKey());
    }

    public String getMediumUrl() {
        return OSSImageHelper.toMediumUrl(getKey());
    }

    public String getSmallUrl() {
        return OSSImageHelper.toSmallUrl(getKey());
    }

    public String getTinyUrl() {
        return OSSImageHelper.toTinyUrl(getKey());
    }

}
