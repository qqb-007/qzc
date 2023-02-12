package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.FoodSupplierPriceIncreaseType;
import info.batcloud.wxc.core.enums.FoodSupplierStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class FoodSupplier {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String phone;

    private String password;

    @NotNull
    private String name;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private StoreUser storeUser;

    private Date createTime;

    private String address;

    @Version
    private Date updateTime;

    @Enumerated(EnumType.STRING)
    private FoodSupplierStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull
    private FoodSupplierPriceIncreaseType priceIncreaseType;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FoodSupplierPriceIncreaseType getPriceIncreaseType() {
        return priceIncreaseType;
    }

    public void setPriceIncreaseType(FoodSupplierPriceIncreaseType priceIncreaseType) {
        this.priceIncreaseType = priceIncreaseType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FoodSupplierStatus getStatus() {
        return status;
    }

    public void setStatus(FoodSupplierStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
