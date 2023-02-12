package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.BagStatus;
import info.batcloud.wxc.core.enums.BagType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class BagDTO {

    private Long id;

    private StoreUserDTO storeUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private BagType bagType;

    private Integer bagNum;

    private String logistics;

    private BagStatus status;

    private Float fee;

    private String context;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BagType getBagType() {
        return bagType;
    }

    public void setBagType(BagType bagType) {
        this.bagType = bagType;
    }

    public String getBagTypeTitle() {
        return getBagType() == null ? null : getBagType().getTitle();
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

    public String getStatusTitle() {
        return getStatus() == null ? null : getStatus().getTitle();
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
}
