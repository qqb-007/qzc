package me.ele.sdk.up.request;

import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.PropertyListResponse;

public class PropertyListRequest extends Request<PropertyListResponse> {

    private String shop_id;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "sku.category.property.list";
    }

    @Override
    public Class<PropertyListResponse> getResponseClass() {
        return PropertyListResponse.class;
    }
}
