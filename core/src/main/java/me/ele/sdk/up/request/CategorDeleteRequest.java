package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.CategoryDeleteResponse;
import me.ele.sdk.up.response.CategoryUpdateResponse;


public class CategorDeleteRequest extends Request<CategoryDeleteResponse> {


    @JSONField(name = "shop_id")
    private String shopId;

    @JSONField(name = "category_id")
    private Long categoryId;


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

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
        return "sku.shop.category.delete";
    }

    @Override
    public Class<CategoryDeleteResponse> getResponseClass() {
        return CategoryDeleteResponse.class;
    }
}
