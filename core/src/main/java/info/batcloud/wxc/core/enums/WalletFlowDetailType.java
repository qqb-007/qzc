package info.batcloud.wxc.core.enums;


import info.batcloud.wxc.core.context.StaticContext;

public enum WalletFlowDetailType {

    WITHDRAW, MERGE_ORDER_SETTLE, MERGE_ORDER_SETTLE_DEDUCT, WITHDRAW_REJECT_RETURN;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }

}
