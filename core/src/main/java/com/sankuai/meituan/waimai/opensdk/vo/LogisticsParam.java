package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangzhidong on 15/10/29.
 */
public class LogisticsParam {
    private String result;
    private String order_id;
    private String logistics_status;
    private String logistics_name;
    private String send_time;
    private String confirm_time;
    private String cancel_time;
    private String fetch_time;
    private String completed_time;
    private String dispatcher_name;
    private String dispatcher_mobile;
    private Double poi_shipping_fee; // 商家承担运费
    private String shipping_tips; // 商家承担运费的具体说明
    private Double tip_amount; // 商家给定的小费金额

    public String getResult() {
        return result;
    }

    public LogisticsParam setResult(String result) {
        this.result = result;
        return this;
    }

    public String getOrder_id() {
        return order_id;
    }

    public LogisticsParam setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }

    public String getLogistics_status() {
        return logistics_status;
    }

    public LogisticsParam setLogistics_status(String logistics_status) {
        this.logistics_status = logistics_status;
        return this;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public LogisticsParam setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
        return this;
    }

    public String getSend_time() {
        return send_time;
    }

    public LogisticsParam setSend_time(String send_time) {
        this.send_time = send_time;
        return this;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public LogisticsParam setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
        return this;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public LogisticsParam setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
        return this;
    }

    public String getFetch_time() {
        return fetch_time;
    }

    public LogisticsParam setFetch_time(String fetch_time) {
        this.fetch_time = fetch_time;
        return this;
    }

    public String getCompleted_time() {
        return completed_time;
    }

    public LogisticsParam setCompleted_time(String completed_time) {
        this.completed_time = completed_time;
        return this;
    }

    public String getDispatcher_name() {
        return dispatcher_name;
    }

    public LogisticsParam setDispatcher_name(String dispatcher_name) {
        this.dispatcher_name = dispatcher_name;
        return this;
    }

    public String getDispatcher_mobile() {
        return dispatcher_mobile;
    }

    public LogisticsParam setDispatcher_mobile(String dispatcher_mobile) {
        this.dispatcher_mobile = dispatcher_mobile;
        return this;
    }

    public Double getPoi_shipping_fee() {
        return poi_shipping_fee;
    }

    public LogisticsParam setPoi_shipping_fee(Double poi_shipping_fee) {
        this.poi_shipping_fee = poi_shipping_fee;
        return this;
    }

    public String getShipping_tips() {
        return shipping_tips;
    }

    public LogisticsParam setShipping_tips(String shipping_tips) {
        this.shipping_tips = shipping_tips;
        return this;
    }

    public Double getTip_amount() {
        return tip_amount;
    }

    public LogisticsParam setTip_amount(Double tip_amount) {
        this.tip_amount = tip_amount;
        return this;
    }

    @Override
    public String toString() {
        return "LogisticsParam [" +
                "result='" + result + '\'' +
                ", order_id='" + order_id + '\'' +
                ", logistics_status='" + logistics_status + '\'' +
                ", logistics_name='" + logistics_name + '\'' +
                ", send_time='" + send_time + '\'' +
                ", confirm_time='" + confirm_time + '\'' +
                ", cancel_time='" + cancel_time + '\'' +
                ", fetch_time='" + fetch_time + '\'' +
                ", completed_time='" + completed_time + '\'' +
                ", dispatcher_name='" + dispatcher_name + '\'' +
                ", dispatcher_mobile='" + dispatcher_mobile + '\'' +
                ", poi_shipping_fee=" + poi_shipping_fee +
                ", shipping_tips='" + shipping_tips + '\'' +
                ", tip_amount=" + tip_amount +
                ']';
    }
}
