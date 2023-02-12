package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.CategoryGetResponse;

public class CategoryGetRequest extends Request<CategoryGetResponse> {


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
        return "sku.shop.category.get";
    }

    @Override
    public Class<CategoryGetResponse> getResponseClass() {
        return CategoryGetResponse.class;
    }
}
