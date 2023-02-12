package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.SettlementSheetDetailStatus;
import org.apache.regexp.RE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class SettlementSheetDetail {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Date date;

    //结算金额
    @NotNull
    private Float settlementAmount;

    @NotNull
    private Integer orderNum;

    @NotNull
    private Float orderAmount;

    @NotNull
    private Float deductAmount;

    @NotNull
    private Integer partRefundNum;

    @NotNull
    private Float partRefundAmount;

    @NotNull
    private Integer cansunNum;

    @NotNull
    private Float cansunAmount;

    @NotNull
    private Date createTime;

    @NotNull
    private int year;

    private int week;

    private String orderIds;

    public Float getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(Float deductAmount) {
        this.deductAmount = deductAmount;
    }

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private StoreUser storeUser;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SettlementSheetDetailStatus status;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private SettlementSheet settlementSheet;

    public SettlementSheet getSettlementSheet() {
        return settlementSheet;
    }

    public void setSettlementSheet(SettlementSheet settlementSheet) {
        this.settlementSheet = settlementSheet;
    }

    public StoreUser getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUser storeUser) {
        this.storeUser = storeUser;
    }

    public SettlementSheetDetailStatus getStatus() {
        return status;
    }

    public void setStatus(SettlementSheetDetailStatus status) {
        this.status = status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Float settlementAmount) {
        this.settlementAmount = settlementAmount;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
