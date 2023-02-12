package uupt.src.com.uupt.openapi.request;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.enums.UuAccountType;
import uupt.src.com.uupt.openapi.*;
import uupt.src.com.uupt.openapi.Config.ApiConfig;
import uupt.src.com.uupt.openapi.Config.NanNingConfig;
import uupt.src.com.uupt.openapi.response.AddOrderResponse;

public class AddOrderReuqest {
    private String price_token;
    private String order_price;
    private String balance_paymoney;
    private String receiver;
    private String receiver_phone;
    private String note;
    private String push_type;
    private String special_type;
    private String callme_withtake;
    private String pubusermobile;
    private String ordersource;
    private String shortordernum;

    public String getPrice_token() {
        return price_token;
    }

    public void setPrice_token(String price_token) {
        this.price_token = price_token;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getBalance_paymoney() {
        return balance_paymoney;
    }

    public void setBalance_paymoney(String balance_paymoney) {
        this.balance_paymoney = balance_paymoney;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public void setReceiver_phone(String receiver_phone) {
        this.receiver_phone = receiver_phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPush_type() {
        return push_type;
    }

    public void setPush_type(String push_type) {
        this.push_type = push_type;
    }

    public String getSpecial_type() {
        return special_type;
    }

    public void setSpecial_type(String special_type) {
        this.special_type = special_type;
    }

    public String getCallme_withtake() {
        return callme_withtake;
    }

    public void setCallme_withtake(String callme_withtake) {
        this.callme_withtake = callme_withtake;
    }

    public String getPubusermobile() {
        return pubusermobile;
    }

    public void setPubusermobile(String pubusermobile) {
        this.pubusermobile = pubusermobile;
    }

    public String getOrdersource() {
        return ordersource;
    }

    public void setOrdersource(String ordersource) {
        this.ordersource = ordersource;
    }

    public String getShortordernum() {
        return shortordernum;
    }

    public void setShortordernum(String shortordernum) {
        this.shortordernum = shortordernum;
    }

    @Override
    public String toString() {
        return "AddOrderReuqest{" +
                "price_token='" + price_token + '\'' +
                ", order_price='" + order_price + '\'' +
                ", balance_paymoney='" + balance_paymoney + '\'' +
                ", receiver='" + receiver + '\'' +
                ", receiver_phone='" + receiver_phone + '\'' +
                ", note='" + note + '\'' +
                ", push_type='" + push_type + '\'' +
                ", special_type='" + special_type + '\'' +
                ", callme_withtake='" + callme_withtake + '\'' +
                ", pubusermobile='" + pubusermobile + '\'' +
                ", ordersource='" + ordersource + '\'' +
                ", shortordernum='" + shortordernum + '\'' +
                '}';
    }

    public AddOrderResponse execute(UuAccountType type) {
        Dictionary<String, String> mydic = new Dictionary<String, String>();
        String appid = "";
        String openid = "";
        String callback_url = "";
        String appsecret = "";
        String url = "";
        if (type == UuAccountType.NANNING) {
            appid = NanNingConfig.AppID;
            openid = NanNingConfig.OpenID;
            callback_url = NanNingConfig.OrderStatusCallBackUrl;
            appsecret = NanNingConfig.AppSecret;
        } else {
            appid = ApiConfig.AppID;
            openid = ApiConfig.OpenID;
            callback_url = ApiConfig.OrderStatusCallBackUrl;
            appsecret = ApiConfig.AppSecret;
        }

        mydic.Add("appid", appid);
        mydic.Add("openid", openid);
        mydic.Add("callback_url", callback_url);
        mydic.Add("nonce_str", UUCommonFun.NewGuid());
        mydic.Add("timestamp", UUCommonFun.getTimeStamp());
        mydic.Add("price_token", this.price_token);
        mydic.Add("order_price", this.order_price);
        mydic.Add("balance_paymoney", this.balance_paymoney);
        mydic.Add("receiver", this.receiver);
        mydic.Add("receiver_phone", this.receiver_phone);
        mydic.Add("note", this.note);
        mydic.Add("push_type", this.push_type);
        mydic.Add("special_type", this.special_type);
        mydic.Add("callme_withtake", this.callme_withtake);
        mydic.Add("pubUserMobile", this.pubusermobile);
        mydic.Add("ordersource", this.ordersource);
        mydic.Add("shortordernum", this.shortordernum);
        mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, appsecret));
        String result = UUHttpRequestHelper.HttpPost(ApiConfig.AddOrderUrl, mydic);
        Response.Write(result);
        return JSON.parseObject(result, AddOrderResponse.class);
    }
}
