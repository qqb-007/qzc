package info.batcloud.wxc.core.config;

public class ClbmApp {
    private String appId;
    private String appSecret;
    private String orderPaidNotifyUrl;

    private String orderCancelNotifyUrl;

    private String orderFinishNotifyUrl;

    private String orderRefundNotifyUrl;

    private String orderPartRefundNotifyUrl;
    private String orderConfirmNotifyUrl;

    public String getOrderPaidNotifyUrl() {
        return orderPaidNotifyUrl;
    }

    public void setOrderPaidNotifyUrl(String orderPaidNotifyUrl) {
        this.orderPaidNotifyUrl = orderPaidNotifyUrl;
    }

    public String getOrderCancelNotifyUrl() {
        return orderCancelNotifyUrl;
    }

    public void setOrderCancelNotifyUrl(String orderCancelNotifyUrl) {
        this.orderCancelNotifyUrl = orderCancelNotifyUrl;
    }

    public String getOrderFinishNotifyUrl() {
        return orderFinishNotifyUrl;
    }

    public void setOrderFinishNotifyUrl(String orderFinishNotifyUrl) {
        this.orderFinishNotifyUrl = orderFinishNotifyUrl;
    }

    public String getOrderRefundNotifyUrl() {
        return orderRefundNotifyUrl;
    }

    public void setOrderRefundNotifyUrl(String orderRefundNotifyUrl) {
        this.orderRefundNotifyUrl = orderRefundNotifyUrl;
    }

    public String getOrderPartRefundNotifyUrl() {
        return orderPartRefundNotifyUrl;
    }

    public void setOrderPartRefundNotifyUrl(String orderPartRefundNotifyUrl) {
        this.orderPartRefundNotifyUrl = orderPartRefundNotifyUrl;
    }

    public String getOrderConfirmNotifyUrl() {
        return orderConfirmNotifyUrl;
    }

    public void setOrderConfirmNotifyUrl(String orderConfirmNotifyUrl) {
        this.orderConfirmNotifyUrl = orderConfirmNotifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
