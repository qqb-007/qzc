package info.batcloud.wxc.core.enums;

public enum CacheKeys {

    SMS_PHONE,
    SMS_PHONE_CODE,
    ;

    public String name(String value) {
        return this.name() + "_" + value;
    }
}

