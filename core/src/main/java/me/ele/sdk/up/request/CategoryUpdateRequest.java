package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.CategoryUpdateResponse;

public class CategoryUpdateRequest extends Request<CategoryUpdateResponse> {


    @JSONField(name = "shop_id")
    private String shopId;

    @JSONField(name = "category_id")
    private Long categoryId;

    private String name;

    private Integer rank;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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
        return "sku.shop.category.update";
    }

    @Override
    public Class<CategoryUpdateResponse> getResponseClass() {
        return CategoryUpdateResponse.class;
    }
}
