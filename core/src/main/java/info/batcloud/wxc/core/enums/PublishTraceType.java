package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum PublishTraceType {

    FOOD_QUOTE_SHEET_PUBLISH, FOOD_CODE_PUBLISH;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }

}
