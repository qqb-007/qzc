package jd.sdk.response;

import jd.sdk.Response;

import java.util.List;

public class GetAfsServiceRes extends Response<GetAfsServiceRes.Data> {
    public static class Data {
        private Integer code;
        private String msg;
        private Boolean success;
        private AfsServiceResponse result;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public AfsServiceResponse getResult() {
            return result;
        }

        public void setResult(AfsServiceResponse result) {
            this.result = result;
        }
    }

    public static class AfsServiceResponse {
        private String orderId;
        private String newOrderId;
        private String afsServiceOrder;
        private Integer afsServiceState;
        private Integer approveType;
        private Long approvedDate;
        private String approvePin;
        private Integer questionTypeCid;
        private String questionDesc;
        private Long cashMoney;
        private Long afsMoney;
        private Long jdBeansMoney;
        private Long virtualMoney;
        private String applyDeal;
        private List<AfsServiceDetail> afsDetailList;
        private Long orderFreightMoney;
        private Long refundUserMmoney;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getNewOrderId() {
            return newOrderId;
        }

        public void setNewOrderId(String newOrderId) {
            this.newOrderId = newOrderId;
        }

        public String getAfsServiceOrder() {
            return afsServiceOrder;
        }

        public void setAfsServiceOrder(String afsServiceOrder) {
            this.afsServiceOrder = afsServiceOrder;
        }

        public Integer getAfsServiceState() {
            return afsServiceState;
        }

        public void setAfsServiceState(Integer afsServiceState) {
            this.afsServiceState = afsServiceState;
        }

        public java.lang.Integer getApproveType() {
            return approveType;
        }

        public void setApproveType(java.lang.Integer approveType) {
            this.approveType = approveType;
        }

        public Long getApprovedDate() {
            return approvedDate;
        }

        public void setApprovedDate(Long approvedDate) {
            this.approvedDate = approvedDate;
        }

        public String getApprovePin() {
            return approvePin;
        }

        public void setApprovePin(String approvePin) {
            this.approvePin = approvePin;
        }

        public java.lang.Integer getQuestionTypeCid() {
            return questionTypeCid;
        }

        public void setQuestionTypeCid(java.lang.Integer questionTypeCid) {
            this.questionTypeCid = questionTypeCid;
        }

        public String getQuestionDesc() {
            return questionDesc;
        }

        public void setQuestionDesc(String questionDesc) {
            this.questionDesc = questionDesc;
        }

        public Long getCashMoney() {
            return cashMoney;
        }

        public void setCashMoney(Long cashMoney) {
            this.cashMoney = cashMoney;
        }

        public Long getAfsMoney() {
            return afsMoney;
        }

        public void setAfsMoney(Long afsMoney) {
            this.afsMoney = afsMoney;
        }

        public Long getJdBeansMoney() {
            return jdBeansMoney;
        }

        public void setJdBeansMoney(Long jdBeansMoney) {
            this.jdBeansMoney = jdBeansMoney;
        }

        public Long getVirtualMoney() {
            return virtualMoney;
        }

        public void setVirtualMoney(Long virtualMoney) {
            this.virtualMoney = virtualMoney;
        }

        public String getApplyDeal() {
            return applyDeal;
        }

        public void setApplyDeal(String applyDeal) {
            this.applyDeal = applyDeal;
        }

        public List<AfsServiceDetail> getAfsDetailList() {
            return afsDetailList;
        }

        public void setAfsDetailList(List<AfsServiceDetail> afsDetailList) {
            this.afsDetailList = afsDetailList;
        }

        public Long getOrderFreightMoney() {
            return orderFreightMoney;
        }

        public void setOrderFreightMoney(Long orderFreightMoney) {
            this.orderFreightMoney = orderFreightMoney;
        }

        public Long getRefundUserMmoney() {
            return refundUserMmoney;
        }

        public void setRefundUserMmoney(Long refundUserMmoney) {
            this.refundUserMmoney = refundUserMmoney;
        }
    }

    public static class AfsServiceDetail {
        private String wareName;
        private String skuIdIsv;
        private Double weight;
        private Long payPrice;
        private Integer skuCount;
        private Long skuMoney;
        private Long mealBoxMoney;
        private Long afsMoney;
        private Long cashMoney;

        public Long getCashMoney() {
            return cashMoney;
        }

        public void setCashMoney(Long cashMoney) {
            this.cashMoney = cashMoney;
        }

        public String getWareName() {
            return wareName;
        }

        public void setWareName(String wareName) {
            this.wareName = wareName;
        }

        public String getSkuIdIsv() {
            return skuIdIsv;
        }

        public void setSkuIdIsv(String skuIdIsv) {
            this.skuIdIsv = skuIdIsv;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public Long getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(Long payPrice) {
            this.payPrice = payPrice;
        }

        public Integer getSkuCount() {
            return skuCount;
        }

        public void setSkuCount(Integer skuCount) {
            this.skuCount = skuCount;
        }

        public Long getSkuMoney() {
            return skuMoney;
        }

        public void setSkuMoney(Long skuMoney) {
            this.skuMoney = skuMoney;
        }

        public Long getMealBoxMoney() {
            return mealBoxMoney;
        }

        public void setMealBoxMoney(Long mealBoxMoney) {
            this.mealBoxMoney = mealBoxMoney;
        }

        public Long getAfsMoney() {
            return afsMoney;
        }

        public void setAfsMoney(Long afsMoney) {
            this.afsMoney = afsMoney;
        }
    }
}
