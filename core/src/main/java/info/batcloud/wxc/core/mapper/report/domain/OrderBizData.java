package info.batcloud.wxc.core.mapper.report.domain;

import info.batcloud.wxc.core.enums.Plat;

public class OrderBizData {

    private Plat plat;
    private int totalNum;
    private Float total;
    private Float refundMoney;
    private Float costTotal;
    private Float costRefund;

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Float costTotal) {
        this.costTotal = costTotal;
    }

    public Float getCostRefund() {
        return costRefund;
    }

    public void setCostRefund(Float costRefund) {
        this.costRefund = costRefund;
    }
}
