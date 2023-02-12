package me.ele.sdk.up.request;

import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.CategoryListResponse;

public class CategoryListRequest extends Request<CategoryListResponse> {

    private Long parent_id;
    private Integer depth;

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "sku.category.list";
    }

    @Override
    public Class getResponseClass() {
        return CategoryListResponse.class;
    }
}
