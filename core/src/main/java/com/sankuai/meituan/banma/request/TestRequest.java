package com.sankuai.meituan.banma.request;

import com.sankuai.meituan.banma.annotation.Param;
import com.sankuai.meituan.banma.response.TestResponse;

/**
 * Created by gw on 2018/1/26.
 */
public class TestRequest extends AbstractRequest<TestResponse> {

    private String endpoint;

    @Param("mt_peisong_id")
    private String mtPeisongId;

    @Param("delivery_id")
    private Long deliveryId;

    public String getMtPeisongId() {
        return mtPeisongId;
    }

    public void setMtPeisongId(String mtPeisongId) {
        this.mtPeisongId = mtPeisongId;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public Class getResponseType() {
        return TestResponse.class;
    }
}
