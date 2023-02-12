package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.OrderRefundRejectRes;

public class OrderRefundRejectReq extends AbstractRequest<OrderRefundRejectRes> {
    private Integer orderId;
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

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
        return "http://order.mall.wangxiaocai.cn/api/product_order/refund/" + this.orderId + "/reject";
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
    public Class<OrderRefundRejectRes> getResponseType() {
        return OrderRefundRejectRes.class;
    }
}
