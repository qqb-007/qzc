package shansong.request;

import shansong.response.AddFeeResponse;

public class AddFeeRequest extends Request<AddFeeResponse> {

    private String issOrderNo;

    private String additionAmount;

    public String getIssOrderNo() {
        return issOrderNo;
    }

    public void setIssOrderNo(String issOrderNo) {
        this.issOrderNo = issOrderNo;
    }

    public String getAdditionAmount() {
        return additionAmount;
    }

    public void setAdditionAmount(String additionAmount) {
        this.additionAmount = additionAmount;
    }

    @Override
    public String toString() {
        return "AddFeeRequest{" +
                "issOrderNo='" + issOrderNo + '\'' +
                ", additionAmount='" + additionAmount + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/openapi/developer/v5/addition";
    }

    @Override
    public Class<AddFeeResponse> getResponseClass() {
        return AddFeeResponse.class;
    }
}
