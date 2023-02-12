package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

public class ActCouponListParam {
    private Long actId;
    private String coupon_name;
    private Integer type;
    private List<ActPriceCouponInfo> act_price_coupon_info;

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<ActPriceCouponInfo> getAct_price_coupon_info() {
        return act_price_coupon_info;
    }

    public void setAct_price_coupon_info(List<ActPriceCouponInfo> act_price_coupon_info) {
        this.act_price_coupon_info = act_price_coupon_info;
    }
}
