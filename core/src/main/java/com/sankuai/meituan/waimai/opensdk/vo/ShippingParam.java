package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by yangzhiqi on 15/10/15.
 */
public class ShippingParam {
    private String app_shipping_code;
    private Integer type;
    private String area;
    private Double min_price;
    private Double shipping_fee;
    /** <a href="http://developer.waimai.meituan.com/doc/show#14.5.2">配送方式code</a> */
    private String logistics_code;

    public String getApp_shipping_code() {
        return app_shipping_code;
    }

    public ShippingParam setApp_shipping_code(String app_shipping_code) {
        this.app_shipping_code = app_shipping_code;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public ShippingParam setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getArea() {
        return area;
    }

    public ShippingParam setArea(String area) {
        this.area = area;
        return this;
    }

    public Double getMin_price() {
        return min_price;
    }

    public ShippingParam setMin_price(Double min_price) {
        this.min_price = min_price;
        return this;
    }

    public Double getShipping_fee() {
        return shipping_fee;
    }

    public ShippingParam setShipping_fee(Double shipping_fee) {
        this.shipping_fee = shipping_fee;
        return this;
    }

    public String getLogistics_code() {
        return logistics_code;
    }

    public ShippingParam setLogistics_code(String logistics_code) {
        this.logistics_code = logistics_code;
        return this;
    }

    @Override
    public String toString() {
        return "ShippingParam [" +
                "app_shipping_code='" + app_shipping_code + '\'' +
                ", type=" + type +
                ", area='" + area + '\'' +
                ", min_price=" + min_price +
                ", shipping_fee=" + shipping_fee +
                ", logistics_code='" + logistics_code + '\'' +
                ']';
    }
}
