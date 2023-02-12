package uupt.src.com.uupt.openapi.request;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.enums.UuAccountType;
import uupt.src.com.uupt.openapi.*;
import uupt.src.com.uupt.openapi.Config.ApiConfig;
import uupt.src.com.uupt.openapi.Config.NanNingConfig;
import uupt.src.com.uupt.openapi.response.CancelOrderResponse;

public class CancelUuRequest {
    private String order_code;
    //private String origin_id;
    private String reason;

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "CancelUuRequest{" +
                "order_code='" + order_code + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }

    public CancelOrderResponse execute(UuAccountType type) {
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
        mydic.Add("nonce_str", UUCommonFun.NewGuid());
        mydic.Add("timestamp", UUCommonFun.getTimeStamp());
        mydic.Add("openid", openid);
        mydic.Add("order_code", this.order_code);
        //mydic.Add("origin_id", " ");
        mydic.Add("reason", this.reason);
        mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, appsecret));
        System.out.println(mydic.toString());
        String result = UUHttpRequestHelper.HttpPost(ApiConfig.CancelOrderUrl, mydic);
        Response.Write(result);
        return JSON.parseObject(result, CancelOrderResponse.class);
    }
}
