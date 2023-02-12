package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum OrderNotificationType implements EnumTitle {

    ORDER, CANCEL, FINISH, CONFIRM, REFUND, PART_REFUND, UU;

    @Override
    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }
}
