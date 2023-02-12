package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.SkuListResponse;

public class SkuListRequest extends Request<SkuListResponse> {

    @JSONField(name = "shop_id")
    private String shopId;

    private Integer page;
    @JSONField(name = "page_size")
    private Integer pageSize;
    @JSONField(name = "custom_sku_id")
    private String customSkuId;
    @JSONField(name = "sku_id")
    private Long skuId;

    private String upc;

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCustomSkuId() {
        return customSkuId;
    }

    public void setCustomSkuId(String customSkuId) {
        this.customSkuId = customSkuId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "sku.list";
    }

    @Override
    public Class<SkuListResponse> getResponseClass() {
        return SkuListResponse.class;
    }
}
