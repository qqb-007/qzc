package jd.sdk.response;

import jd.sdk.Response;

import java.util.List;

public class GetAfsSeriveOrderListRes extends Response<GetAfsSeriveOrderListRes.Data> {
    public static class Data {
        private String msg;
        private Integer code;
        private Integer totalCount;
        private List<AfsSeriveOrder> afsSeriveOrderList;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public java.lang.Integer getCode() {
            return code;
        }

        public void setCode(java.lang.Integer code) {
            this.code = code;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public List<AfsSeriveOrder> getAfsSeriveOrderList() {
            return afsSeriveOrderList;
        }

        public void setAfsSeriveOrderList(List<AfsSeriveOrder> afsSeriveOrderList) {
            this.afsSeriveOrderList = afsSeriveOrderList;
        }
    }

    public static class AfsSeriveOrder {
        private String afsServiceOrder;
        private String orderId;
        private String newOrderId;
        private Long totalMoney;
        private String applyDeal;
        private Integer afsServiceState;

        public String getAfsServiceOrder() {
            return afsServiceOrder;
        }

        public void setAfsServiceOrder(String afsServiceOrder) {
            this.afsServiceOrder = afsServiceOrder;
        }

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

        public Long getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(Long totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getApplyDeal() {
            return applyDeal;
        }

        public void setApplyDeal(String applyDeal) {
            this.applyDeal = applyDeal;
        }

        public Integer getAfsServiceState() {
            return afsServiceState;
        }

        public void setAfsServiceState(Integer afsServiceState) {
            this.afsServiceState = afsServiceState;
        }
    }
}
