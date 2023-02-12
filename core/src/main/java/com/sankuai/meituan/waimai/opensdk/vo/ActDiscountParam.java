package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by yujun05 on 2017-12-29.
 */
public class ActDiscountParam {

    private String app_food_code;
    private Integer user_type;
    private Long start_time;
    private Long end_time;
    private Double act_price;
    private Integer order_limit;
    private Integer day_limit;
    private String period;
    private String weeks_time;
    private String name;
    private Double origin_price;
    private Integer stock;
    private Integer status;
    private Long item_id;

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Double getAct_price() {
        return act_price;
    }

    public void setAct_price(Double act_price) {
        this.act_price = act_price;
    }

    public Integer getOrder_limit() {
        return order_limit;
    }

    public void setOrder_limit(Integer order_limit) {
        this.order_limit = order_limit;
    }

    public Integer getDay_limit() {
        return day_limit;
    }

    public void setDay_limit(Integer day_limit) {
        this.day_limit = day_limit;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getWeeks_time() {
        return weeks_time;
    }

    public void setWeeks_time(String weeks_time) {
        this.weeks_time = weeks_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(Double origin_price) {
        this.origin_price = origin_price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ActDiscountParam{" +
                "app_food_code='" + app_food_code + '\'' +
                ", user_type=" + user_type +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", act_price=" + act_price +
                ", order_limit=" + order_limit +
                ", day_limit=" + day_limit +
                ", period='" + period + '\'' +
                ", weeks_time='" + weeks_time + '\'' +
                ", name='" + name + '\'' +
                ", origin_price=" + origin_price +
                ", stock=" + stock +
                ", status=" + status +
                '}';
    }

}