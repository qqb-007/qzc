package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.OrderSerllerDeliveryRes;

public class OrderSerllerDeliveryReq extends Request<OrderSerllerDeliveryRes> {
    private String orderId;
    private String operator;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
        return "https://openapi.jddj.com/djapi/bm/open/api/order/OrderSerllerDelivery";
    }

    @Override
    public Class<OrderSerllerDeliveryRes> getResponseClass() {
        return OrderSerllerDeliveryRes.class;
    }
}
