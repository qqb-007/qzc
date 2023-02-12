package me.ele.sdk.up;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.*;

public class Test {


    public static String initParams() {
        Map<String, Object> params = new HashMap<>();

        params.put("cmd", "shop.get");
        params.put("encrypt", "");//如果encrypt没有，直接用""，不要用null表示
        params.put("secret", "25345i52345i2452352345234523452");
        params.put("source", "12345");
        params.put("ticket", "5D67EC2B-D793-44AC-A0A9-2B68DF8873B0");
        //在body中添加需要输入的参数
        Map<String, Object> body = new HashMap<>();
        body.put("baidu_shop_id", "2542352");
        params.put("body", body);
        params.put("version", "3");
        params.put("timestamp", "1539225682");//当前时间戳


        String sign = SignUtil.getSign(params);
        params.put("sign", sign);
        params.remove("secret");
        params.put("body", JSON.toJSONString(params.get("body")));
        StringBuilder requestparams = new StringBuilder();
        for (Map.Entry<String, Object> map : params.entrySet()) {
            requestparams.append(map.getKey()+"="+map.getValue() + "&");
        }
        return requestparams.substring(0, requestparams.length() - 1).toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String requestParams = initParams();
        String result = HttpRequestUtil.sendPost("https://api-be.ele.me", requestParams);
        System.out.print(result);
    }

    /**
     * 中文转unicode,如果参数中有中文，需要转一下
     *
     * @param gbString
     * @return
     */
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }
}


