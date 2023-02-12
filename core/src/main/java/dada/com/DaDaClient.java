package dada.com;

import com.alibaba.fastjson.JSON;
import dada.com.config.Config;
import dada.com.request.Request;
import dada.com.response.Response;
import dada.com.utils.HttpsClient;
import dada.com.utils.JSONUtil;


import java.util.*;

public class DaDaClient {
    public <T extends Response> T request(Request<T> request) {
        String data;
        if (request.getUrl().equals("/api/shop/add")) {
            List<Request<T>> list = new ArrayList<>();
            list.add(request);
            data = JSONUtil.toJson(list);
        } else {
            data = JSON.toJSONString(request);
        }

        //计算签名
        //请求接口获取的结果
        String res = HttpsClient.postRequest(Config.ONLINE_HOST + request.getUrl(), getRequestParams(data));


        return (T) JSON.parseObject(res, request.getResponseClass());
    }

    private String getRequestParams(String body) {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("source_id", Config.SOURCE_ID);
        requestParams.put("app_key", Config.APP_KEY);
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        requestParams.put("format", Config.FORMAT);
        requestParams.put("v", Config.V);
        requestParams.put("body", body);
        requestParams.put("signature", this.getSign(requestParams));
        return JSONUtil.toJson(requestParams);
    }

    private String getSign(Map<String, String> requestParams) {
        //请求参数键值升序排序
        Map<String, String> sortedParams = new TreeMap<String, String>(requestParams);
        Set<Map.Entry<String, String>> entrySets = sortedParams.entrySet();

        //拼参数字符串。
        StringBuilder signStr = new StringBuilder();
        for (Map.Entry<String, String> entry : entrySets) {
            signStr.append(entry.getKey()).append(entry.getValue());
        }

        //MD5签名并校验
        String toSign = Config.APP_SECRET + signStr.toString() + Config.APP_SECRET;
        String sign = encrypt(toSign);
        return sign.toUpperCase();
    }

    private String encrypt(String inbuf) {
        String s = null;
        char[] hexDigits = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(inbuf.getBytes("UTF-8"));
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
