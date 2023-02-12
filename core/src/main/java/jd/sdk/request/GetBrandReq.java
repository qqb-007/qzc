package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.GetBrandRes;

import java.util.List;

public class GetBrandReq extends Request<GetBrandRes> {
    private String brandName;
    private Long brandId;
    private List<String> fields;
    private Integer pageNo;
    private Integer pageSize;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/pms/queryPageBrandInfo";
    }

    @Override
    public Class<GetBrandRes> getResponseClass() {
        return GetBrandRes.class;
    }
}
