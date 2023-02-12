package info.batcloud.wxc.core.domain;

import info.batcloud.wxc.core.enums.TimeInfoType;

public class TimeInfo {

    private int value;
    private TimeInfoType type;


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TimeInfoType getType() {
        return type;
    }

    public String getTypeTitle() {
        return getType() == null ? null : getType().getTitle();
    }

    public void setType(TimeInfoType type) {
        this.type = type;
    }

}
