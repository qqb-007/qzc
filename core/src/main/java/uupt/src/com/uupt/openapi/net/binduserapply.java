package uupt.src.com.uupt.openapi.net;


import uupt.src.com.uupt.openapi.*;
import uupt.src.com.uupt.openapi.Config.ApiConfig;

public class binduserapply {

	public static void main(String[] args) {
		Page_Load();
	}

	private static void Page_Load() {
		Dictionary<String, String> mydic = new Dictionary<String, String>();
		mydic.Add("appid", ApiConfig.AppID);
		mydic.Add("nonce_str", UUCommonFun.NewGuid());
		mydic.Add("timestamp", UUCommonFun.getTimeStamp());
		mydic.Add("user_mobile", "15083121409");
		mydic.Add("user_ip", "192.168.1.66");
		mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, ApiConfig.AppSecret));
		String result = UUHttpRequestHelper.HttpPost(ApiConfig.BindUserApplyUrl, mydic);
		Response.Write(result);
	}

}
