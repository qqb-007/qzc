package uupt.src.com.uupt.openapi.request;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.enums.UuAccountType;
import uupt.src.com.uupt.openapi.*;
import uupt.src.com.uupt.openapi.Config.ApiConfig;
import uupt.src.com.uupt.openapi.Config.NanNingConfig;
import uupt.src.com.uupt.openapi.response.GetOrderPriceReponse;

public class GetOrderPriceRequest {
    private String origin_id;
    private String from_address;
    //private String from_usernote;
    private String to_address;
    //private String to_usernote;
    private String city_name;
    //private String county_name;
    private String from_lng;
    private String from_lat;
    private String to_lng;
    private String to_lat;
    private String send_type;
    private String subscribe_type;
    private String subscribe_time;

    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }

    public String getSubscribe_type() {
        return subscribe_type;
    }

    public void setSubscribe_type(String subscribe_type) {
        this.subscribe_type = subscribe_type;
    }

    public String getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(String subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

//    public String getOrigin_id() {
//        return origin_id;
//    }
//
//    public void setOrigin_id(String origin_id) {
//        this.origin_id = origin_id;
//    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

//    public String getFrom_usernote() {
//        return from_usernote;
//    }
//
//    public void setFrom_usernote(String from_usernote) {
//        this.from_usernote = from_usernote;
//    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

//    public String getTo_usernote() {
//        return to_usernote;
//    }
//
//    public void setTo_usernote(String to_usernote) {
//        this.to_usernote = to_usernote;
//    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

//    public String getCounty_name() {
//        return county_name;
//    }
//
//    public void setCounty_name(String county_name) {
//        this.county_name = county_name;
//    }

    public String getFrom_lng() {
        return from_lng;
    }

    public void setFrom_lng(String from_lng) {
        this.from_lng = from_lng;
    }

    public String getFrom_lat() {
        return from_lat;
    }

    public void setFrom_lat(String from_lat) {
        this.from_lat = from_lat;
    }

    public String getTo_lng() {
        return to_lng;
    }

    public void setTo_lng(String to_lng) {
        this.to_lng = to_lng;
    }

    public String getTo_lat() {
        return to_lat;
    }

    public void setTo_lat(String to_lat) {
        this.to_lat = to_lat;
    }

    @Override
    public String toString() {
        return "GetOrderPriceRequest{" +
                "from_address='" + from_address + '\'' +
                ", to_address='" + to_address + '\'' +
                ", city_name='" + city_name + '\'' +
                ", from_lng='" + from_lng + '\'' +
                ", from_lat='" + from_lat + '\'' +
                ", to_lng='" + to_lng + '\'' +
                ", to_lat='" + to_lat + '\'' +
                ", send_type='" + send_type + '\'' +
                ", subscribe_type='" + subscribe_type + '\'' +
                ", subscribe_time='" + subscribe_time + '\'' +
                '}';
    }

    public GetOrderPriceReponse excute(UuAccountType type) {
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
        mydic.Add("origin_id", this.origin_id);
        mydic.Add("from_address", this.from_address);
        mydic.Add("send_type", this.send_type);
        mydic.Add("to_address", this.to_address);
        //mydic.Add("to_usernote", this.to_usernote);
        mydic.Add("city_name", this.city_name);
        //mydic.Add("county_name", this.county_name);
        mydic.Add("from_lng", this.from_lng);
        mydic.Add("from_lat", this.from_lat);
        mydic.Add("to_lng", this.to_lng);
        mydic.Add("to_lat", this.to_lat);
        mydic.Add("subscribe_time", this.subscribe_time);
        mydic.Add("subscribe_type", this.subscribe_type);
        mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, appsecret));
        String result = UUHttpRequestHelper.HttpPost(ApiConfig.GetOrderPriceUrl, mydic);
        Response.Write(result);
        return JSON.parseObject(result, GetOrderPriceReponse.class);
    }
}
