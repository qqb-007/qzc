package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum OrderStatus {

    PAID, WAIT_MERCHANT_CONFIRM, MERCHANT_CONFIRMED, CANCELED, SHIPPING, SHIPPED, FINISHED;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }


}
