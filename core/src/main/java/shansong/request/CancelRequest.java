package shansong.request;

import shansong.response.CancelResponse;

public class CancelRequest extends Request<CancelResponse> {

    private String issOrderNo;


    public String getIssOrderNo() {
        return issOrderNo;
    }

    public void setIssOrderNo(String issOrderNo) {
        this.issOrderNo = issOrderNo;
    }

    @Override
    public String toString() {
        return "CancelRequest{" +
                "issOrderNo='" + issOrderNo + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/openapi/developer/v5/abortOrder";
    }

    @Override
    public Class<CancelResponse> getResponseClass() {
        return CancelResponse.class;
    }
}
