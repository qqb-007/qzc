package info.batcloud.wxc.core.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StoreUserFoodSku {
    @Id
    @GeneratedValue
    private Long id;

    private Long foodId;

    private Long storeUserId;

    private Long storeUserFoodId;

    private Long foodSkuId;

    private String warehouseIds;

    private Integer stock;

    private Float inputPrice;

    private Float outputPrice;

    //upc
    private String upc;

    private String name;

    private Integer weight;

    private String spec;

    private Float inputTax;

    private Float outputTax;

    private Integer minOrderCount;

    private Integer boxNum;

    private Float boxPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Long getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(Long storeUserId) {
        this.storeUserId = storeUserId;
    }

    public Long getStoreUserFoodId() {
        return storeUserFoodId;
    }

    public void setStoreUserFoodId(Long storeUserFoodId) {
        this.storeUserFoodId = storeUserFoodId;
    }

    public Long getFoodSkuId() {
        return foodSkuId;
    }

    public void setFoodSkuId(Long foodSkuId) {
        this.foodSkuId = foodSkuId;
    }

    public String getWarehouseIds() {
        return warehouseIds;
    }

    public void setWarehouseIds(String warehouseIds) {
        this.warehouseIds = warehouseIds;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(Float inputPrice) {
        this.inputPrice = inputPrice;
    }

    public Float getOutputPrice() {
        return outputPrice;
    }

    public void setOutputPrice(Float outputPrice) {
        this.outputPrice = outputPrice;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Float getInputTax() {
        return inputTax;
    }

    public void setInputTax(Float inputTax) {
        this.inputTax = inputTax;
    }

    public Float getOutputTax() {
        return outputTax;
    }

    public void setOutputTax(Float outputTax) {
        this.outputTax = outputTax;
    }

    public Integer getMinOrderCount() {
        return minOrderCount;
    }

    public void setMinOrderCount(Integer minOrderCount) {
        this.minOrderCount = minOrderCount;
    }

    public Integer getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Integer boxNum) {
        this.boxNum = boxNum;
    }

    public Float getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(Float boxPrice) {
        this.boxPrice = boxPrice;
    }

    @Override
    public String toString() {
        return "StoreUserFoodSku{" +
                "id=" + id +
                ", foodId=" + foodId +
                ", storeUserId=" + storeUserId +
                ", storeUserFoodId=" + storeUserFoodId +
                ", foodSkuId=" + foodSkuId +
                ", warehouseIds='" + warehouseIds + '\'' +
                ", stock=" + stock +
                ", inputPrice=" + inputPrice +
                ", outputPrice=" + outputPrice +
                ", upc='" + upc + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", spec='" + spec + '\'' +
                ", inputTax=" + inputTax +
                ", outputTax=" + outputTax +
                ", minOrderCount=" + minOrderCount +
                ", boxNum=" + boxNum +
                ", boxPrice=" + boxPrice +
                '}';
    }
}
