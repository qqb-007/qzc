package com.sankuai.meituan.waimai.opensdk.vo;

public class GiftDetilParam {

    private String app_food_code;           //主品app_food_code

    private String sku_id;                  //主品sku_id

    private String gifts_app_food_code;     //赠品app_food_code，如赠品没有维护app_food_code或非店内商品，则为空

    private String gifts_sku_id;            //赠品sku_id，如赠品没有维护sku_id或非店内商品，则为空

    private String gifts_name;              //赠品名称

    private Integer gift_num;               //赠品数量

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getGifts_app_food_code() {
        return gifts_app_food_code;
    }

    public void setGifts_app_food_code(String gifts_app_food_code) {
        this.gifts_app_food_code = gifts_app_food_code;
    }

    public String getGifts_sku_id() {
        return gifts_sku_id;
    }

    public void setGifts_sku_id(String gifts_sku_id) {
        this.gifts_sku_id = gifts_sku_id;
    }

    public String getGifts_name() {
        return gifts_name;
    }

    public void setGifts_name(String gifts_name) {
        this.gifts_name = gifts_name;
    }

    public Integer getGift_num() {
        return gift_num;
    }

    public void setGift_num(Integer gift_num) {
        this.gift_num = gift_num;
    }

    @Override
    public String toString() {
        return "GiftDetilParam{" +
                "app_food_code='" + app_food_code + '\'' +
                ", sku_id='" + sku_id + '\'' +
                ", gifts_app_food_code='" + gifts_app_food_code + '\'' +
                ", gifts_sku_id='" + gifts_sku_id + '\'' +
                ", gifts_name='" + gifts_name + '\'' +
                ", gift_num=" + gift_num +
                '}';
    }
}
