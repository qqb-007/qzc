package me.ele.sdk.up.request;

import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.BrandListResponse;

public class BrandListRequest extends Request<BrandListResponse> {

    private String keyword;

    private int page;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "sku.brand.list";
    }

    @Override
    public Class<BrandListResponse> getResponseClass() {
        return BrandListResponse.class;
    }
}
