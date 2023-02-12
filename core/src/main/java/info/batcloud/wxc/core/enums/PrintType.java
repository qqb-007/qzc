package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum PrintType implements EnumTitle{

    ORDER;

    @Override
    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }

}