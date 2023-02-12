package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.AddCategoryRes;

public class AddCategoryReq extends Request<AddCategoryRes> {
    private Long pid;
    private String shopCategoryName;
    private Integer shopCategoryLevel;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public Integer getShopCategoryLevel() {
        return shopCategoryLevel;
    }

    public void setShopCategoryLevel(Integer shopCategoryLevel) {
        this.shopCategoryLevel = shopCategoryLevel;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/pms/addShopCategory";
    }

    @Override
    public Class<AddCategoryRes> getResponseClass() {
        return AddCategoryRes.class;
    }
}
