package wante.sdk.up;

/**
 * 抽象request类
 */
public abstract class AbstractRequest<T> {

    public abstract AbstractRequest.Method getMethod();

    public abstract String getURL();

    public abstract Boolean isJson();

    public abstract String getJson();

    public abstract String getSecret();

    public abstract Class<T> getResponseType();

    public enum Method {
        POST, GET
    }
}
