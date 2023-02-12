package com.sankuai.meituan.waimai.opensdk.vo;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * Created by wangshiyao on 2018/06/14.
 */
public class ActItemParam {
    private Double act_price;
    private String app_poi_code;
    private String app_food_code;
    private Integer day_limit;
    private Integer start_time;
    private Integer end_time;
    private Integer order_limit;
    private String period;
    private Integer user_type;
    private String weeks_time;
    private String name;
    private Double origin_price;
    private Integer stock;
    private Integer status;

    public ActItemParam() {
    }

    public Double getAct_price() {
        return act_price;
    }

    public ActItemParam setAct_price(Double act_price) {
        this.act_price = act_price;
        return this;
    }

    public String getApp_food_code() {
        return app_food_code;
    }

    public ActItemParam setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
        return this;
    }

    public Integer getDay_limit() {
        return day_limit;
    }

    public ActItemParam setDay_limit(Integer day_limit) {
        this.day_limit = day_limit;
        return this;
    }

    public Integer getStart_time() {
        return start_time;
    }

    public ActItemParam setStart_time(Integer start_time) {
        this.start_time = start_time;
        return this;
    }

    public Integer getEnd_time() {
        return end_time;
    }

    public ActItemParam setEnd_time(Integer end_time) {
        this.end_time = end_time;
        return this;
    }

    public Integer getOrder_limit() {
        return order_limit;
    }

    public ActItemParam setOrder_limit(Integer order_limit) {
        this.order_limit = order_limit;
        return this;
    }

    public String getPeriod() {
        return period;
    }

    public ActItemParam setPeriod(String period) {
        this.period = period;
        return this;
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

    public Integer getUser_type() {
        return user_type;
    }

    public ActItemParam setUser_type(Integer user_type) {
        this.user_type = user_type;
        return this;
    }

    public String getWeeks_time() {
        return weeks_time;
    }

    public ActItemParam setWeeks_time(String weeks_time) {
        this.weeks_time = weeks_time;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWmActItemCharge(double ori_price) {
        JSONObject jo = new JSONObject();
        jo.put("actPrice", act_price);
        // 通过接口创建的折扣商品活动默认全为商家承担
        jo.put("mtCharge", 0.0);
        jo.put("originPrice", ori_price);
        // 避免精度损失，四舍五入，保留两位小数
        double poiCharge = BigDecimal.valueOf(ori_price - act_price)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        jo.put("poiCharge", poiCharge);
        return JSONObject.toJSONString(jo);
    }


    @Override
    public String toString() {
        return "ActItemParam [" +
                "act_price='" + act_price + '\'' +
                ", app_food_code='" + app_food_code + '\'' +
                ", day_limit=" + day_limit +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", order_limit=" + order_limit +
                ", period='" + period + '\'' +
                ", user_type=" + user_type +
                ", weeks_time='" + weeks_time + '\'' +
                ", name='" + name + '\'' +
                ", origin_price='" + origin_price +
                ", stock='" + stock +
                ", status='" + status +
                ']';
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }
}
