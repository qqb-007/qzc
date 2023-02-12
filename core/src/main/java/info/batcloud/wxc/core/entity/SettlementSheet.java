package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.SettlementSheetStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 结算单
 */
@Entity
@NamedEntityGraph(name = "StoreUser.Graph", attributeNodes = {@NamedAttributeNode("storeUser")})
public class SettlementSheet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @NotNull
    @Fetch(FetchMode.JOIN)
    private StoreUser storeUser;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SettlementSheetStatus status;

    @NotNull
    private Integer orderNum;

    @NotNull
    private Float orderAmount;

    @NotNull
    private Integer cansunNum;

    @NotNull
    private Float cansunAmount;

    @NotNull
    private Integer partRefundNum;

    @NotNull
    private Float partRefundAmount;

    @NotNull
    private Float deductAmount;

    @NotNull
    private Date createTime;

    @NotNull
    private Date updateTime;

    /**
     * 开始日期
     */
    @NotNull
    private Date startDate;

    /**
     * 结束日期
     */
    @NotNull
    private Date endDate;

    /**
     * 结算金额
     */
    private Float settlementAmount;

    @NotNull
    private Boolean settled;

    @NotNull
    private Integer year;

    @NotNull
    private Integer week;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_sheet_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<SettlementSheetDetail> detailList;

    @Version
    private Integer version;

    public Float getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(Float deductAmount) {
        this.deductAmount = deductAmount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public SettlementSheetStatus getStatus() {
        return status;
    }

    public void setStatus(SettlementSheetStatus status) {
        this.status = status;
    }

    public List<SettlementSheetDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SettlementSheetDetail> detailList) {
        this.detailList = detailList;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getSettled() {
        return settled;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getPartRefundNum() {
        return partRefundNum;
    }

    public void setPartRefundNum(Integer partRefundNum) {
        this.partRefundNum = partRefundNum;
    }

    public Float getPartRefundAmount() {
        return partRefundAmount;
    }

    public void setPartRefundAmount(Float partRefundAmount) {
        this.partRefundAmount = partRefundAmount;
    }

    public Float getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Float settlementAmount) {
        this.settlementAmount = settlementAmount;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCansunNum() {
        return cansunNum;
    }

    public void setCansunNum(Integer cansunNum) {
        this.cansunNum = cansunNum;
    }

    public Float getCansunAmount() {
        return cansunAmount;
    }

    public void setCansunAmount(Float cansunAmount) {
        this.cansunAmount = cansunAmount;
    }
}
