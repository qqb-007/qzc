package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by songxu on 2017/08/07
 */
public class OrderPartRefundParam {
    private String app_food_code;
    private Double box_num;
    private Double box_price;
    private Integer count;
    private String food_name;
    private Double food_price;
    private Double origin_food_price;
    private Double refund_price;
    private String sku_id;
    private String spec;
    private String pictures;
    private Double refunded_weight;

    public Double getRefunded_weight() {
        return refunded_weight;
    }

    public void setRefunded_weight(Double refunded_weight) {
        this.refunded_weight = refunded_weight;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getApp_food_code() {
        return app_food_code;
    }

    public OrderPartRefundParam setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
        return this;
    }

    public Double getBox_num() {
        return box_num;
    }

    public OrderPartRefundParam setBox_num(Double box_num) {
        this.box_num = box_num;
        return this;
    }

    public Double getBox_price() {
        return box_price;
    }

    public OrderPartRefundParam setBox_price(Double box_price) {
        this.box_price = box_price;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public OrderPartRefundParam setCount(Integer count) {
        this.count = count;
        return this;
    }

    public String getFood_name() {
        return food_name;
    }

    public OrderPartRefundParam setFood_name(String food_name) {
        this.food_name = food_name;
        return this;
    }

    public Double getFood_price() {
        return food_price;
    }

    public OrderPartRefundParam setFood_price(Double food_price) {
        this.food_price = food_price;
        return this;
    }

    public Double getOrigin_food_price() {
        return origin_food_price;
    }

    public OrderPartRefundParam setOrigin_food_price(Double origin_food_price) {
        this.origin_food_price = origin_food_price;
        return this;
    }

    public Double getRefund_price() {
        return refund_price;
    }

    public OrderPartRefundParam setRefund_price(Double refund_price) {
        this.refund_price = refund_price;
        return this;
    }

    public String getSku_id() {
        return sku_id;
    }

    public OrderPartRefundParam setSku_id(String sku_id) {
        this.sku_id = sku_id;
        return this;
    }

    public String getSpec() {
        return spec;
    }

    public OrderPartRefundParam setSpec(String spec) {
        this.spec = spec;
        return this;
    }

    @Override
    public String toString() {
        return "FoodPartRefundParam [" +
                "app_food_code='" + app_food_code + '\'' +
                ", box_num=" + box_num +
                ", box_price=" + box_price +
                ", count=" + count +
                ", food_name='" + food_name + '\'' +
                ", food_price=" + food_price +
                ", origin_food_price=" + origin_food_price +
                ", refund_price=" + refund_price +
                ", sku_id='" + sku_id + '\'' +
                ", spec='" + spec + '\'' +
                ']';
    }
}
