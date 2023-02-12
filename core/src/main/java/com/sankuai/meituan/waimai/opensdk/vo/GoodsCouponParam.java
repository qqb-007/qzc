package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

public class GoodsCouponParam {
    private String app_poi_codes;
    private String coupon_name;
    private Integer is_single_poi;
    private String app_spu_codes;
    private Integer take_coupon_start_time;
    private Integer take_coupon_end_time;
    private Integer use_coupon_start_time;
    private Integer coupon_limit_count;
    private Integer type;
    private List<PriceCouponInfo> act_price_coupon_info;

    public String getApp_poi_codes() {
        return app_poi_codes;
    }

    public void setApp_poi_codes(String app_poi_codes) {
        this.app_poi_codes = app_poi_codes;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public Integer getIs_single_poi() {
        return is_single_poi;
    }

    public void setIs_single_poi(Integer is_single_poi) {
        this.is_single_poi = is_single_poi;
    }

    public String getApp_spu_codes() {
        return app_spu_codes;
    }

    public void setApp_spu_codes(String app_spu_codes) {
        this.app_spu_codes = app_spu_codes;
    }

    public Integer getTake_coupon_start_time() {
        return take_coupon_start_time;
    }

    public void setTake_coupon_start_time(Integer take_coupon_start_time) {
        this.take_coupon_start_time = take_coupon_start_time;
    }

    public Integer getTake_coupon_end_time() {
        return take_coupon_end_time;
    }

    public void setTake_coupon_end_time(Integer take_coupon_end_time) {
        this.take_coupon_end_time = take_coupon_end_time;
    }

    public Integer getUse_coupon_start_time() {
        return use_coupon_start_time;
    }

    public void setUse_coupon_start_time(Integer use_coupon_start_time) {
        this.use_coupon_start_time = use_coupon_start_time;
    }

    public Integer getCoupon_limit_count() {
        return coupon_limit_count;
    }

    public void setCoupon_limit_count(Integer coupon_limit_count) {
        this.coupon_limit_count = coupon_limit_count;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<PriceCouponInfo> getAct_price_coupon_info() {
        return act_price_coupon_info;
    }

    public void setAct_price_coupon_info(List<PriceCouponInfo> act_price_coupon_info) {
        this.act_price_coupon_info = act_price_coupon_info;
    }
}
