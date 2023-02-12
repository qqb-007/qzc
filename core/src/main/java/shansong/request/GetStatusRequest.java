package shansong.request;

import shansong.response.GetStatusResponse;

public class GetStatusRequest extends Request<GetStatusResponse> {

    private String issOrderNo;

    private String thirdOrderNo;



    public String getIssOrderNo() {
        return issOrderNo;
    }

    public void setIssOrderNo(String issOrderNo) {
        this.issOrderNo = issOrderNo;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    @Override
    public String getUrl() {
        return "/openapi/merchants/v5/orderInfo";
    }

    @Override
    public Class<GetStatusResponse> getResponseClass() {
        return GetStatusResponse.class;
    }
}
