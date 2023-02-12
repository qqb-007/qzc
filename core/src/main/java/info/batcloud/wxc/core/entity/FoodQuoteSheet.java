package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.FoodQuoteSheetStatus;
import info.batcloud.wxc.core.enums.PublishStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 商品报价单
 */
@Entity
public class FoodQuoteSheet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private StoreUser storeUser;

    @NotNull
    private Date createTime;

    @Enumerated(EnumType.STRING)
    private FoodQuoteSheetStatus status;

    @NotNull
    private Date updateTime;

    @NotNull
    private Integer foodNum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PublishStatus publishStatus;

    private Date publishTime;

    private String verifyRemark;

    public String getPublishStatusTitle() {
        return publishStatus == null ? null : publishStatus.getTitle();
    }

    public PublishStatus getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(PublishStatus publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Long getId() {
        return id;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark;
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

    public FoodQuoteSheetStatus getStatus() {
        return status;
    }

    public void setStatus(FoodQuoteSheetStatus status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(Integer foodNum) {
        this.foodNum = foodNum;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
