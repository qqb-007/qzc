package info.batcloud.wxc.core.settings;

import info.batcloud.wxc.core.settings.annotation.Single;

import java.io.Serializable;

@Single
public class SettlementSetting implements Serializable {

    /**
     * 结算周期T+N
     * */
    private int intervalDay;

    public int getIntervalDay() {
        return intervalDay;
    }

    public void setIntervalDay(int intervalDay) {
        this.intervalDay = intervalDay;
    }
}
