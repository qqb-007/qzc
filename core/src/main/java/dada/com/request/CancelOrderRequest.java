package dada.com.request;

import com.alibaba.fastjson.annotation.JSONField;
import dada.com.response.CancelOrderResponse;

public class CancelOrderRequest extends Request<CancelOrderResponse> {

    @JSONField(name = "order_id")
    private String orderId;

    @JSONField(name = "cancel_reason_id")
    private String cancelReasonId;

    @JSONField(name = "cancel_reason")
    private String cancelReason;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(String cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String toString() {
        return "CancelOrderRequest{" +
                "orderId='" + orderId + '\'' +
                ", cancelReasonId='" + cancelReasonId + '\'' +
                ", cancelReason='" + cancelReason + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/api/order/formalCancel";
    }

    @Override
    public Class<CancelOrderResponse> getResponseClass() {
        return CancelOrderResponse.class;
    }
}
