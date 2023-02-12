package info.batcloud.wxc.core.domain;

import java.io.Serializable;

public class OrderExtra implements Serializable {

    private String remark;
    private float reduceFee;
    private float platCharge;
    private float storeCharge;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public float getReduceFee() {
        return reduceFee;
    }

    public void setReduceFee(float reduceFee) {
        this.reduceFee = reduceFee;
    }

    public float getPlatCharge() {
        return platCharge;
    }

    public void setPlatCharge(float platCharge) {
        this.platCharge = platCharge;
    }

    public float getStoreCharge() {
        return storeCharge;
    }

    public void setStoreCharge(float storeCharge) {
        this.storeCharge = storeCharge;
    }
}
