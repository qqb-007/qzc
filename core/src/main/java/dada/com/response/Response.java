package dada.com.response;

public class Response<T> {
    //通用响应类
    private String status;

    private Integer code;

    private String msg;

    private T result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
                "status='" + status + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * 是否成功！
     *
     * @return
     */
    public boolean isSuccess() {
        return "success".equals(this.status);
    }
}
