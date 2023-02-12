package wante.sdk.up.response;

import java.util.List;

public class OrderRefundRejectRes {
    private String message;//订单不存在的时候报错信息
    private Integer id;//订单id

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
