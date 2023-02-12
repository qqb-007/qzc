package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.StoreFoodApplicationStatus;

import java.util.Date;

public class StoreFoodApplicationDTO {

    private Long id;

    private Long storeUserId;

    private String storeUserName;

    private Long foodSupplierId;

    private String foodSupplierName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String foodName;

    private String unit;

    private String sku;

    private Float price;

    private String foodPicture;

    private StoreFoodApplicationStatus status;

    public Long getFoodSupplierId() {
        return foodSupplierId;
    }

    public void setFoodSupplierId(Long foodSupplierId) {
        this.foodSupplierId = foodSupplierId;
    }

    public String getFoodSupplierName() {
        return foodSupplierName;
    }

    public void setFoodSupplierName(String foodSupplierName) {
        this.foodSupplierName = foodSupplierName;
    }

    public String getStatusTitle() {
        return status == null ? null : status.getTitle();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return status == null ? null : status.getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(Long storeUserId) {
        this.storeUserId = storeUserId;
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public StoreFoodApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(StoreFoodApplicationStatus status) {
        this.status = status;
    }
}
