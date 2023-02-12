package info.batcloud.wxc.core.entity;


import info.batcloud.wxc.core.enums.FundTransferType;
import info.batcloud.wxc.core.enums.WithdrawStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class StoreUserWithdraw {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private StoreUser storeUser;

    private Float money;

    private Date createTime;

    @Enumerated(EnumType.STRING)
    private WithdrawStatus status;

    @NotNull
    private String bankAccountName;

    @NotNull
    private String bankAccount;

    @NotNull
    private String bankName;

    private String payeeAccount;

    private String payeeName;

    //操作时间
    private Date updateTime;

    private Long walletFlowDetailId;

    private Long fundTransferOrderId;

    private String remark;

    private String payDate;

    private String alipayOrderId;

    @Enumerated(EnumType.STRING)
    private FundTransferType fundTransferType;

    public FundTransferType getFundTransferType() {
        return fundTransferType;
    }

    public void setFundTransferType(FundTransferType fundTransferType) {
        this.fundTransferType = fundTransferType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

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

    public Long getFundTransferOrderId() {
        return fundTransferOrderId;
    }

    public void setFundTransferOrderId(Long fundTransferOrderId) {
        this.fundTransferOrderId = fundTransferOrderId;
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

    public Long getWalletFlowDetailId() {
        return walletFlowDetailId;
    }

    public void setWalletFlowDetailId(Long walletFlowDetailId) {
        this.walletFlowDetailId = walletFlowDetailId;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
