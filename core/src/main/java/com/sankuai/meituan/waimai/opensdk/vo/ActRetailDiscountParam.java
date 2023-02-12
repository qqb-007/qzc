package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.Objects;

public class ActRetailDiscountParam {
    private String app_food_code;
    private Integer user_type;
    private Integer start_time;
    private Integer end_time;
    private Integer day_limit;
    private Integer order_limit;
    private String period;
    private String weeks_time;
    private Double act_price;
    private Double  discount_coefficient;
    private Integer setting_type;
    private Long item_id;
    private Double origin_price;

    public Double getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(Double origin_price) {
        this.origin_price = origin_price;
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

    public Integer getStart_time() {
        return start_time;
    }

    public void setStart_time(Integer start_time) {
        this.start_time = start_time;
    }

    public Integer getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Integer end_time) {
        this.end_time = end_time;
    }

    public Integer getDay_limit() {
        return day_limit;
    }

    public void setDay_limit(Integer day_limit) {
        this.day_limit = day_limit;
    }

    public Integer getOrder_limit() {
        return order_limit;
    }

    public void setOrder_limit(Integer order_limit) {
        this.order_limit = order_limit;
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

    public Double getAct_price() {
        return act_price;
    }

    public void setAct_price(Double act_price) {
        this.act_price = act_price;
    }

    public Double getDiscount_coefficient() {
        return discount_coefficient;
    }

    public void setDiscount_coefficient(Double discount_coefficient) {
        this.discount_coefficient = discount_coefficient;
    }

    public Integer getSetting_type() {
        return setting_type;
    }

    public void setSetting_type(Integer setting_type) {
        this.setting_type = setting_type;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActRetailDiscountParam that = (ActRetailDiscountParam) o;
        return Objects.equals(app_food_code, that.app_food_code) &&
                Objects.equals(user_type, that.user_type) &&
                Objects.equals(start_time, that.start_time) &&
                Objects.equals(end_time, that.end_time) &&
                Objects.equals(day_limit, that.day_limit) &&
                Objects.equals(order_limit, that.order_limit) &&
                Objects.equals(period, that.period) &&
                Objects.equals(weeks_time, that.weeks_time) &&
                Objects.equals(act_price, that.act_price) &&
                Objects.equals(discount_coefficient, that.discount_coefficient) &&
                Objects.equals(setting_type, that.setting_type) &&
                Objects.equals(item_id, that.item_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app_food_code, user_type, start_time, end_time, day_limit, order_limit, period, weeks_time, act_price, discount_coefficient, setting_type, item_id);
    }

    @Override
    public String toString() {
        return "ActRetailDiscountParam{" +
                "app_food_code='" + app_food_code + '\'' +
                ", user_type=" + user_type +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", day_limit=" + day_limit +
                ", order_limit=" + order_limit +
                ", period='" + period + '\'' +
                ", weeks_time='" + weeks_time + '\'' +
                ", act_price=" + act_price +
                ", discount_coefficient=" + discount_coefficient +
                ", setting_type=" + setting_type +
                ", item_id=" + item_id +
                '}';
    }
}
