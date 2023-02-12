package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.BagStatus;
import info.batcloud.wxc.core.enums.BagType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Bag {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @NotNull
    private StoreUser storeUser;

    private Date createTime;

    @Enumerated(EnumType.STRING)
    private BagType bagType;

    private Integer bagNum;

    private String logistics;

    @Enumerated(EnumType.STRING)
    private BagStatus status;

    private Float fee;

    private String context;


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

    public BagType getBagType() {
        return bagType;
    }

    public void setBagType(BagType bagType) {
        this.bagType = bagType;
    }

    public Integer getBagNum() {
        return bagNum;
    }

    public void setBagNum(Integer bagNum) {
        this.bagNum = bagNum;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public BagStatus getStatus() {
        return status;
    }

    public void setStatus(BagStatus status) {
        this.status = status;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    @Override
    public String toString() {
        return "Bag{" +
                "id=" + id +
                ", storeUser=" + storeUser +
                ", createTime=" + createTime +
                ", bagType=" + bagType +
                ", bagNum=" + bagNum +
                ", logistics='" + logistics + '\'' +
                ", status=" + status +
                ", fee=" + fee +
                ", context='" + context + '\'' +
                '}';
    }


}
