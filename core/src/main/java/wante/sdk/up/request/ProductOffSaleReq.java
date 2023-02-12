package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.ProductOffsaleRes;

public class ProductOffSaleReq extends AbstractRequest<ProductOffsaleRes> {
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
        return "http://product.mall.wangxiaocai.cn/api/product/off_sale";
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
    public Class<ProductOffsaleRes> getResponseType() {
        return ProductOffsaleRes.class;
    }
}
