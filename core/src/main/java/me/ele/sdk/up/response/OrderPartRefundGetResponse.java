package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

import java.util.List;

public class OrderPartRefundGetResponse extends Response<OrderPartRefundGetResponse.Data, String> {


    public static class Data {
        @JSONField(name = "order_id")
        private String orderId;
        private Integer type;
        @JSONField(name = "total_price")
        private Float totalPrice;
        @JSONField(name = "refund_order_id")
        private String refundOrderId;
        @JSONField(name = "user_fee")
        private Float userFee;
        @JSONField(name = "shop_fee")
        private Float shopFee;
        @JSONField(name = "package_fee")
        private Float packageFee;
        @JSONField(name = "send_fee")
        private Float sendFee;
        private Float fee;
        private Float commission;
        @JSONField(name = "fund_calculate_type")
        private Integer fundCalculateType;
        @JSONField(name = "refund_price")
        private Float refundPrice;
        @JSONField(name = "history_refund_detail")
        private List<List<RefundDetail>> historyRefundDetail;
        @JSONField(name = "refund_detail")
        private List<RefundDetail> refundDetails;

        public List<RefundDetail> getRefundDetails() {
            return refundDetails;
        }

        public void setRefundDetails(List<RefundDetail> refundDetails) {
            this.refundDetails = refundDetails;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Float getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Float totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Float getUserFee() {
            return userFee;
        }

        public void setUserFee(Float userFee) {
            this.userFee = userFee;
        }

        public Float getShopFee() {
            return shopFee;
        }

        public void setShopFee(Float shopFee) {
            this.shopFee = shopFee;
        }

        public Float getPackageFee() {
            return packageFee;
        }

        public void setPackageFee(Float packageFee) {
            this.packageFee = packageFee;
        }

        public Float getSendFee() {
            return sendFee;
        }

        public void setSendFee(Float sendFee) {
            this.sendFee = sendFee;
        }

        public Float getFee() {
            return fee;
        }

        public void setFee(Float fee) {
            this.fee = fee;
        }

        public Float getCommission() {
            return commission;
        }

        public void setCommission(Float commission) {
            this.commission = commission;
        }

        public Float getRefundPrice() {
            return refundPrice;
        }

        public void setRefundPrice(Float refundPrice) {
            this.refundPrice = refundPrice;
        }

        public List<List<RefundDetail>> getHistoryRefundDetail() {
            return historyRefundDetail;
        }

        public void setHistoryRefundDetail(List<List<RefundDetail>> historyRefundDetail) {
            this.historyRefundDetail = historyRefundDetail;
        }

        public String getRefundOrderId() {
            return refundOrderId;
        }

        public void setRefundOrderId(String refundOrderId) {
            this.refundOrderId = refundOrderId;
        }

        public Integer getFundCalculateType() {
            return fundCalculateType;
        }

        public void setFundCalculateType(Integer fundCalculateType) {
            this.fundCalculateType = fundCalculateType;
        }
    }

    public static class RefundDetail {
        @JSONField(name = "refund_id")
        private String refundId;
        @JSONField(name = "apply_time")
        private Long applyTime;
        private Integer status;
        private String desc;
        @JSONField(name = "sku_id")
        private String skuId;
        private String upc;
        @JSONField(name = "custom_sku_id")
        private String customSkuId;
        private Integer type;
        private String name;
        private Integer number;
        @JSONField(name = "history_fund_calculate_type")
        private Integer historyFundCalculateType;
        @JSONField(name = "total_refund")
        private Float totalRefund;
        @JSONField(name = "shop_ele_refund")
        private Float shopEleRefund;

        public String getRefundId() {
            return refundId;
        }

        public void setRefundId(String refundId) {
            this.refundId = refundId;
        }

        public Long getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(Long applyTime) {
            this.applyTime = applyTime;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getUpc() {
            return upc;
        }

        public void setUpc(String upc) {
            this.upc = upc;
        }

        public String getCustomSkuId() {
            return customSkuId;
        }

        public void setCustomSkuId(String customSkuId) {
            this.customSkuId = customSkuId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public Float getTotalRefund() {
            return totalRefund;
        }

        public void setTotalRefund(Float totalRefund) {
            this.totalRefund = totalRefund;
        }

        public Float getShopEleRefund() {
            return shopEleRefund;
        }

        public void setShopEleRefund(Float shopEleRefund) {
            this.shopEleRefund = shopEleRefund;
        }

        public Integer getHistoryFundCalculateType() {
            return historyFundCalculateType;
        }

        public void setHistoryFundCalculateType(Integer historyFundCalculateType) {
            this.historyFundCalculateType = historyFundCalculateType;
        }
    }

}
