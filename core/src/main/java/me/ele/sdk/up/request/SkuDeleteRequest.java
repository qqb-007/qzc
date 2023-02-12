package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.SkuDeleteResponse;

public class SkuDeleteRequest extends Request<SkuDeleteResponse> {

    @JSONField(name = "custom_sku_id")
    private String customSkuId;
    @JSONField(name = "sku_id")
    private String skuId;
    @JSONField(name = "shop_id")
    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCustomSkuId() {
        return customSkuId;
    }

    public void setCustomSkuId(String customSkuId) {
        this.customSkuId = customSkuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "sku.delete";
    }

    @Override
    public Class<SkuDeleteResponse> getResponseClass() {
        return SkuDeleteResponse.class;
    }
}
