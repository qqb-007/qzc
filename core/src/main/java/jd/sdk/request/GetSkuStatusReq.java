package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.GetSkuStatusRes;

public class GetSkuStatusReq extends Request<GetSkuStatusRes> {

    private String outSkuId;
    private String traceId;

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/pms/getSkuStatus";
    }

    @Override
    public Class<GetSkuStatusRes> getResponseClass() {
        return GetSkuStatusRes.class;
    }
}
