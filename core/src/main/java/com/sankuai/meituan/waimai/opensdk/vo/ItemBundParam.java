package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

public class ItemBundParam {
    private Long act_id;
    private String act_name;
    private Integer start_time;
    private Integer end_time;
    private Double act_price;
    private Integer act_num;
    private List<BundItemParam> app_foods;

    public Long getAct_id() {
        return act_id;
    }

    public void setAct_id(Long act_id) {
        this.act_id = act_id;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
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

    public Double getAct_price() {
        return act_price;
    }

    public void setAct_price(Double act_price) {
        this.act_price = act_price;
    }

    public Integer getAct_num() {
        return act_num;
    }

    public void setAct_num(Integer act_num) {
        this.act_num = act_num;
    }

    public List<BundItemParam> getApp_foods() {
        return app_foods;
    }

    public void setApp_foods(List<BundItemParam> app_foods) {
        this.app_foods = app_foods;
    }
}
