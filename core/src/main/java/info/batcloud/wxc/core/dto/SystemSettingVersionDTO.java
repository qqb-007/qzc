package info.batcloud.wxc.core.dto;

import info.batcloud.wxc.core.enums.SystemSettingStatus;

public class SystemSettingVersionDTO {

    private int version;
    private SystemSettingStatus status;

    public String getStatusTitle() {
        return status == null ? null : status.title;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public SystemSettingStatus getStatus() {
        return status;
    }

    public void setStatus(SystemSettingStatus status) {
        this.status = status;
    }
}
