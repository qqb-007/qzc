package info.batcloud.wxc.core.settings;

import info.batcloud.wxc.core.settings.annotation.Single;

import java.io.Serializable;

@Single
public class WithdrawSetting implements Serializable {

    private boolean openWithdraw;

    private float minWithdrawValue; //最小提现金额

    private boolean autoVerify; //自动转账

    public boolean isOpenWithdraw() {
        return openWithdraw;
    }

    public void setOpenWithdraw(boolean openWithdraw) {
        this.openWithdraw = openWithdraw;
    }

    public float getMinWithdrawValue() {
        return minWithdrawValue;
    }

    public void setMinWithdrawValue(float minWithdrawValue) {
        this.minWithdrawValue = minWithdrawValue;
    }

    public boolean isAutoVerify() {
        return autoVerify;
    }

    public void setAutoVerify(boolean autoVerify) {
        this.autoVerify = autoVerify;
    }
}
