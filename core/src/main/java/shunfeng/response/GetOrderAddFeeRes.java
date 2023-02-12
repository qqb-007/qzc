package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class GetOrderAddFeeRes extends Response<GetOrderAddFeeRes.Result> {
    public static class Result {
        @JSONField(name = "sf_order_id")
        private String sfOrderId;
        @JSONField(name = "sf_order_id")
        private String shopOrderId;
        @JSONField(name = "total_gratuity_fee")
        private Double totalGratuityFee;
        @JSONField(name = "total_gratuity_times")
        private int totalGratuityTimes;
        @JSONField(name = "gratuity_fee_list")
        private List<AddInfo> gratuityFeeList;

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

        public Double getTotalGratuityFee() {
            return totalGratuityFee;
        }

        public void setTotalGratuityFee(Double totalGratuityFee) {
            this.totalGratuityFee = totalGratuityFee;
        }

        public int getTotalGratuityTimes() {
            return totalGratuityTimes;
        }

        public void setTotalGratuityTimes(int totalGratuityTimes) {
            this.totalGratuityTimes = totalGratuityTimes;
        }

        public List<AddInfo> getGratuityFeeList() {
            return gratuityFeeList;
        }

        public void setGratuityFeeList(List<AddInfo> gratuityFeeList) {
            this.gratuityFeeList = gratuityFeeList;
        }
    }

    public static class AddInfo {
        @JSONField(name = "gratuity_fee")
        private Double gratuityFee;
        @JSONField(name = "gratuity_time")
        private Long gratuityTime;

        public Double getGratuityFee() {
            return gratuityFee;
        }

        public void setGratuityFee(Double gratuityFee) {
            this.gratuityFee = gratuityFee;
        }

        public Long getGratuityTime() {
            return gratuityTime;
        }

        public void setGratuityTime(Long gratuityTime) {
            this.gratuityTime = gratuityTime;
        }
    }
}
