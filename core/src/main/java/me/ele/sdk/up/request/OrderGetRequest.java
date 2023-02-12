package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.OrderGetResponse;

public class OrderGetRequest extends Request<OrderGetResponse> {

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
        return "order.get";
    }

    @Override
    public Class<OrderGetResponse> getResponseClass() {
        return OrderGetResponse.class;
    }
}
