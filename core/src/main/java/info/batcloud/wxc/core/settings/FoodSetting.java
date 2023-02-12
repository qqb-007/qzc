package info.batcloud.wxc.core.settings;

import info.batcloud.wxc.core.settings.annotation.Single;

import java.io.Serializable;

@Single
public class FoodSetting implements Serializable {

    /**
     * 默认加价百分比
     * */
    private Float perIncrease;

    private float boxNum;

    private float boxPrice;

    public float getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(float boxNum) {
        this.boxNum = boxNum;
    }

    public float getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(float boxPrice) {
        this.boxPrice = boxPrice;
    }

    public Float getPerIncrease() {
        return perIncrease;
    }

    public void setPerIncrease(Float perIncrease) {
        this.perIncrease = perIncrease;
    }
}
