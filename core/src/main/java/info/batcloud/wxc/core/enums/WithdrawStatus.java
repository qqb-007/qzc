package info.batcloud.wxc.core.enums;


import info.batcloud.wxc.core.context.StaticContext;

public enum WithdrawStatus implements EnumTitle {

    WAIT_VERIFY, WAIT_TRANSFER, RETRY_TRANSFER,
    ON_TRANSFER, VERIFY_FAIL,
    TRANSFER_SUCCESS,
    TRANSFER_FAIL;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }

}
