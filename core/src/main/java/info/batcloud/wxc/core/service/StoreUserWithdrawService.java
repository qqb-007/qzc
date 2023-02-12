package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.StoreUserWithdrawDTO;
import info.batcloud.wxc.core.enums.WithdrawStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;

public interface StoreUserWithdrawService {

    boolean withdraw(Long storeUserId);

    void BatchCheck(SearchParam param);

    void BatchTransfer();

    String export(SearchParam param) throws IOException;

    void withdraw(long userId, StoreUserWithdrawParams params);

    Paging<StoreUserWithdrawDTO> search(SearchParam param);

    StoreUserWithdrawDTO findById(long id);

    StoreUserWithdrawDTO findByWalletFlowDetailId(long walletFlowDetailId);

    Result withdrawFundTransferById(long id);

    String findStoreUserLastAlipayAccount(long userId);

    Result withdrawManualFundTransfer(ManualFundTransferParam param);

    void verify(VerifyParam param);

    class ManualFundTransferParam {
        @NotNull
        private Long id;
        @NotNull
        private String alipayOrderId;

        private String remark;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
    }

    class VerifyParam {
        private Long id;
        private boolean success;
        private String remark;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    class SearchParam extends PagingParam {
        private String storeUserName;
        private String storeUserPhone;
        private Long storeUserId;
        private WithdrawStatus status;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createStartTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createEndTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date createStartDate;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date createEndDate;

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Date getCreateStartDate() {
            return createStartDate;
        }

        public void setCreateStartDate(Date createStartDate) {
            this.createStartDate = createStartDate;
        }

        public Date getCreateEndDate() {
            return createEndDate;
        }

        public void setCreateEndDate(Date createEndDate) {
            this.createEndDate = createEndDate;
        }

        public String getStoreUserName() {
            return storeUserName;
        }

        public void setStoreUserName(String storeUserName) {
            this.storeUserName = storeUserName;
        }

        public String getStoreUserPhone() {
            return storeUserPhone;
        }

        public void setStoreUserPhone(String storeUserPhone) {
            this.storeUserPhone = storeUserPhone;
        }

        public WithdrawStatus getStatus() {
            return status;
        }

        public void setStatus(WithdrawStatus status) {
            this.status = status;
        }

        public Date getCreateStartTime() {
            return createStartTime;
        }

        public void setCreateStartTime(Date createStartTime) {
            this.createStartTime = createStartTime;
        }

        public Date getCreateEndTime() {
            return createEndTime;
        }

        public void setCreateEndTime(Date createEndTime) {
            this.createEndTime = createEndTime;
        }
    }

    class StoreUserWithdrawParams {
        @NotNull
        private Float money;
        @NotNull
        private String bankName;
        @NotNull
        private String bankAccount;
        @NotNull
        private String bankAccountName;

        public Float getMoney() {
            return money;
        }

        public void setMoney(Float money) {
            this.money = money;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
        }

        public String getBankAccountName() {
            return bankAccountName;
        }

        public void setBankAccountName(String bankAccountName) {
            this.bankAccountName = bankAccountName;
        }
    }

}
