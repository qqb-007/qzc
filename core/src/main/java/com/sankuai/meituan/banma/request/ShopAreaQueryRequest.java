package com.sankuai.meituan.banma.request;

import com.sankuai.meituan.banma.annotation.Param;
import com.sankuai.meituan.banma.response.ShopAreaQueryResponse;

/**
 * 创建门店id
 */
public class ShopAreaQueryRequest extends AbstractRequest<ShopAreaQueryResponse> {

    @Param("delivery_service_code")
    private int deliveryServiceCode;

    @Param("shop_id")
    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getDeliveryServiceCode() {
        return deliveryServiceCode;
    }

    public void setDeliveryServiceCode(int deliveryServiceCode) {
        this.deliveryServiceCode = deliveryServiceCode;
    }

    @Override
    public String getEndpoint() {
        return "/shop/area/query";
    }

    @Override
    public Class getResponseType() {
        return ShopAreaQueryResponse.class;
    }
}
