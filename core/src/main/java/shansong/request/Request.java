package shansong.request;


import com.alibaba.fastjson.annotation.JSONField;
import shansong.response.Response;

public abstract class Request <T extends Response> {
    @JSONField(serialize = false)
    public abstract String getUrl();
    @JSONField(serialize = false)
    public abstract Class<T> getResponseClass();
}
