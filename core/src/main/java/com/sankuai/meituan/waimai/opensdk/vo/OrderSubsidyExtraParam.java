package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class OrderSubsidyExtraParam {
    private String reduce_fee;
    private String remark;
    private Integer type;
    private Integer act_detail_id; // 活动id
    private Float mt_charge;
    private Float poi_charge;

    public String getReduce_fee() {
        return reduce_fee;
    }

    public OrderSubsidyExtraParam setReduce_fee(String reduce_fee) {
        this.reduce_fee = reduce_fee;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public OrderSubsidyExtraParam setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public OrderSubsidyExtraParam setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getAct_detail_id() {
        return act_detail_id;
    }

    public OrderSubsidyExtraParam setAct_detail_id(Integer act_detail_id) {
        this.act_detail_id = act_detail_id;
        return this;
    }

    public Float getMt_charge() {
        return mt_charge;
    }

    public OrderSubsidyExtraParam setMt_charge(Float mt_charge) {
        this.mt_charge = mt_charge;
        return this;
    }

    public Float getPoi_charge() {
        return poi_charge;
    }

    public OrderSubsidyExtraParam setPoi_charge(Float poi_charge) {
        this.poi_charge = poi_charge;
        return this;
    }

    @Override
    public String toString() {
        return "OrderSubsidyExtraParam [" +
                "reduce_fee='" + reduce_fee + '\'' +
                ", remark='" + remark + '\'' +
                ", type=" + type +
                ", act_detail_id=" + act_detail_id +
                ", mt_charge=" + mt_charge +
                ", poi_charge=" + poi_charge +
                ']';
    }
}
