package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.SelfDeliveryStateSyncResponse;

public class SelfDeliveryStateSyncRequest extends Request<SelfDeliveryStateSyncResponse> {

    @JSONField(name = "distributor_id")
    private Integer distributorId;

    @JSONField(name = "order_id")
    private String orderId;

    private Integer state;

    private Integer selfStatus;

    private Knight knight;

    public Integer getSelfStatus() {
        return selfStatus;
    }

    public void setSelfStatus(Integer selfStatus) {
        this.selfStatus = selfStatus;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Knight getKnight() {
        return knight;
    }

    public void setKnight(Knight knight) {
        this.knight = knight;
    }

    public static class Knight {

        private String name;

        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "order.selfDeliveryStateSync";
    }

    @Override
    public Class<SelfDeliveryStateSyncResponse> getResponseClass() {
        return SelfDeliveryStateSyncResponse.class;
    }
}
