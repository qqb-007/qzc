package dada.com.request;

import com.alibaba.fastjson.annotation.JSONField;
import dada.com.response.SendOrderResponse;

public class SendOrderRequest extends Request<SendOrderResponse> {

    private String deliveryNo;

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    @Override
    public String toString() {
        return "SendOrderRequest{" +
                "deliveryNo='" + deliveryNo + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/api/order/addAfterQuery";
    }

    @Override
    public Class<SendOrderResponse> getResponseClass() {
        return SendOrderResponse.class;
    }
}
