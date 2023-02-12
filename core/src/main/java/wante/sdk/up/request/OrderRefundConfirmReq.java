package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.OrderRefundConfirmRes;

public class OrderRefundConfirmReq extends AbstractRequest<OrderRefundConfirmRes> {
    private Integer orderId;
    private Float money;
    private String remarks;//退款备注（格式要遵守）

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

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getURL() {
        return "http://order.mall.wangxiaocai.cn/api/product_order/refund/" + this.orderId + "/confirm";
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
    public Class<OrderRefundConfirmRes> getResponseType() {
        return OrderRefundConfirmRes.class;
    }
}
