package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.GetAfsSeriveOrderListRes;

public class GetAfsSeriveOrderListReq extends Request<GetAfsSeriveOrderListRes> {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/afs/getAfsSeriveOrderList";
    }

    @Override
    public Class<GetAfsSeriveOrderListRes> getResponseClass() {
        return GetAfsSeriveOrderListRes.class;
    }
}
