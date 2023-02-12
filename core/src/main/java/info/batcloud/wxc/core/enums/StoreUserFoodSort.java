package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum StoreUserFoodSort {

    ID_DESC, QUOTE_PRICE_DESC, QUOTE_PRICE_ASC;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }


}
