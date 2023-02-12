package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;
import shunfeng.response.GetSfLocationRes;

public class GetSfLocationReq extends Request<GetSfLocationRes> {

    @JSONField(name = "order_id")
    private String orderId;

    @Override
    public String toString() {
        return "GetSfLocationReq{" +
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
        return "riderlatestposition";
    }

    @Override
    public Class<GetSfLocationRes> getResponseType() {
        return GetSfLocationRes.class;
    }
}
