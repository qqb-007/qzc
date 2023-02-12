package info.batcloud.wxc.core.domain;

/**
 * Created by Administrator on 2017-07-29.
 */
public abstract class ApiResponse<T> {

    private boolean success;

    private int code;

    private String errMsg;

    private T data;

    private long cacheExpire;

    private ApiResponseLevel level;

    public ApiResponse() {
        this.cacheExpire = 0;
    }

    public ApiResponseLevel getLevel() {
        return level;
    }

    public void setLevel(ApiResponseLevel level) {
        this.level = level;
    }

    public long getCacheExpire() {
        return cacheExpire;
    }

    public void setCacheExpire(long cacheExpire) {
        this.cacheExpire = cacheExpire;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
