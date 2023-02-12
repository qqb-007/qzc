package dada.com.request;

import com.alibaba.fastjson.annotation.JSONField;
import dada.com.response.OrderInfoResponse;

public class OrderInfoRequest extends Request<OrderInfoResponse> {
    @JSONField(name = "order_id")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderInfoRequest{" +
                "orderId='" + orderId + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/api/order/status/query";
    }

    @Override
    public Class<OrderInfoResponse> getResponseClass() {
        return OrderInfoResponse.class;
    }
}
