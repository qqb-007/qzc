package shansong.request;

import shansong.response.GetLocationResponse;

public class GetLocationRequest extends Request<GetLocationResponse> {
    private String issOrderNo;


    public String getIssOrderNo() {
        return issOrderNo;
    }

    public void setIssOrderNo(String issOrderNo) {
        this.issOrderNo = issOrderNo;
    }


    @Override
    public String toString() {
        return "GetLocationRequest{" +
                "issOrderNo='" + issOrderNo + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/openapi/developer/v5/courierInfo";
    }

    @Override
    public Class<GetLocationResponse> getResponseClass() {
        return GetLocationResponse.class;
    }
}
