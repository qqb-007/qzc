package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.SettlementSheetDetailStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SettlementSheetDetailDTO {

    private Long id;

    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private Date date;

    //结算金额
    private Float settlementAmount;

    private Integer orderNum;

    private Float orderAmount;

    private Integer partRefundNum;

    private Float partRefundAmount;

    private Float deductAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String storeUserName;
    private Long storeUserId;

    @NotNull
    private Integer cansunNum;

    @NotNull
    private Float cansunAmount;

    private int year;

    private int week;

    public Float getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(Float deductAmount) {
        this.deductAmount = deductAmount;
    }

    private SettlementSheetDetailStatus status;

    public String getStatusTitle() {
        return status == null ? null : status.getTitle();
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
    }

    public Long getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(Long storeUserId) {
        this.storeUserId = storeUserId;
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

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SettlementSheetDetailStatus getStatus() {
        return status;
    }

    public void setStatus(SettlementSheetDetailStatus status) {
        this.status = status;
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
}
