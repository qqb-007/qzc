package com.sankuai.meituan.banma.request;

import com.sankuai.meituan.banma.annotation.Param;
import com.sankuai.meituan.banma.response.RiderLocationResponse;

public class RiderLocationRequest extends AbstractRequest<RiderLocationResponse> {

    /**
     * 配送活动标识
     */
    @Param("delivery_id")
    private Long deliveryId;

    /**
     * 美团配送内部订单id，最长不超过32个字符
     */
    @Param("mt_peisong_id")
    private String mtPeisongId;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getMtPeisongId() {
        return mtPeisongId;
    }

    public void setMtPeisongId(String mtPeisongId) {
        this.mtPeisongId = mtPeisongId;
    }

    @Override
    public String toString() {
        return "RiderLocationRequest{" +
                "deliveryId=" + deliveryId +
                ", mtPeisongId='" + mtPeisongId + '\'' +
                '}';
    }

    @Override
    public String getEndpoint() {
        return "/order/rider/location";
    }

    @Override
    public Class<RiderLocationResponse> getResponseType() {
        return RiderLocationResponse.class;
    }
}
