package com.sankuai.meituan.waimai.opensdk.vo;

public class ActListParam {
    private String app_food_code;
    private String type_name;
    private Integer type;
    private Integer status;
    private Integer start_time;
    private Integer end_time;
    private Long act_id;
    private Integer can_modify_price;
    private String sku_id;

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getAct_id() {
        return act_id;
    }

    public void setAct_id(Long act_id) {
        this.act_id = act_id;
    }

    public Integer getCan_modify_price() {
        return can_modify_price;
    }

    public void setCan_modify_price(Integer can_modify_price) {
        this.can_modify_price = can_modify_price;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    @Override
    public String toString() {
        return "ActListParam{" +
                "app_food_code='" + app_food_code + '\'' +
                ", type_name='" + type_name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", act_id=" + act_id +
                ", can_modify_price=" + can_modify_price +
                ", sku_id='" + sku_id + '\'' +
                '}';
    }
}
