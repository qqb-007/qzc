package com.sankuai.meituan.banma.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单id信息
 */
public class OrderIdInfo {
    /**
     * 配送唯一标识
     */
    @JSONField(name = "mt_peisong_id")
    private String mtPeisongId;
    /**
     * 订单ID
     */
    @JSONField(name = "order_id")
    private String orderId;
    /**
     * 配送活动标识
     */
    @JSONField(name = "delivery_id")
    private long deliveryId;
    /**
     * 目的地id
     */
    @JSONField(name = "destination_id")
    private String destinationId;
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

    @JSONField(name = "pay_amount")
    private Double payAmount;
    /**
     * 路区信息
     */
    @JSONField(name = "road_area")
    private String roadArea;

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getMtPeisongId() {
        return mtPeisongId;
    }

    public void setMtPeisongId(String mtPeisongId) {
        this.mtPeisongId = mtPeisongId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

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

    public String getRoadArea() {
        return roadArea;
    }

    public void setRoadArea(String roadArea) {
        this.roadArea = roadArea;
    }
}
