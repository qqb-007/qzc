package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.WithdrawStatus;

import java.util.Date;

public class StoreUserWithdrawDTO {

    private Long id;

    private StoreUserDTO storeUser;

    private Float money;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private WithdrawStatus status;

    private String payeeAccount;

    private String payeeName;

    //操作时间
    private Date updateTime;

    private Long fundTransferOrderId;

    private String remark;

    private String payDate;

    private String alipayOrderId;

    private String bankAccountName;

    private String bankAccount;

    private String bankName;

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getAlipayOrderId() {
        return alipayOrderId;
    }

    public void setAlipayOrderId(String alipayOrderId) {
        this.alipayOrderId = alipayOrderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatusTitle() {
        return status == null ? null : status.getTitle();
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

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public WithdrawStatus getStatus() {
        return status;
    }

    public void setStatus(WithdrawStatus status) {
        this.status = status;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getFundTransferOrderId() {
        return fundTransferOrderId;
    }

    public void setFundTransferOrderId(Long fundTransferOrderId) {
        this.fundTransferOrderId = fundTransferOrderId;
    }
}
