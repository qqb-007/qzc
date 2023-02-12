package shansong;

import com.alibaba.fastjson.JSON;
import shansong.config.Config;
import shansong.request.Request;
import shansong.response.Response;
import shansong.utils.HttpsClient;
import shansong.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

public class ShanSongClient {
    public <T extends Response> T request(Request<T> request) {

        Long timestamp = System.currentTimeMillis();
        String data = JSON.toJSONString(request);

        StringBuffer sb = new StringBuffer(Config.AppSecret)
                .append("clientId").append(Config.AppKey)
                .append("data").append(data)
                .append("shopId").append(Config.ShopId)
                .append("timestamp").append(timestamp);
        //计算签名
        String sign = SignUtils.bytesToMD5(sb.toString().getBytes());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("clientId", Config.AppKey);
        map.put("shopId", Config.ShopId);
        map.put("timestamp", timestamp);
        map.put("sign", sign);
        map.put("data", data);
        //请求接口获取的结果
        String res = HttpsClient.sendPost(Config.Host + request.getUrl(), map);


        return (T) JSON.parseObject(res, request.getResponseClass());
    }
}
