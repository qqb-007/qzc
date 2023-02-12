package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by wangshiyao on 2018/06/14.
 */
public class ActBuyGiftsItemParam {
    private Long item_id;
    private String app_food_code;
    private String app_poi_code;
    private Integer gifts_type;
    private String gifts_name;
    private Integer start_time;
    private Integer end_time;
    private Integer buy_num;
    private Integer gifts_num;
    private String buy_gifts_num;
    private String gifts_charge;//单个赠品成本
    private Integer gifts_day_limit;
    private Integer status;
    private String item_name;//活动组装名称
    private String buy_gifts_model;
    private String charge;//成本相关组装数据

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public Integer getGifts_type() {
        return gifts_type;
    }

    public void setGifts_type(Integer gifts_type) {
        this.gifts_type = gifts_type;
    }

    public String getGifts_name() {
        return gifts_name;
    }

    public void setGifts_name(String gifts_name) {
        this.gifts_name = gifts_name;
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

    public Integer getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(Integer buy_num) {
        this.buy_num = buy_num;
    }

    public Integer getGifts_num() {
        return gifts_num;
    }

    public void setGifts_num(Integer gifts_num) {
        this.gifts_num = gifts_num;
    }

    public String getBuy_gifts_num() {
        return buy_gifts_num;
    }

    public void setBuy_gifts_num(String buy_gifts_num) {
        this.buy_gifts_num = buy_gifts_num;
    }

    public String getGifts_charge() {
        return gifts_charge;
    }

    public void setGifts_charge(String gifts_charge) {
        this.gifts_charge = gifts_charge;
    }

    public Integer getGifts_day_limit() {
        return gifts_day_limit;
    }

    public void setGifts_day_limit(Integer gifts_day_limit) {
        this.gifts_day_limit = gifts_day_limit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBuy_gifts_model() {
        return buy_gifts_model;
    }

    public void setBuy_gifts_model(String buy_gifts_model) {
        this.buy_gifts_model = buy_gifts_model;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    @Override
    public String toString() {
        return "ActBuyGiftsItem{" +
                "item_id=" + item_id +
                ", app_food_code='" + app_food_code + '\'' +
                ", app_poi_code='" + app_poi_code + '\'' +
                ", gifts_type=" + gifts_type +
                ", gifts_name='" + gifts_name + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", buy_num=" + buy_num +
                ", gifts_num=" + gifts_num +
                ", buy_gifts_num='" + buy_gifts_num + '\'' +
                ", gifts_charge='" + gifts_charge + '\'' +
                ", gifts_day_limit=" + gifts_day_limit +
                ", status=" + status +
                ", item_name='" + item_name + '\'' +
                ", buy_gifts_model='" + buy_gifts_model + '\'' +
                ", charge='" + charge + '\'' +
                '}';
    }
}