package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.SystemSettingStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("SystemSetting")
public class SystemSetting extends Setting {

    private int version;

    @Enumerated(EnumType.STRING)
    private SystemSettingStatus status;

    public SystemSettingStatus getStatus() {
        return status;
    }

    public void setStatus(SystemSettingStatus status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
