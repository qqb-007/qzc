package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.ShopOpenResponse;

public class ShopOpenRequest extends Request<ShopOpenResponse> {

    @JSONField(name = "shop_id")
    private String shopId;

    @JSONField(name = "baidu_shop_id")
    private String baiduShopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBaiduShopId() {
        return baiduShopId;
    }

    public void setBaiduShopId(String baiduShopId) {
        this.baiduShopId = baiduShopId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "shop.open";
    }

    @Override
    public Class<ShopOpenResponse> getResponseClass() {
        return ShopOpenResponse.class;
    }
}
