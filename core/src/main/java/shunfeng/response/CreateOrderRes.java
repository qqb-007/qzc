package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

public class CreateOrderRes extends Response<CreateOrderRes.Result> {
    public static class Result {
        @JSONField(name = "sf_order_id")
        private String sfOrderId;//顺丰订单号（标准默认为int，可以设置为string）
        @JSONField(name = "shop_order_id")
        private String shopOrderId;//商家订单号
        @JSONField(name = "push_time")
        private String pushTime;//推送时间
        @JSONField(name = "sf_bill_id")
        private String sfBillId;//顺丰运单号（需要设置）
        @JSONField(name = "total_price")
        private Double totalPrice;//配送费总额 单位分
        @JSONField(name = "delivery_distance_meter")
        private Double deliveryDistanceMeter;//配送距离，当return_flag中包含2时返回，单位米
        @JSONField(name = "weight_gram")
        private Double weightGram;//商品重量，当return_flag中包含4时返回，单位克（值为下单传入参数回传）
        @JSONField(name = "start_time")
        private Long startTime;//起送时间
        @JSONField(name = "expect_time")
        private Long expectTime;//期望送达时间
        @JSONField(name = "totalPayMoney")
        private Double totalPayMoney;//支付费用 单位分
        @JSONField(name = "real_pay_money")
        private Double realPayMoney;//实际支付金额 单位分
        @JSONField(name = "coupons_total_fee")
        private Double couponsTotalFee;//优惠券总金额 单位分
        @JSONField(name = "settlement_type")
        private int settlementType;//结算方式

        public String getSfOrderId() {
            return sfOrderId;
        }

        public void setSfOrderId(String sfOrderId) {
            this.sfOrderId = sfOrderId;
        }

        public String getShopOrderId() {
            return shopOrderId;
        }

        public void setShopOrderId(String shopOrderId) {
            this.shopOrderId = shopOrderId;
        }

        public String getPushTime() {
            return pushTime;
        }

        public void setPushTime(String pushTime) {
            this.pushTime = pushTime;
        }

        public String getSfBillId() {
            return sfBillId;
        }

        public void setSfBillId(String sfBillId) {
            this.sfBillId = sfBillId;
        }

        public Double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Double getDeliveryDistanceMeter() {
            return deliveryDistanceMeter;
        }

        public void setDeliveryDistanceMeter(Double deliveryDistanceMeter) {
            this.deliveryDistanceMeter = deliveryDistanceMeter;
        }

        public Double getWeightGram() {
            return weightGram;
        }

        public void setWeightGram(Double weightGram) {
            this.weightGram = weightGram;
        }

        public Long getStartTime() {
            return startTime;
        }

        public void setStartTime(Long startTime) {
            this.startTime = startTime;
        }

        public Long getExpectTime() {
            return expectTime;
        }

        public void setExpectTime(Long expectTime) {
            this.expectTime = expectTime;
        }

        public Double getTotalPayMoney() {
            return totalPayMoney;
        }

        public void setTotalPayMoney(Double totalPayMoney) {
            this.totalPayMoney = totalPayMoney;
        }

        public Double getRealPayMoney() {
            return realPayMoney;
        }

        public void setRealPayMoney(Double realPayMoney) {
            this.realPayMoney = realPayMoney;
        }

        public Double getCouponsTotalFee() {
            return couponsTotalFee;
        }

        public void setCouponsTotalFee(Double couponsTotalFee) {
            this.couponsTotalFee = couponsTotalFee;
        }

        public int getSettlementType() {
            return settlementType;
        }

        public void setSettlementType(int settlementType) {
            this.settlementType = settlementType;
        }
    }
}
