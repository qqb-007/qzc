package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.FoodSupplierPriceIncreaseType;
import info.batcloud.wxc.core.enums.FoodSupplierStatus;

import java.util.Date;

public class FoodSupplierDTO {

    private Long id;

    private String phone;

    private String name;

    private String address;

    private StoreUserDTO storeUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private FoodSupplierStatus status;

    private FoodSupplierPriceIncreaseType priceIncreaseType;

    public String getStatusTitle() {
        return status == null ? null : status.getTitle();
    }

    public String getPriceIncreaseTypeTitle() {
        return priceIncreaseType == null ? null : priceIncreaseType.getTitle();
    }

    public FoodSupplierPriceIncreaseType getPriceIncreaseType() {
        return priceIncreaseType;
    }

    public void setPriceIncreaseType(FoodSupplierPriceIncreaseType priceIncreaseType) {
        this.priceIncreaseType = priceIncreaseType;
    }

    public FoodSupplierStatus getStatus() {
        return status;
    }

    public void setStatus(FoodSupplierStatus status) {
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StoreUserDTO getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUserDTO storeUser) {
        this.storeUser = storeUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
