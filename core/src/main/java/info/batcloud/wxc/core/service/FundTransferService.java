package info.batcloud.wxc.core.service;

public interface FundTransferService {

    FundTransferResult transferFund(FundTransferParam param);

    class FundTransferParam {
        private String payeeRealName;
        private float amount;
        private String payeeAccount;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPayeeRealName() {
            return payeeRealName;
        }

        public void setPayeeRealName(String payeeRealName) {
            this.payeeRealName = payeeRealName;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getPayeeAccount() {
            return payeeAccount;
        }

        public void setPayeeAccount(String payeeAccount) {
            this.payeeAccount = payeeAccount;
        }
    }


    class FundTransferResult {
        private boolean success;
        private long fundTransferOrderId;
        private String alipayOrderId;
        private String payDate;
        private String errorMsg;

        public String getAlipayOrderId() {
            return alipayOrderId;
        }

        public void setAlipayOrderId(String alipayOrderId) {
            this.alipayOrderId = alipayOrderId;
        }

        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public long getFundTransferOrderId() {
            return fundTransferOrderId;
        }

        public void setFundTransferOrderId(long fundTransferOrderId) {
            this.fundTransferOrderId = fundTransferOrderId;
        }
    }

}
