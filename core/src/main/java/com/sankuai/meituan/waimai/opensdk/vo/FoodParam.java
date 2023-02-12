package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by yangzhiqi on 15/10/15.
 */
public class FoodParam {
    private Integer food_id;
    private String app_poi_code;
    private String app_food_code;
    private Integer operation;
    private String name;
    private String description;
    private Float price;
    private Integer min_order_count;
    private String unit;
    private Float box_num;
    private Float box_price;
    private String category_name;
    private String secondary_category_name;
    private Integer is_sold_out;
    private String picture;
    private Integer sequence;
    private List<FoodSkuParam> skus;
    private Long ctime;
    private Long utime;
    private Float special_price; // 菜品特价价格
    private Integer max_order_count; // 最大购买数量

    public Integer getFood_id() {
        return food_id;
    }

    public FoodParam setFood_id(Integer food_id) {
        this.food_id = food_id;
        return this;
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public FoodParam setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
        return this;
    }

    public String getApp_food_code() {
        return app_food_code;
    }

    public FoodParam setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
        return this;
    }

    public Integer getOperation() {
        return operation;
    }

    public FoodParam setOperation(Integer operation) {
        this.operation = operation;
        return this;
    }

    public String getName() {
        return name;
    }

    public FoodParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FoodParam setDescription(String description) {
        this.description = description;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public FoodParam setPrice(Float price) {
        this.price = price;
        return this;
    }

    public Integer getMin_order_count() {
        return min_order_count;
    }

    public FoodParam setMin_order_count(Integer min_order_count) {
        this.min_order_count = min_order_count;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public FoodParam setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public Float getBox_num() {
        return box_num;
    }

    public FoodParam setBox_num(Float box_num) {
        this.box_num = box_num;
        return this;
    }

    public Float getBox_price() {
        return box_price;
    }

    public FoodParam setBox_price(Float box_price) {
        this.box_price = box_price;
        return this;
    }

    public String getCategory_name() {
        return category_name;
    }

    public FoodParam setCategory_name(String category_name) {
        this.category_name = category_name;
        return this;
    }

    public String getSecondary_category_name() {
        return secondary_category_name;
    }

    public FoodParam setSecondary_category_name(String secondary_category_name) {
        this.secondary_category_name = secondary_category_name;
        return this;
    }

    public Integer getIs_sold_out() {
        return is_sold_out;
    }

    public FoodParam setIs_sold_out(Integer is_sold_out) {
        this.is_sold_out = is_sold_out;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public FoodParam setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public FoodParam setSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public List<FoodSkuParam> getSkus() {
        return skus;
    }

    public FoodParam setSkus(List<FoodSkuParam> skus) {
        this.skus = skus;
        return this;
    }

    public Long getCtime() {
        return ctime;
    }

    public FoodParam setCtime(Long ctime) {
        this.ctime = ctime;
        return this;
    }

    public Long getUtime() {
        return utime;
    }

    public FoodParam setUtime(Long utime) {
        this.utime = utime;
        return this;
    }

    public Float getSpecial_price() {
        return special_price;
    }

    public FoodParam setSpecial_price(Float special_price) {
        this.special_price = special_price;
        return this;
    }

    public Integer getMax_order_count() {
        return max_order_count;
    }

    public FoodParam setMax_order_count(Integer max_order_count) {
        this.max_order_count = max_order_count;
        return this;
    }

    @Override
    public String toString() {
        return "FoodParam [" +
                "food_id=" + food_id +
                ", app_poi_code='" + app_poi_code + '\'' +
                ", app_food_code='" + app_food_code + '\'' +
                ", operation=" + operation +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", min_order_count=" + min_order_count +
                ", unit='" + unit + '\'' +
                ", box_num=" + box_num +
                ", box_price=" + box_price +
                ", category_name='" + category_name + '\'' +
                ", secondary_category_name='" + secondary_category_name + '\'' +
                ", is_sold_out=" + is_sold_out +
                ", picture='" + picture + '\'' +
                ", sequence=" + sequence +
                ", skus=" + skus +
                ", ctime=" + ctime +
                ", utime=" + utime +
                ", special_price=" + special_price +
                ", max_order_count=" + max_order_count +
                ']';
    }
}
