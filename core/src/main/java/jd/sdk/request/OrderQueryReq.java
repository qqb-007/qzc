package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.OrderQueryRes;

public class OrderQueryReq extends Request<OrderQueryRes> {
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/order/es/query";
    }

    @Override
    public Class<OrderQueryRes> getResponseClass() {
        return OrderQueryRes.class;
    }
}
