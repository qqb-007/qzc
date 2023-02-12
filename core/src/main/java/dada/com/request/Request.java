package dada.com.request;

import com.alibaba.fastjson.annotation.JSONField;
import dada.com.response.Response;


public abstract class Request <T extends Response> {
    @JSONField(serialize = false)
    public abstract String getUrl();
    @JSONField(serialize = false)
    public abstract Class<T> getResponseClass();
}
