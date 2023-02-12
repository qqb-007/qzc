package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum TimeInfoType implements EnumTitle{
    YEAR,
    MONTH,
    DAY,
    HOUR,
    MIN,
    SEC;

    @Override
    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, "", null);
    }
}
