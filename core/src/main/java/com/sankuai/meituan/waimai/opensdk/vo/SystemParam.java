package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by yangzhiqi on 15/10/15.
 * 每个接口都需要传入的参数
 */
public class SystemParam {
    private String appId;
    private String appSecret;

    public SystemParam(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public SystemParam setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public SystemParam setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    @Override
    public String toString() {
        return "SystemParam [" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ']';
    }
}
