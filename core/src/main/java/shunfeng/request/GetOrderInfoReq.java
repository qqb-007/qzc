package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;
import shunfeng.response.GetOrderInfoRes;

public class GetOrderInfoReq extends Request<GetOrderInfoRes> {

    @JSONField(name = "order_id")
    private String orderId;

    @Override
    public String toString() {
        return "GetOrderInfoReq{" +
                "orderId='" + orderId + '\'' +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String getEndpoint() {
        return "getorderstatus";
    }

    @Override
    public Class<GetOrderInfoRes> getResponseType() {
        return GetOrderInfoRes.class;
    }
}
