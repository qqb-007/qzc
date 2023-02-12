package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum BagStatus implements EnumTitle {
    WAIT_VERIFY, WAIT_DELIVERY, SHIPPED, REJECT;

    @Override
    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }
}
