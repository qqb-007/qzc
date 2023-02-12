package info.batcloud.wxc.core.config;

public class MeituanPeisongApp {

    private String version;
    private String appKey;
    private String secret;
    private int deliveryServiceCode;

    public int getDeliveryServiceCode() {
        return deliveryServiceCode;
    }

    public void setDeliveryServiceCode(int deliveryServiceCode) {
        this.deliveryServiceCode = deliveryServiceCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
