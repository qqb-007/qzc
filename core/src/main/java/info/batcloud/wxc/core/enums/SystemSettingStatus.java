package info.batcloud.wxc.core.enums;

public enum SystemSettingStatus {

    ACTIVE("激活"), LOCKED("锁定");

    public String title;

    SystemSettingStatus(String title) {
        this.title = title;
    }

}
