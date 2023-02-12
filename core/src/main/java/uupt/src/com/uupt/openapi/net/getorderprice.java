package uupt.src.com.uupt.openapi.net;


import uupt.src.com.uupt.openapi.*;
import uupt.src.com.uupt.openapi.request.GetOrderPriceRequest;
import uupt.src.com.uupt.openapi.response.GetOrderPriceReponse;

public class getorderprice {

	public static void main(String[] args) {
		Page_Load();
	}

	protected static void Page_Load() {
//		Dictionary<String, String> mydic = new Dictionary<String, String>();
//		mydic.Add("appid", ApiConfig.AppID);
//		mydic.Add("nonce_str", UUCommonFun.NewGuid());
//		mydic.Add("timestamp", UUCommonFun.getTimeStamp());
//		mydic.Add("openid", ApiConfig.OpenID);
//		mydic.Add("from_address", "金水路与玉凤路交汇处浦发国际金融中心");
//		mydic.Add("from_usernote", "21层2111房间");
//		mydic.Add("to_address", "中原路与嵩山路交叉处绿城广场");
//		mydic.Add("to_usernote", "北门");
//		mydic.Add("city_name", "郑州市");
//		mydic.Add("county_name", "金水区");
//		mydic.Add("from_lng", "113.71742");
//		mydic.Add("from_lat", "34.767995");
//		mydic.Add("to_lng", "113.638827");
//		mydic.Add("to_lat", "34.753592");
//		mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, ApiConfig.AppSecret));
//		String result = UUHttpRequestHelper.HttpPost(ApiConfig.GetOrderPriceUrl, mydic);
//		Response.Write(result);
		GetOrderPriceRequest request = new GetOrderPriceRequest();
		request.setCity_name("杭州市");
		request.setFrom_address("碧沙港公园");
		request.setFrom_lat("34.757948");
		request.setFrom_lng("113.636446");
		//request.setOrigin_id("110110UU1");
		request.setSend_type("0");
		request.setTo_address("方圆连锁酒店");
		request.setTo_lat("34.75995");
		request.setTo_lng("113.645088");
		//GetOrderPriceReponse excute = request.excute();
		//System.out.println(excute);
	}

}
