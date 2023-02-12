package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by wangshiyao on 2018/06/14.
 */
public class ActFullDiscountParam {

    private String app_poi_code;
    private ActFullDiscountItemParam act_info;
    private List<ActItemParam> act_details; //调用接口时，入参：满减层级
    private String act_remark;//满减层级，查询返回值，例：满10减1，满20减2
    private String app_food_codes;
    private List<ActItemParam> app_foods;

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public ActFullDiscountItemParam getAct_info() {
        return act_info;
    }

    public void setAct_info(ActFullDiscountItemParam act_info) {
        this.act_info = act_info;
    }

    public String getApp_food_codes() {
        return app_food_codes;
    }

    public void setApp_food_codes(String app_food_codes) {
        this.app_food_codes = app_food_codes;
    }

    public List<ActItemParam> getApp_foods() {
        return app_foods;
    }

    public void setApp_foods(List<ActItemParam> app_foods) {
        this.app_foods = app_foods;
    }

    public List<ActItemParam> getAct_details() {
        return act_details;
    }

    public void setAct_details(List<ActItemParam> act_details) {
        this.act_details = act_details;
    }

    public String getAct_remark() {
        return act_remark;
    }

    public void setAct_remark(String act_remark) {
        this.act_remark = act_remark;
    }

    @Override
    public String toString() {
        return "WmAppActFullDiscount{" +
                "app_poi_code='" + app_poi_code + '\'' +
                ", act_info=" + act_info +
                ", act_details=" + act_details +
                ", act_remark='" + act_remark + '\'' +
                ", app_food_codes='" + app_food_codes + '\'' +
                ", app_foods=" + app_foods +
                '}';
    }
}
