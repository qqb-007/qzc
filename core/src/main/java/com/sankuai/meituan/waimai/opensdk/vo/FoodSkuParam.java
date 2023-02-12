package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class FoodSkuParam {
    private String sku_id;
    private String spec;
    private String upc;
    private String stock;
    private String price;
    private String location_code;
    private AvailableTimeParam available_times;
    private String box_num;
    private String box_price;
    private Long weight;

    public String getSku_id() {
        return sku_id;
    }

    public FoodSkuParam setSku_id(String sku_id) {
        this.sku_id = sku_id;
        return this;
    }

    public String getSpec() {
        return spec;
    }

    public FoodSkuParam setSpec(String spec) {
        this.spec = spec;
        return this;
    }

    public String getUpc() {
        return upc;
    }

    public FoodSkuParam setUpc(String upc) {
        this.upc = upc;
        return this;
    }

    public String getStock() {
        return stock;
    }

    public FoodSkuParam setStock(String stock) {
        this.stock = stock;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public FoodSkuParam setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getLocation_code() {
        return location_code;
    }

    public FoodSkuParam setLocation_code(String location_code) {
        this.location_code = location_code;
        return this;
    }

    public AvailableTimeParam getAvailable_times() {
        return available_times;
    }

    public FoodSkuParam setAvailable_times(AvailableTimeParam available_times) {
        this.available_times = available_times;
        return this;
    }

    public String getBox_num() {
        return box_num;
    }

    public FoodSkuParam setBox_num(String box_num) {
        this.box_num = box_num;
        return this;
    }

    public String getBox_price() {
        return box_price;
    }

    public FoodSkuParam setBox_price(String box_price) {
        this.box_price = box_price;
        return this;
    }

    public Long getWeight() {
        return weight;
    }

    public FoodSkuParam setWeight(Long weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public String toString() {
        return "FoodSkuParam [" +
                "sku_id='" + sku_id + '\'' +
                ", spec='" + spec + '\'' +
                ", upc='" + upc + '\'' +
                ", stock='" + stock + '\'' +
                ", price='" + price + '\'' +
                ", location_code='" + location_code + '\'' +
                ", available_times=" + available_times +
                ", box_num='" + box_num + '\'' +
                ", box_price='" + box_price + '\'' +
                ", weight=" + weight +
                ']';
    }
}
