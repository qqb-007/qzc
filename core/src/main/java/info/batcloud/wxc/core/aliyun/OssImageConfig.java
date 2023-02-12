package info.batcloud.wxc.core.aliyun;

public class OssImageConfig {

    private static OssImageConfig instance = new OssImageConfig();

    private OssImageConfig() {

    }

    public static OssImageConfig getInstance() {
        return instance;
    }

    private String smallStyle;

    private String tinyStyle;

    private String largeStyle;

    private String mediumStyle;

    public String getSmallStyle() {
        return smallStyle;
    }

    public void setSmallStyle(String smallStyle) {
        this.smallStyle = smallStyle;
    }

    public String getTinyStyle() {
        return tinyStyle;
    }

    public void setTinyStyle(String tinyStyle) {
        this.tinyStyle = tinyStyle;
    }

    public String getLargeStyle() {
        return largeStyle;
    }

    public void setLargeStyle(String largeStyle) {
        this.largeStyle = largeStyle;
    }

    public String getMediumStyle() {
        return mediumStyle;
    }

    public void setMediumStyle(String mediumStyle) {
        this.mediumStyle = mediumStyle;
    }
}
