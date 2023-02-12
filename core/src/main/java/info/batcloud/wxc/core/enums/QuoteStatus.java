package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum QuoteStatus {

    WAIT_VERIFY, VERIFY_FAIL, VERIFY_SUCCESS;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }


}
