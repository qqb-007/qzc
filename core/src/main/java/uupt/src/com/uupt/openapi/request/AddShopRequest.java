package uupt.src.com.uupt.openapi.request;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.enums.UuAccountType;
import uupt.src.com.uupt.openapi.*;
import uupt.src.com.uupt.openapi.Config.ApiConfig;
import uupt.src.com.uupt.openapi.Config.NanNingConfig;
import uupt.src.com.uupt.openapi.response.AddShopResponse;

public class AddShopRequest {
    private String shopname;
    private String address;
    private String usernote;
    private String lng;
    private String lat;
    private String city_name;
    private String county_name;
    private String linkman;
    private String linkman_mobile;

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsernote() {
        return usernote;
    }

    public void setUsernote(String usernote) {
        this.usernote = usernote;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCounty_name() {
        return county_name;
    }

    public void setCounty_name(String county_name) {
        this.county_name = county_name;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkman_mobile() {
        return linkman_mobile;
    }

    public void setLinkman_mobile(String linkman_mobile) {
        this.linkman_mobile = linkman_mobile;
    }

    @Override
    public String toString() {
        return "AddShopRequest{" +
                "shopname='" + shopname + '\'' +
                ", address='" + address + '\'' +
                ", usernote='" + usernote + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", city_name='" + city_name + '\'' +
                ", county_name='" + county_name + '\'' +
                ", linkman='" + linkman + '\'' +
                ", linkman_mobile='" + linkman_mobile + '\'' +
                '}';
    }

    public AddShopResponse execute(UuAccountType type) {
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
        mydic.Add("shopname", this.shopname);
        mydic.Add("address", this.address);
        mydic.Add("usernote", this.usernote);
        mydic.Add("lng", this.lng);
        mydic.Add("lat", this.lat);
        mydic.Add("city_name", this.city_name);
        mydic.Add("county_name", this.county_name);
        mydic.Add("linkman", this.linkman);
        mydic.Add("linkman_mobile", this.linkman_mobile);
        mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, appsecret));
        String result = UUHttpRequestHelper.HttpPost(ApiConfig.AddShopUrl, mydic);
        return JSON.parseObject(result, AddShopResponse.class);
    }
}
