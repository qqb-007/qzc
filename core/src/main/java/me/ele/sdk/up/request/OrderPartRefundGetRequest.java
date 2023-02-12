package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.OrderPartRefundGetResponse;

public class OrderPartRefundGetRequest extends Request<OrderPartRefundGetResponse> {

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
        return "order.partrefund.get";
    }

    @Override
    public Class<OrderPartRefundGetResponse> getResponseClass() {
        return OrderPartRefundGetResponse.class;
    }
}
