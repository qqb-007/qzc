package shansong.request;

import shansong.response.SendOrderResponse;

public class SendOrderRequest extends Request<SendOrderResponse> {

    private String issOrderNo;

    public String getIssOrderNo() {
        return issOrderNo;
    }

    public void setIssOrderNo(String issOrderNo) {
        this.issOrderNo = issOrderNo;
    }

    @Override
    public String getUrl() {
        return "/openapi/developer/v5/orderPlace";
    }

    @Override
    public Class<SendOrderResponse> getResponseClass() {
        return SendOrderResponse.class;
    }
}
