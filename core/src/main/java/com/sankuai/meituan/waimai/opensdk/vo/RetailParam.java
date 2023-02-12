package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by wangshiyao on 2018/06/14.
 */
public class RetailParam {

    private String app_poi_code;
    private String app_food_code;
    private Integer operation; //标志位：0:新增，1:更改
    private String name;
    private String description;
    private Float price;
    private Integer min_order_count;
    private String unit;
    private Float box_num;
    private Float box_price;
    private String category_name;
    private String secondary_category_name;
    private Integer is_sold_out; //0:卖光 1:未卖光
    private String picture;
    private Long video_id;
    private Integer sequence;
    private List<RetailSkuParam> skus;
    private RetailSkuParam standard_sku;
    private List<RetailSkuParam> unstandard_skus;
    private List<FoodProperty> properties;
    private String upc_code;
    private Long ctime;
    private Long utime;
    private int is_sp;
    //for 特价菜
    private Float special_price;//商品特价价格
    private Integer max_order_count;//最大购买数量
    // 商品类目id
    private Long tag_id;
    //商品品牌
    private String zh_name;
    //标准品名
    private String product_name;
    //补充标题
    private String flavour;
    //产地
    private String origin_name;
    //产地id
    private Integer origin_id;

    public List<FoodProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<FoodProperty> properties) {
        this.properties = properties;
    }

    public int getIs_sp() {
        return is_sp;
    }

    public void setIs_sp(int is_sp) {
        this.is_sp = is_sp;
    }

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getMin_order_count() {
        return min_order_count;
    }

    public void setMin_order_count(Integer min_order_count) {
        this.min_order_count = min_order_count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getBox_num() {
        return box_num;
    }

    public void setBox_num(Float box_num) {
        this.box_num = box_num;
    }

    public Float getBox_price() {
        return box_price;
    }

    public void setBox_price(Float box_price) {
        this.box_price = box_price;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSecondary_category_name() {
        return secondary_category_name;
    }

    public void setSecondary_category_name(String secondary_category_name) {
        this.secondary_category_name = secondary_category_name;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }

    public Integer getIs_sold_out() {
        return is_sold_out;
    }

    public void setIs_sold_out(Integer is_sold_out) {
        this.is_sold_out = is_sold_out;
    }

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public List<RetailSkuParam> getSkus() {
        return skus;
    }

    public void setSkus(List<RetailSkuParam> skus) {
        this.skus = skus;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(Float special_price) {
        this.special_price = special_price;
    }

    public Integer getMax_order_count() {
        return max_order_count;
    }

    public void setMax_order_count(Integer max_order_count) {
        this.max_order_count = max_order_count;
    }

    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public String getZh_name() {
        return zh_name;
    }

    public void setZh_name(String zh_name) {
        this.zh_name = zh_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }

    public RetailSkuParam getStandard_sku() {
        return standard_sku;
    }

    public void setStandard_sku(RetailSkuParam standard_sku) {
        this.standard_sku = standard_sku;
    }

    public List<RetailSkuParam> getUnstandard_skus() {
        return unstandard_skus;
    }

    public void setUnstandard_skus(List<RetailSkuParam> unstandard_skus) {
        this.unstandard_skus = unstandard_skus;
    }

    public String getUpc_code() {
        return upc_code;
    }

    public void setUpc_code(String upc_code) {
        this.upc_code = upc_code;
    }

    public Integer getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(Integer origin_id) {
        this.origin_id = origin_id;
    }

    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Long video_id) {
        this.video_id = video_id;
    }

    @Override
    public String toString() {
        return "RetailParam{" +
                "app_poi_code='" + app_poi_code + '\'' +
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
                ", video_id=" + video_id +
                ", sequence=" + sequence +
                ", skus=" + skus +
                ", standard_sku=" + standard_sku +
                ", unstandard_skus=" + unstandard_skus +
                ", upc_code='" + upc_code + '\'' +
                ", ctime=" + ctime +
                ", utime=" + utime +
                ", is_sp=" + is_sp +
                ", special_price=" + special_price +
                ", max_order_count=" + max_order_count +
                ", tag_id=" + tag_id +
                ", zh_name='" + zh_name + '\'' +
                ", product_name='" + product_name + '\'' +
                ", flavour='" + flavour + '\'' +
                ", origin_name='" + origin_name + '\'' +
                ", origin_id=" + origin_id +
                '}';
    }
}
