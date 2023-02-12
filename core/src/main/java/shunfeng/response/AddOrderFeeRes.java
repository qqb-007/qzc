package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

public class AddOrderFeeRes extends Response<AddOrderFeeRes.Result> {
    public static class Result {
        @JSONField(name = "sf_order_id")
        private String sfOrderId;
        @JSONField(name = "shop_order_id")
        private String shopOrderId;
        @JSONField(name = "gratuity_fee")
        private Double gratuityFee;
        @JSONField(name = "total_gratuity_fee")
        private Double totalGratuityFee;

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

        public Double getGratuityFee() {
            return gratuityFee;
        }

        public void setGratuityFee(Double gratuityFee) {
            this.gratuityFee = gratuityFee;
        }

        public Double getTotalGratuityFee() {
            return totalGratuityFee;
        }

        public void setTotalGratuityFee(Double totalGratuityFee) {
            this.totalGratuityFee = totalGratuityFee;
        }
    }
}
