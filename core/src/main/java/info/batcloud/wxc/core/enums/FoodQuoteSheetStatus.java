package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum FoodQuoteSheetStatus {

    WAIT_VERIFY, VERIFY_SUCCESS, VERIFY_FAIL, DELETED;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }

}
