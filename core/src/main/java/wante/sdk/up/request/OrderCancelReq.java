package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.OrderCancelRes;

public class OrderCancelReq extends AbstractRequest<OrderCancelRes> {

    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getURL() {
        return "http://order.mall.wangxiaocai.cn/api/product_order/cancel_order/" + this.orderId;
    }

    @Override
    public Boolean isJson() {
        return false;
    }

    @Override
    public String getJson() {
        return null;
    }

    @Override
    public String getSecret() {
        return "Basic bWFsbC1vcmRlcjpMOUlCb0pkMUpSdTViVHlv";
    }

    @Override
    public Class<OrderCancelRes> getResponseType() {
        return OrderCancelRes.class;
    }


}
