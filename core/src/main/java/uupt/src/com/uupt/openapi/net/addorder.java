package uupt.src.com.uupt.openapi.net;


import uupt.src.com.uupt.openapi.request.AddOrderReuqest;
import uupt.src.com.uupt.openapi.response.AddOrderResponse;

public class addorder {

	public static void main(String[] args) {
		Page_Load();
	}

	private static void Page_Load() {
//		Dictionary<String, String> mydic = new Dictionary<String, String>();
//		mydic.Add("appid", ApiConfig.AppID);
//		mydic.Add("nonce_str", UUCommonFun.NewGuid());
//		mydic.Add("timestamp", UUCommonFun.getTimeStamp());
//		mydic.Add("openid", ApiConfig.OpenID);
//		mydic.Add("price_token", "dd4ce57351d54b41ab686358129df8dd");
//		mydic.Add("order_price", "25.40");
//		mydic.Add("balance_paymoney", "15.40");
//		mydic.Add("receiver", "张三");
//		mydic.Add("receiver_phone", "13766666666");
//		mydic.Add("note", "请尽快取件");
//		mydic.Add("callback_url", "http://www.uupt.com");
//		mydic.Add("push_type", "0");
//		mydic.Add("special_type", "0");
//		mydic.Add("callme_withtake", "1");
//		mydic.Add("pubUserMobile", "13788888888");
//		mydic.Add("sign", UUCommonFun.CreateMd5Sign(mydic, ApiConfig.AppSecret));
//		String result = UUHttpRequestHelper.HttpPost(ApiConfig.AddOrderUrl, mydic);
//		System.out.println(result);
		AddOrderReuqest reuqest = new AddOrderReuqest();
		reuqest.setBalance_paymoney("0.00");
		reuqest.setCallme_withtake("1");
		reuqest.setNote("测试备注");
		reuqest.setOrder_price("6.50");
		reuqest.setOrdersource("1");
		reuqest.setPrice_token("76235d26ce3c47c4a08c5dff71b20359");
		reuqest.setPubusermobile("18339173308");
		reuqest.setPush_type("0");
		reuqest.setReceiver("大猫");
		reuqest.setReceiver_phone("18339173308");
		reuqest.setShortordernum("10");
		reuqest.setSpecial_type("0");
		//AddOrderResponse execute = reuqest.execute();
		//System.out.println(execute);
	}

}
