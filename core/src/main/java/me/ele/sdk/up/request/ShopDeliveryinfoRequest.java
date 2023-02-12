package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.ShopDeliveryinfoResponse;

public class ShopDeliveryinfoRequest extends Request<ShopDeliveryinfoResponse> {

    @JSONField(name = "shop_id")
    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "shop.deliveryinfo.get";
    }

    @Override
    public Class<ShopDeliveryinfoResponse> getResponseClass() {
        return ShopDeliveryinfoResponse.class;
    }
}
