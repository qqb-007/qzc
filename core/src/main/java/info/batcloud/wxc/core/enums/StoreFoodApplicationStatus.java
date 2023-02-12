package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum StoreFoodApplicationStatus implements EnumTitle{

    WAIT_VERIFY, FINISHED, DELETED;

    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }

}
