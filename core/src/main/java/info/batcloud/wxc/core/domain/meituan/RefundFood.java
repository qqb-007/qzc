package info.batcloud.wxc.core.domain.meituan;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Objects;

public class RefundFood {

    @JSONField(name = "app_food_code")
    private String appFoodCode;

    @JSONField(name = "food_name")
    private String foodName;

    @JSONField(name = "sku_id")
    private String skuId;

    @JSONField(name = "spec")
    private String spec;

    @JSONField(name = "type")
    private Integer type;

    @JSONField(name = "food_price")
    private Float foodPrice;

    private Double count;

    @JSONField(name = "box_num")
    private Float boxNum;

    @JSONField(name = "box_price")
    private Float boxPrice;

    @JSONField(name = "origin_food_price")
    private Float originFoodPrice;

    @JSONField(name = "refund_price")
    private Float refundPrice;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Float foodPrice) {
        this.foodPrice = foodPrice;
    }

    public Float getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Float boxNum) {
        this.boxNum = boxNum;
    }

    public Float getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(Float boxPrice) {
        this.boxPrice = boxPrice;
    }

    public Float getOriginFoodPrice() {
        return originFoodPrice;
    }

    public void setOriginFoodPrice(Float originFoodPrice) {
        this.originFoodPrice = originFoodPrice;
    }

    public Float getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Float refundPrice) {
        this.refundPrice = refundPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefundFood that = (RefundFood) o;
        return count == that.count &&
                Objects.equals(appFoodCode, that.appFoodCode) &&
                Objects.equals(foodName, that.foodName) &&
                Objects.equals(skuId, that.skuId) &&
                Objects.equals(spec, that.spec) &&
                Objects.equals(foodPrice, that.foodPrice) &&
                Objects.equals(boxNum, that.boxNum) &&
                Objects.equals(boxPrice, that.boxPrice) &&
                Objects.equals(originFoodPrice, that.originFoodPrice) &&
                Objects.equals(refundPrice, that.refundPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appFoodCode, foodName, skuId, spec, foodPrice, count, boxNum, boxPrice, originFoodPrice, refundPrice);
    }
}
