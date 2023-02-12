package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.ShopListResponse;

public class ShopListRequest extends Request<ShopListResponse> {

    @JSONField(name = "sys_status")
    private Integer sysStatus;

    public Integer getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "shop.list";
    }

    @Override
    public Class<ShopListResponse> getResponseClass() {
        return ShopListResponse.class;
    }
}
