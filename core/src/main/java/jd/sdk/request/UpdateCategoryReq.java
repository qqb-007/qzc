package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.UpdateCategoryRes;

public class UpdateCategoryReq extends Request<UpdateCategoryRes> {
    private Long id;
    private String shopCategoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/pms/updateShopCategory";
    }

    @Override
    public Class<UpdateCategoryRes> getResponseClass() {
        return UpdateCategoryRes.class;
    }
}
