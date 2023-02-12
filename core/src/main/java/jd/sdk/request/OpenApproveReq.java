package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.OpenApproveRes;

/*根据售后服务单号对申请售后单审核，要求商家自新的售后单申请时起，门店营业时间内申请的售后单，需要在2个小时内审核完成；门店营业时间外申请的售后单，需要在第二天门店营业开始时间+2小时内审核完成，超时未审核的系统自动审核为退款。*/
public class OpenApproveReq extends Request<OpenApproveRes> {
    private String serviceOrder;//售后服务单号
    private Integer approveType;//审核结果类型（1：退款 2：退货 3：驳回）
    private String rejectReason;//驳回原因(审核为驳回时必须，审核为退货或退款时不传)
    private String optPin;

    public String getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(String serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public Integer getApproveType() {
        return approveType;
    }

    public void setApproveType(Integer approveType) {
        this.approveType = approveType;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getOptPin() {
        return optPin;
    }

    public void setOptPin(String optPin) {
        this.optPin = optPin;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/afs/afsOpenApprove";
    }

    @Override
    public Class<OpenApproveRes> getResponseClass() {
        return OpenApproveRes.class;
    }
}
