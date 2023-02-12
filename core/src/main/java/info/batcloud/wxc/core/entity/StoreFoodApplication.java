package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.StoreFoodApplicationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class StoreFoodApplication {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @NotNull
    private StoreUser storeUser;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private FoodSupplier foodSupplier;

    @NotNull
    private Date createTime;

    @NotNull
    private Date updateTime;

    @NotNull
    private String foodName;

    @NotNull
    private String unit;

    @NotNull
    private Float price;

    @NotNull
    private String foodPicture;

    @Enumerated(EnumType.STRING)
    private StoreFoodApplicationStatus status;

    public FoodSupplier getFoodSupplier() {
        return foodSupplier;
    }

    public void setFoodSupplier(FoodSupplier foodSupplier) {
        this.foodSupplier = foodSupplier;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreUser getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUser storeUser) {
        this.storeUser = storeUser;
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
