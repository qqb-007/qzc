package uupt.src.com.uupt.openapi.net;

import uupt.src.com.uupt.openapi.Config.ApiConfig;
import uupt.src.com.uupt.openapi.Dictionary;
import uupt.src.com.uupt.openapi.UUCommonFun;
import uupt.src.com.uupt.openapi.UUHttpRequestHelper;

public class addshop {
    public static void main(String[] args) {
        Page_Load();
    }

    private static void Page_Load() {
        Dictionary<String, String> mydic = new Dictionary<String, String>();
        mydic.Add("appid", ApiConfig.AppID);
        mydic.Add("nonce_str", UUCommonFun.NewGuid());
        mydic.Add("timestamp", UUCommonFun.getTimeStamp());
        mydic.Add("openid", ApiConfig.OpenID);
        mydic.Add("shopname", "王小菜(枫树山农贸市场店）");
        mydic.Add("address", "江西省景德镇市昌江区枫树山农贸市场内J05摊位");
        mydic.Add("usernote", "枫树山农贸市场内J05摊位");
        mydic.Add("lng", "117.198808");
        mydic.Add("lat", "29.280056");
        mydic.Add("city_name", "景德镇市");
        mydic.Add("county_name", "昌江区");
        mydic.Add("linkman", "王小菜(枫树山农贸市场店）");
        mydic.Add("linkman_mobile", "15179882300");
        mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, ApiConfig.AppSecret));
        String result = UUHttpRequestHelper.HttpPost(ApiConfig.AddShopUrl, mydic);
        System.out.println(result);
    }
}
