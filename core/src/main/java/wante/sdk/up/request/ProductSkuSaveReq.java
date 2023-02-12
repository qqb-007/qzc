package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.ProductSkuSaveRes;

public class ProductSkuSaveReq extends AbstractRequest<ProductSkuSaveRes> {
    private String name;
    private Integer productId;
    private Integer productStandardGroupId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductStandardGroupId() {
        return productStandardGroupId;
    }

    public void setProductStandardGroupId(Integer productStandardGroupId) {
        this.productStandardGroupId = productStandardGroupId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getURL() {
        return "http://product.mall.wangxiaocai.cn/api/product/productStandard/save";
    }

    @Override
    public Boolean isJson() {
        return false;
    }

    @Override
    public String getJson() {
        return null;
    }

    @Override
    public String getSecret() {
        return "Basic bWFsbDpMOUlCb0pkMUpSdTViVHlv";
    }

    @Override
    public Class<ProductSkuSaveRes> getResponseType() {
        return ProductSkuSaveRes.class;
    }
}
