package jd.sdk.request;

import com.alibaba.fastjson.annotation.JSONField;
import jd.sdk.Request;
import jd.sdk.response.GetStoreInfoRes;

public class GetStoreInfoReq extends Request<GetStoreInfoRes> {
    @JSONField(name = "StoreNo")
    private String StoreNo;

    public String getStoreNo() {
        return StoreNo;
    }

    public void setStoreNo(String storeNo) {
        StoreNo = storeNo;
    }

    @Override
    public Method getMethod() {
        return Method.GET;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/storeapi/getStoreInfoByStationNo";
    }

    @Override
    public Class<GetStoreInfoRes> getResponseClass() {
        return GetStoreInfoRes.class;
    }
}
