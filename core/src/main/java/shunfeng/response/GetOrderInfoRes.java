package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

public class GetOrderInfoRes extends Response<GetOrderInfoRes.Result> {
    public static class Result {
        @JSONField(name = "order_status")
        private Integer orderStatus;


        @JSONField(name = "total_pay_money")
        private Integer totalPayMoney;

        @JSONField(name = "status_desc")
        private String statusDesc;//顺分价格单位是分

        public Integer getTotalPayMoney() {
            return totalPayMoney;
        }

        public void setTotalPayMoney(Integer totalPayMoney) {
            this.totalPayMoney = totalPayMoney;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getStatusDesc() {
            return statusDesc;
        }

        public void setStatusDesc(String statusDesc) {
            this.statusDesc = statusDesc;
        }
    }
}
