package com.sankuai.meituan.banma.response;

/**
 * 抽象响应父类
 */
public abstract class AbstractResponse<T> {
    protected String code;
    protected String message;

    protected T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
