package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.QueryImageRes;

import java.util.List;

public class QueryImageReq extends Request<QueryImageRes> {
    private List<Long> skuIds;

    public List<Long> getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(List<Long> skuIds) {
        this.skuIds = skuIds;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/order/queryListBySkuIds";
    }

    @Override
    public Class<QueryImageRes> getResponseClass() {
        return QueryImageRes.class;
    }
}
