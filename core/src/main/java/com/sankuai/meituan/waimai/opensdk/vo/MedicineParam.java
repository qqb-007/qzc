package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangyuanbo02 on 15/10/28.
 */
public class MedicineParam {
    private String app_poi_code;
    private String app_medicine_code;
    private String upc;
    private String medicine_no;
    private String spec;
    private String price;
    private String stock;
    private String category_code;
    private String category_name;
    private String is_sold_out;
    private String sequence;
    private String app_medicine_name;
    private String name;
    private String medicine_id;
    private String ctime;
    private String utime;
    private String source_food_code;
    private String unit;
    private String id;

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public MedicineParam setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
        return this;
    }

    public String getApp_medicine_code() {
        return app_medicine_code;
    }

    public MedicineParam setApp_medicine_code(String app_medicine_code) {
        this.app_medicine_code = app_medicine_code;
        return this;
    }

    public String getUpc() {
        return upc;
    }

    public MedicineParam setUpc(String upc) {
        this.upc = upc;
        return this;
    }

    public String getMedicine_no() {
        return medicine_no;
    }

    public MedicineParam setMedicine_no(String medicine_no) {
        this.medicine_no = medicine_no;
        return this;
    }

    public String getSpec() {
        return spec;
    }

    public MedicineParam setSpec(String spec) {
        this.spec = spec;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public MedicineParam setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getStock() {
        return stock;
    }

    public MedicineParam setStock(String stock) {
        this.stock = stock;
        return this;
    }

    public String getCategory_code() {
        return category_code;
    }

    public MedicineParam setCategory_code(String category_code) {
        this.category_code = category_code;
        return this;
    }

    public String getCategory_name() {
        return category_name;
    }

    public MedicineParam setCategory_name(String category_name) {
        this.category_name = category_name;
        return this;
    }

    public String getIs_sold_out() {
        return is_sold_out;
    }

    public MedicineParam setIs_sold_out(String is_sold_out) {
        this.is_sold_out = is_sold_out;
        return this;
    }

    public String getSequence() {
        return sequence;
    }

    public MedicineParam setSequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public String getApp_medicine_name() {
        return app_medicine_name;
    }

    public MedicineParam setApp_medicine_name(String app_medicine_name) {
        this.app_medicine_name = app_medicine_name;
        return this;
    }

    public String getName() {
        return name;
    }

    public MedicineParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getMedicine_id() {
        return medicine_id;
    }

    public MedicineParam setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
        return this;
    }

    public String getCtime() {
        return ctime;
    }

    public MedicineParam setCtime(String ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getUtime() {
        return utime;
    }

    public MedicineParam setUtime(String utime) {
        this.utime = utime;
        return this;
    }

    public String getSource_food_code() {
        return source_food_code;
    }

    public MedicineParam setSource_food_code(String source_food_code) {
        this.source_food_code = source_food_code;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public MedicineParam setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String getId() {
        return id;
    }

    public MedicineParam setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "MedicineParam [" +
                "app_poi_code='" + app_poi_code + '\'' +
                ", app_medicine_code='" + app_medicine_code + '\'' +
                ", upc='" + upc + '\'' +
                ", medicine_no='" + medicine_no + '\'' +
                ", spec='" + spec + '\'' +
                ", price='" + price + '\'' +
                ", stock='" + stock + '\'' +
                ", category_code='" + category_code + '\'' +
                ", category_name='" + category_name + '\'' +
                ", is_sold_out='" + is_sold_out + '\'' +
                ", sequence='" + sequence + '\'' +
                ", app_medicine_name='" + app_medicine_name + '\'' +
                ", name='" + name + '\'' +
                ", medicine_id='" + medicine_id + '\'' +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                ", source_food_code='" + source_food_code + '\'' +
                ", unit='" + unit + '\'' +
                ", id='" + id + '\'' +
                ']';
    }
}
