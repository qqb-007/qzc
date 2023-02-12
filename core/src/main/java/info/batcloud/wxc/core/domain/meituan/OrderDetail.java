package info.batcloud.wxc.core.domain.meituan;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderDetail {

    @JSONField(name = "app_food_code")
    private String appFoodCode;

    @JSONField(name = "food_name")
    private String foodName;

    @JSONField(name = "sku_id")
    private String skuId;

    @JSONField(name = "quantity")
    private float quantity;

    @JSONField(name = "price")
    private float price;

    @JSONField(name = "box_num")
    private float boxNum;

    @JSONField(name = "box_price")
    private float boxPrice;

    @JSONField(name = "unit")
    private String unit;

    @JSONField(name = "food_discount")
    private Float foodDiscount;

    @JSONField(name = "food_property")
    private String foodProperty;

    public String getAppFoodCode() {
        return appFoodCode;
    }

    public void setAppFoodCode(String appFoodCode) {
        this.appFoodCode = appFoodCode;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(float boxNum) {
        this.boxNum = boxNum;
    }

    public float getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(float boxPrice) {
        this.boxPrice = boxPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getFoodDiscount() {
        return foodDiscount;
    }

    public void setFoodDiscount(Float foodDiscount) {
        this.foodDiscount = foodDiscount;
    }

    public String getFoodProperty() {
        return foodProperty;
    }

    public void setFoodProperty(String foodProperty) {
        this.foodProperty = foodProperty;
    }
}
