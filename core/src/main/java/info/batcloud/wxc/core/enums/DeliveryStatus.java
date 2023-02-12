package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum DeliveryStatus implements EnumTitle {

    WAIT_DELIVERY, WAIT_SCHEDULE, ACCEPT, TAKEN, ARRIVED, CANCELED, OUTRANGE, TURNRUN;

    @Override
    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }
}
