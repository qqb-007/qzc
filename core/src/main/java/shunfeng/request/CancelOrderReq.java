package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;
import shunfeng.response.CancelOrderRes;

public class CancelOrderReq extends Request<CancelOrderRes> {
    @JSONField(name = "order_id")
    private String orderId;
    @JSONField(name = "order_type")
    private int orderType;//1 顺丰订单号 2、商家订单号

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    @Override
    public String getEndpoint() {
        return "cancelorder";
    }

    @Override
    public Class<CancelOrderRes> getResponseType() {
        return CancelOrderRes.class;
    }
}
