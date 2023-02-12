package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.SettlementSheetStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class SettlementSheetDTO {

    private Long id;

    private StoreUserDTO storeUser;

    private Integer orderNum;

    private Float orderAmount;

    private Integer partRefundNum;

    private Float partRefundAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 结算金额
     */
    private Float settlementAmount;

    private Float deductAmount;

    @NotNull
    private Integer cansunNum;

    @NotNull
    private Float cansunAmount;

    private int year;

    private int week;

    private List<SettlementSheetDetailDTO> detailList;

    private Boolean settled;

    private SettlementSheetStatus status;

    public Float getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(Float deductAmount) {
        this.deductAmount = deductAmount;
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

    public String getStatusTitle() {
        return status == null ? null : status.getTitle();
    }

    public SettlementSheetStatus getStatus() {
        return status;
    }

    public void setStatus(SettlementSheetStatus status) {
        this.status = status;
    }

    public List<SettlementSheetDetailDTO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<SettlementSheetDetailDTO> detailList) {
        this.detailList = detailList;
    }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate == null ? startDate : endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Float getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Float settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public Boolean getSettled() {
        return settled;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
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
