package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.OrderConfirmResponse;
import me.ele.sdk.up.response.OrderGetResponse;

public class OrderConfirmRequest extends Request<OrderConfirmResponse> {

    @JSONField(name = "order_id")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "order.confirm";
    }

    @Override
    public Class<OrderConfirmResponse> getResponseClass() {
        return OrderConfirmResponse.class;
    }
}
