package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.OrderAcceptRes;

public class OrderAcceptReq extends Request<OrderAcceptRes> {
    private String orderId;
    private Boolean isAgreed;
    private String operator;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getIsAgreed() {
        return isAgreed;
    }

    public void setIsAgreed(Boolean isAgreed) {
        this.isAgreed = isAgreed;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/ocs/orderAcceptOperate";
    }

    @Override
    public Class<OrderAcceptRes> getResponseClass() {
        return OrderAcceptRes.class;
    }
}
