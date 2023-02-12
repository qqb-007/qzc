package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.CategoryCreateResponse;

public class CategoryCreateRequest extends Request<CategoryCreateResponse> {


    @JSONField(name = "shop_id")
    private String shopId;

    @JSONField(name = "parent_category_id")
    private Long parentCategoryId;

    private String name;

    private Integer rank;

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
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
        return "sku.shop.category.create";
    }

    @Override
    public Class<CategoryCreateResponse> getResponseClass() {
        return CategoryCreateResponse.class;
    }
}
