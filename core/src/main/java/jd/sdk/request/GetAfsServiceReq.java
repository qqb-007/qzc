package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.GetAfsServiceRes;

public class GetAfsServiceReq extends Request<GetAfsServiceRes> {
    private String afsServiceOrder;

    public String getAfsServiceOrder() {
        return afsServiceOrder;
    }

    public void setAfsServiceOrder(String afsServiceOrder) {
        this.afsServiceOrder = afsServiceOrder;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/afs/getAfsService";
    }

    @Override
    public Class<GetAfsServiceRes> getResponseClass() {
        return GetAfsServiceRes.class;
    }
}
