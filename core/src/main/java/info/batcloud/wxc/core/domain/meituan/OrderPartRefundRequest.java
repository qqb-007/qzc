package info.batcloud.wxc.core.domain.meituan;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class OrderPartRefundRequest {

    @JSONField(name = "order_id")
    private long orderId;

    @JSONField(name = "notify_type")
    private String notifyType;

    private String reason;

    @JSONField(name = "res_type")
    private int resType;

    @JSONField(name = "money")
    private float money;

    private String food;

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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
