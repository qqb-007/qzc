package info.batcloud.wxc.core.domain.meituan;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderRefundRequest {

    @JSONField(name = "order_id")
    private long orderId;

    @JSONField(name = "notify_type")
    private String notifyType;

    private String reason;

    @JSONField(name = "res_type")
    private int resType;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getResType() {
        return resType;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }
}
