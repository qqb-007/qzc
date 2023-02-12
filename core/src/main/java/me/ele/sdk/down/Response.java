package me.ele.sdk.down;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Response<T> {

    private int errno;
    private String error = "success";

    private T data;

    @JsonIgnore
    @JSONField(serialize = false)
    public abstract String getCmd();

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
