package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.OrderCancelOperateRes;
/*商家审核用户取消申请接口*/
public class OrderCancelOperateReq extends Request<OrderCancelOperateRes> {
    private String orderId;
    private Boolean isAgreed;
    private String operator;
    private String remark;//操作备注(isAgreed=false时此字段必填，isAgreed=true时可不填)

    public java.lang.String getOrderId() {
        return orderId;
    }

    public void setOrderId(java.lang.String orderId) {
        this.orderId = orderId;
    }

    public Boolean getIsAgreed() {
        return isAgreed;
    }

    public void setIsAgreed(Boolean isAgreed) {
        this.isAgreed = isAgreed;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public java.lang.String getRemark() {
        return remark;
    }

    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/ocs/orderCancelOperate";
    }

    @Override
    public Class<OrderCancelOperateRes> getResponseClass() {
        return OrderCancelOperateRes.class;
    }
}
