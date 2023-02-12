package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.ModifySellerDeliveryRes;

public class ModifySellerDeliveryReq extends Request<ModifySellerDeliveryRes> {
    //订单达达配送转商家自送接口，运单状态为待抢单且超时5分钟，可以调用此接口转商家自送
    private String orderId;
    private String updatePin;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUpdatePin() {
        return updatePin;
    }

    public void setUpdatePin(String updatePin) {
        this.updatePin = updatePin;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/order/modifySellerDelivery";
    }

    @Override
    public Class<ModifySellerDeliveryRes> getResponseClass() {
        return ModifySellerDeliveryRes.class;
    }
}
