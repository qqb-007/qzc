package info.batcloud.wxc.merchant.api.constants;

public class CacheKeys {

    public static final String SESSION = "SESSION";

    public static String join(String ...keys) {
        return String.join(":", keys);
    }
}
