package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.GetTigRes;

import java.util.List;

public class GetTigReq extends Request<GetTigRes> {
    private Long id;
    private List<String> fields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/api/queryChildCategoriesForOP";
    }

    @Override
    public Class<GetTigRes> getResponseClass() {
        return GetTigRes.class;
    }
}
