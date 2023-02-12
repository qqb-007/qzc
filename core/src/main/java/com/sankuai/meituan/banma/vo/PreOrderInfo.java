package com.sankuai.meituan.banma.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class PreOrderInfo {
    /**
     * 订单配送距离
     */
    @JSONField(name = "delivery_distance")
    private Integer deliveryDistance;
    /**
     * 订单配送价格（面向商家）
     */
    @JSONField(name = "delivery_fee")
    private Double deliveryFee;


    public Integer getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(Integer deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}
