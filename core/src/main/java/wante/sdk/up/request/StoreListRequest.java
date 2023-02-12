package wante.sdk.up.request;

import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.StoreListResponse;

public class StoreListRequest extends AbstractRequest<StoreListResponse> {
    @Override
    public Method getMethod() {
        return Method.GET;
    }

    @Override
    public String getURL() {
        return "http://product.mall.wangxiaocai.cn/api/product/store/list";
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
    public Class<StoreListResponse> getResponseType() {
        return StoreListResponse.class;
    }
}
