package jd.sdk.response;

import jd.sdk.Response;
import org.hibernate.dialect.Ingres9Dialect;

import java.util.List;

public class AdjustOrderRes extends Response<AdjustOrderRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private String detail;
        private OrderAdjustRespDTO result;

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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public OrderAdjustRespDTO getResult() {
            return result;
        }

        public void setResult(OrderAdjustRespDTO result) {
            this.result = result;
        }
    }

    public static class OrderAdjustRespDTO {
        private Long orderId;
        private Integer adjustCount;
        private String ProduceStationNo;
        private Long orderTotalMoney;
        private Long orderDiscountMoney;
        private Long orderFreightMoney;
        private Long orderBuyerPayableMoney;
        private List<Detil> oaList;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Integer getAdjustCount() {
            return adjustCount;
        }

        public void setAdjustCount(Integer adjustCount) {
            this.adjustCount = adjustCount;
        }

        public String getProduceStationNo() {
            return ProduceStationNo;
        }

        public void setProduceStationNo(String produceStationNo) {
            ProduceStationNo = produceStationNo;
        }

        public Long getOrderTotalMoney() {
            return orderTotalMoney;
        }

        public void setOrderTotalMoney(Long orderTotalMoney) {
            this.orderTotalMoney = orderTotalMoney;
        }

        public Long getOrderDiscountMoney() {
            return orderDiscountMoney;
        }

        public void setOrderDiscountMoney(Long orderDiscountMoney) {
            this.orderDiscountMoney = orderDiscountMoney;
        }

        public Long getOrderFreightMoney() {
            return orderFreightMoney;
        }

        public void setOrderFreightMoney(Long orderFreightMoney) {
            this.orderFreightMoney = orderFreightMoney;
        }

        public Long getOrderBuyerPayableMoney() {
            return orderBuyerPayableMoney;
        }

        public void setOrderBuyerPayableMoney(Long orderBuyerPayableMoney) {
            this.orderBuyerPayableMoney = orderBuyerPayableMoney;
        }

        public List<Detil> getOaList() {
            return oaList;
        }

        public void setOaList(List<Detil> oaList) {
            this.oaList = oaList;
        }
    }

    public static class Detil {
        private Long skuId;
        private Integer skuCount;
        private String skuName;
        private Long skuJdPrice;
        private String outSkuId;
        private String upc;

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public Integer getSkuCount() {
            return skuCount;
        }

        public void setSkuCount(Integer skuCount) {
            this.skuCount = skuCount;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public Long getSkuJdPrice() {
            return skuJdPrice;
        }

        public void setSkuJdPrice(Long skuJdPrice) {
            this.skuJdPrice = skuJdPrice;
        }

        public String getOutSkuId() {
            return outSkuId;
        }

        public void setOutSkuId(String outSkuId) {
            this.outSkuId = outSkuId;
        }

        public String getUpc() {
            return upc;
        }

        public void setUpc(String upc) {
            this.upc = upc;
        }
    }
}
