package uupt.src.com.uupt.openapi.request;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.enums.UuAccountType;
import uupt.src.com.uupt.openapi.Config.ApiConfig;
import uupt.src.com.uupt.openapi.Config.NanNingConfig;
import uupt.src.com.uupt.openapi.Dictionary;
import uupt.src.com.uupt.openapi.Response;
import uupt.src.com.uupt.openapi.UUCommonFun;
import uupt.src.com.uupt.openapi.UUHttpRequestHelper;
import uupt.src.com.uupt.openapi.response.AddOrderResponse;
import uupt.src.com.uupt.openapi.response.GetOrderInfoResponse;

public class GetOrderInfoRequest {
    private String order_code;

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    @Override
    public String toString() {
        return "GetOrderInfoRequest{" +
                "order_code='" + order_code + '\'' +
                '}';
    }

    public GetOrderInfoResponse execute(UuAccountType type) {
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
        //mydic.Add("callback_url", callback_url);
        mydic.Add("nonce_str", UUCommonFun.NewGuid());
        mydic.Add("timestamp", UUCommonFun.getTimeStamp());
        mydic.Add("order_code", this.order_code);

        mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, appsecret));
        String result = UUHttpRequestHelper.HttpPost(ApiConfig.GetOrderInfoUrl, mydic);
        Response.Write(result);
        return JSON.parseObject(result, GetOrderInfoResponse.class);
    }
}
