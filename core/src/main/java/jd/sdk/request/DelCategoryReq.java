package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.DelCategoryRes;

public class DelCategoryReq extends Request<DelCategoryRes> {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/pms/delShopCategory";
    }

    @Override
    public Class<DelCategoryRes> getResponseClass() {
        return DelCategoryRes.class;
    }
}
