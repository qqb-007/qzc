package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.StoreGetRes;

public class StoreGetReq extends AbstractRequest<StoreGetRes> {
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
        return "http://product.mall.wangxiaocai.cn/api/product/store/index";
    }

    @Override
    public Boolean isJson() {
        return null;
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
    public Class<StoreGetRes> getResponseType() {
        return StoreGetRes.class;
    }
}
