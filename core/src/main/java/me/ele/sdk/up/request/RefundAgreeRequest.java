package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.RefundAgreeResponse;

public class RefundAgreeRequest extends Request<RefundAgreeResponse> {
    @JSONField(name = "order_id")
    private String orderId;

    @JSONField(name = "refund_order_id")
    private String refundOrderId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRefundOrderId() {
        return refundOrderId;
    }

    public void setRefundOrderId(String refundOrderId) {
        this.refundOrderId = refundOrderId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "order.agreerefund";
    }

    @Override
    public Class<RefundAgreeResponse> getResponseClass() {
        return RefundAgreeResponse.class;
    }
}
