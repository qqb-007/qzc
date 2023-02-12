package me.ele.sdk.up;

public abstract class Request<T extends Response> {

    public abstract Method getMethod();

    public abstract String getCmd();

    public abstract Class<T> getResponseClass();

    public enum Method {
        POST, GET
    }

}
