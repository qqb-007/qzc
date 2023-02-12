package com.sankuai.meituan.banma.request;

import com.sankuai.meituan.banma.annotation.Param;
import com.sankuai.meituan.banma.constants.CancelOrderReasonId;
import com.sankuai.meituan.banma.response.CancelOrderResponse;

/**
 * 取消订单参数
 */
public class CancelOrderRequest extends AbstractRequest<CancelOrderResponse> {
    /**
     * 配送活动标识
     */
    @Param("delivery_id")
    private long deliveryId;

    /**
     * 美团配送内部订单id，最长不超过32个字符
     */
    @Param("mt_peisong_id")
    private String mtPeisongId;

    /**
     * 取消原因类别，默认为接入方原因
     */
    @Param("cancel_order_reason_id")
    private CancelOrderReasonId cancelOrderReasonId;

    /**
     * 详细取消原因，最长不超过256个字符
     */
    @Param("cancel_reason")
    private String cancelReason;


    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getMtPeisongId() {
        return mtPeisongId;
    }

    public void setMtPeisongId(String mtPeisongId) {
        this.mtPeisongId = mtPeisongId;
    }

    public CancelOrderReasonId getCancelOrderReasonId() {
        return cancelOrderReasonId;
    }

    public void setCancelOrderReasonId(CancelOrderReasonId cancelOrderReasonId) {
        this.cancelOrderReasonId = cancelOrderReasonId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public String toString() {
        return "CancelOrderRequest [deliveryId=" + deliveryId + ", mtPeisongId="
                + mtPeisongId + ", cancelOrderReasonId=" + cancelOrderReasonId
                + ", cancelReason=" + cancelReason + "]";
    }

    @Override
    public String getEndpoint() {
        return "/order/delete";
    }

    @Override
    public Class<CancelOrderResponse> getResponseType() {
        return CancelOrderResponse.class;
    }
}
