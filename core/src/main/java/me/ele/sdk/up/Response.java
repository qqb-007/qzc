package me.ele.sdk.up;

public class Response<T, R> {

    private int errno;
    private R error;

    private T data;

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

    public R getError() {
        return error;
    }

    public void setError(R error) {
        this.error = error;
    }
}
