package shansong.response;

public class Response<T> {
    //通用响应类
    private Integer status;

    private String msg;


    private T data;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * 是否成功！
     *
     * @return
     */
    public boolean isSuccess() {
        return 200 == this.status;
    }
}
