package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum SettlementSheetDetailStatus {

    WAIT_CHECK, WAIT_VERIFY, WAIT_SETTLE, SETTLED;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }


}
