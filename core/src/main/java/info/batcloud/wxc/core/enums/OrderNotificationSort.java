package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum OrderNotificationSort implements EnumTitle {

    ID_DESC, ID_ASC;

    @Override
    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }
}
