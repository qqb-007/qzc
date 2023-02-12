package info.batcloud.wxc.core.enums;

import info.batcloud.wxc.core.context.StaticContext;

public enum DeliveryType implements EnumTitle {

    MEITUAN_OPEN, PLATFORM, SELF, SHUFENG_OPEN, UU_OPEN, ZHONGBAO, SS_OPEN, UNDETERMINED, DD_OPEN;

    @Override
    public String getTitle() {
        return StaticContext.messageSource.getMessage(this.getClass().getSimpleName() + "." + this.name(), null, null);
    }
}
