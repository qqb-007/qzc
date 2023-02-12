package info.batcloud.wxc.core.aliyun;

public class OpenSearchConfig {

    private String app;
    private HostType hostType;
    private String host;
    private String intranetHost;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public HostType getHostType() {
        return hostType;
    }

    public void setHostType(HostType hostType) {
        this.hostType = hostType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIntranetHost() {
        return intranetHost;
    }

    public void setIntranetHost(String intranetHost) {
        this.intranetHost = intranetHost;
    }

    public enum HostType {
        internet, intranet;
    }
}
