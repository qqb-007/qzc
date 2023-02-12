package uupt.src.com.uupt.openapi.net;


import uupt.src.com.uupt.openapi.*;
import uupt.src.com.uupt.openapi.Config.ApiConfig;

public class bindusersubmit {

	public static void main(String[] args) {
		Page_Load();
	}

	protected static void Page_Load() {
		Dictionary<String, String> mydic = new Dictionary<String, String>();
		mydic.Add("appid", ApiConfig.AppID);
		mydic.Add("nonce_str", UUCommonFun.NewGuid());
		mydic.Add("timestamp", UUCommonFun.getTimeStamp());
		mydic.Add("user_mobile", "18566666123");
		mydic.Add("validate_code", "0470");
		mydic.Add("city_name", "郑州市");
		mydic.Add("county_name", "金水区");
		mydic.Add("reg_ip", "192.168.6.1");
		mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, ApiConfig.AppSecret));
		String result = UUHttpRequestHelper.HttpPost(ApiConfig.BindUserSubmitUrl, mydic);
		Response.Write(result);
	}

}
