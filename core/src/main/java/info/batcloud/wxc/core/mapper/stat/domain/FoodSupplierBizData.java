package info.batcloud.wxc.core.mapper.stat.domain;

public class FoodSupplierBizData {

    private Float totalMoney;
    private Float refundMoney;
    private Float remainMoney;
    private int totalNum;
    /**
     * 有效单数
     */
    private int validOrderNum;

    private int invalidOrderNum;

    private float perFee;

    public Float getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(Float remainMoney) {
        this.remainMoney = remainMoney;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(int validOrderNum) {
        this.validOrderNum = validOrderNum;
    }

    public int getInvalidOrderNum() {
        return invalidOrderNum;
    }

    public void setInvalidOrderNum(int invalidOrderNum) {
        this.invalidOrderNum = invalidOrderNum;
    }

    public float getPerFee() {
        return perFee;
    }

    public void setPerFee(float perFee) {
        this.perFee = perFee;
    }
}
