package jd.sdk;


import com.alibaba.fastjson.annotation.JSONField;

public abstract class Request<T extends Response> {
    @JSONField(serialize = false)
    public abstract Method getMethod();
    @JSONField(serialize = false)
    public abstract String getUrl();
    @JSONField(serialize = false)
    public abstract Class<T> getResponseClass();

    public enum Method {
        POST, GET, FORM
    }

}

