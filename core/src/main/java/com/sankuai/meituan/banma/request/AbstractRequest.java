package com.sankuai.meituan.banma.request;

import com.sankuai.meituan.banma.annotation.Param;
import com.sankuai.meituan.banma.response.AbstractResponse;

/**
 * 抽象request类
 */
public abstract class AbstractRequest<T extends AbstractResponse> {

    /**
     * 配送开放平台为每个合作方分配独立的appkey，作为合作方接入认证标识。
     * 每个appkey会绑定一个secret，用于计算签名。请妥善保管secret，避免泄密。如果secret意外泄露，可要求重新生成。
     */
    @Param("appkey")
    protected String appkey;

    /**
     * 时间戳，格式为long，时区为GMT+8，即合作方调用接口时距离Epoch（1970年1月1日) 以秒计算的时间（unix-timestamp）。
     * 开放平台允许合作方请求最大时间误差为10分钟（配送开放平台接到请求的时间 - 合作方调用接口的时间 < 10分钟）。
     */
    @Param("timestamp")
    protected long timestamp;

    /**
     * API协议版本，可选值：1.0。
     */
    @Param("version")
    protected String version;

    /**
     * API请求参数的签名计算结果。
     */
    @Param("sign")
    protected String sign;

    public abstract String getEndpoint();

    public abstract Class<T> getResponseType();

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
