package me.ele.sdk.up.request;

import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.ShopStatusResponse;

public class ShopStatusRequest extends Request<ShopStatusResponse> {
    private String shop_id;
    private String platformFlag;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getPlatformFlag() {
        return platformFlag;
    }

    public void setPlatformFlag(String platformFlag) {
        this.platformFlag = platformFlag;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "shop.busstatus.get";
    }

    @Override
    public Class<ShopStatusResponse> getResponseClass() {
        return ShopStatusResponse.class;
    }
}
