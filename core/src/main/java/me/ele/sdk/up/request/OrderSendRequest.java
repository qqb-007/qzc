package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.OrderSendResponse;

public class OrderSendRequest extends Request<OrderSendResponse> {

    @JSONField(name = "order_id")
    private String orderId;
    private String phone;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "order.sendout";
    }

    @Override
    public Class<OrderSendResponse> getResponseClass() {
        return OrderSendResponse.class;
    }
}
