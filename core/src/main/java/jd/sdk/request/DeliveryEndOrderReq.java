package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.DeliveryEndOrderRes;

import java.util.Date;
//商家自送订单模式下，订单由商家配送完成时，调用此接口，变更订单状态。
public class DeliveryEndOrderReq extends Request<DeliveryEndOrderRes> {
    private Long orderId;
    private String operPin;
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
        return "https://openapi.jddj.com/djapi/ocs/deliveryEndOrder";
    }

    @Override
    public Class<DeliveryEndOrderRes> getResponseClass() {
        return DeliveryEndOrderRes.class;
    }
}
