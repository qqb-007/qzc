package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.RefundRejectReponse;

public class RefundRejectRequest extends Request<RefundRejectReponse> {
    @JSONField(name = "order_id")
    private String orderId;

    @JSONField(name = "refund_order_id")
    private String refundOrderId;

    @JSONField(name = "refuse_reason")
    private String reason;

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "order.disagreerefund";
    }

    @Override
    public Class<RefundRejectReponse> getResponseClass() {
        return RefundRejectReponse.class;
    }
}
