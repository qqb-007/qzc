package shansong.request;

import shansong.response.PreCancelResponse;

public class PreCancelRequest extends Request<PreCancelResponse> {
    private String issOrderNo;

    public String getIssOrderNo() {
        return issOrderNo;
    }

    public void setIssOrderNo(String issOrderNo) {
        this.issOrderNo = issOrderNo;
    }

    @Override
    public String toString() {
        return "PreCancelRequest{" +
                "issOrderNo='" + issOrderNo + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/openapi/developer/v5/preAbortOrder";
    }

    @Override
    public Class<PreCancelResponse> getResponseClass() {
        return PreCancelResponse.class;
    }
}
