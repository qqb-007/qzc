package jd.sdk.response;

import jd.sdk.Response;

public class DeliveryEndOrderRes extends Response<DeliveryEndOrderRes.Data> {
    public static class Data{
        private String code;
        private String msg;
        private OrderMainDTO result;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public OrderMainDTO getResult() {
            return result;
        }

        public void setResult(OrderMainDTO result) {
            this.result = result;
        }
    }
    public static class OrderMainDTO{
        private Long orderId;
        private Integer orderStatus;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}
