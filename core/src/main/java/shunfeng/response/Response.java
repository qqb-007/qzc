package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 抽象响应父类
 */
public abstract class Response<T> {
    @JSONField(name = "error_code")
    protected Integer errorCode;
    @JSONField(name = "error_msg")
    protected String errorMsg;
    @JSONField(name = "error_data")
    protected String errorData;
    protected T result;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorData() {
        return errorData;
    }

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorData='" + errorData + '\'' +
                ", result=" + result +
                '}';
    }
}
