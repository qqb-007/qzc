package uupt.src.com.uupt.openapi.response;

public class CancelOrderResponse {
    private String order_code;
    private String origin_id;
    private String nonce_str;
    private String sign;
    private String appid;
    private String return_msg;
    private String return_code;

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    @Override
    public String toString() {
        return "CancelOrderResponse{" +
                "order_code='" + order_code + '\'' +
                ", origin_id='" + origin_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", appid='" + appid + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", return_code='" + return_code + '\'' +
                '}';
    }
}
