package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.CancelAndRefundRes;

import java.util.Date;

//订单取消且退款接口
public class CancelAndRefundReq extends Request<CancelAndRefundRes> {
    private Long orderId;
    private String operPin;
    private String operRemark;
    private String operTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOperPin() {
        return operPin;
    }

    public void setOperPin(String operPin) {
        this.operPin = operPin;
    }

    public String getOperRemark() {
        return operRemark;
    }

    public void setOperRemark(String operRemark) {
        this.operRemark = operRemark;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/orderStatus/cancelAndRefund";
    }

    @Override
    public Class<CancelAndRefundRes> getResponseClass() {
        return CancelAndRefundRes.class;
    }
}
