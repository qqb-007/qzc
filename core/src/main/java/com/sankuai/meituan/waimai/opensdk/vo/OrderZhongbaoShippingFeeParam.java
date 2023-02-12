package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by gaokeming on 16/9/12.
 */
public class OrderZhongbaoShippingFeeParam {
    private Long wm_order_id;
    private Double shipping_fee;
    private Long wm_poi_id;
    private Long wm_order_view_id;
    private String shipping_tips;

    public Long getWm_order_id() {
        return wm_order_id;
    }

    public OrderZhongbaoShippingFeeParam setWm_order_id(Long wm_order_id) {
        this.wm_order_id = wm_order_id;
        return this;
    }

    public Double getShipping_fee() {
        return shipping_fee;
    }

    public OrderZhongbaoShippingFeeParam setShipping_fee(Double shipping_fee) {
        this.shipping_fee = shipping_fee;
        return this;
    }

    public Long getWm_poi_id() {
        return wm_poi_id;
    }

    public OrderZhongbaoShippingFeeParam setWm_poi_id(Long wm_poi_id) {
        this.wm_poi_id = wm_poi_id;
        return this;
    }

    public Long getWm_order_view_id() {
        return wm_order_view_id;
    }

    public OrderZhongbaoShippingFeeParam setWm_order_view_id(Long wm_order_view_id) {
        this.wm_order_view_id = wm_order_view_id;
        return this;
    }

    public String getShipping_tips() {
        return shipping_tips;
    }

    public OrderZhongbaoShippingFeeParam setShipping_tips(String shipping_tips) {
        this.shipping_tips = shipping_tips;
        return this;
    }

    @Override
    public String toString() {
        return "OrderZhongbaoShippingFeeParam [" +
                "wm_order_id=" + wm_order_id +
                ", shipping_fee=" + shipping_fee +
                ", wm_poi_id=" + wm_poi_id +
                ", wm_order_view_id=" + wm_order_view_id +
                ", shipping_tips='" + shipping_tips + '\'' +
                ']';
    }
}
