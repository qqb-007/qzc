package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.ProductSkuDeleteRes;

public class ProductSkuDeleteReq extends AbstractRequest<ProductSkuDeleteRes> {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Method getMethod() {
        return Method.GET;
    }

    @Override
    public String getURL() {
        return "http://product.mall.wangxiaocai.cn/api/product/productStandard/delete";
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
    public Class<ProductSkuDeleteRes> getResponseType() {
        return ProductSkuDeleteRes.class;
    }
}
