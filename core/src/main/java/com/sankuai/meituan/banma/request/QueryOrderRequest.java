package com.sankuai.meituan.banma.request;

import com.sankuai.meituan.banma.response.QueryOrderResponse;

/**
 * 查询订单参数
 */
public class QueryOrderRequest extends AbstractRequest<QueryOrderResponse> {

    /**
     * 配送活动标识
     */
    private Long deliveryId;

    /**
     * 美团配送内部订单id，最长不超过32个字符
     */
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
        return "QueryOrderRequest [" +
                "deliveryId=" + deliveryId +
                ", mtPeisongId=" + mtPeisongId + "]";
    }

    @Override
    public String getEndpoint() {
        return "/order/status/query";
    }

    @Override
    public Class<QueryOrderResponse> getResponseType() {
        return QueryOrderResponse.class;
    }
}
