package me.ele.sdk.up;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EleClient {

    private final static String API_URL = "https://api-be.ele.me";

    private String version;
    private String source;
    private String secret;

    public <T extends Response> T request(Request<T> request) {
        String result = null;
        if (request.getMethod() == Request.Method.GET) {
            result = HttpRequestUtil.sendGet(API_URL, toRequestString(request.getCmd(), request));
        } else if (request.getMethod() == Request.Method.POST) {
            result = HttpRequestUtil.sendPost(API_URL, toRequestString(request.getCmd(), request));
        }
        JSONObject jsonObject = JSON.parseObject(result);
        T response = JSONObject.toJavaObject(jsonObject.getJSONObject("body"), request.getResponseClass());
        return response;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    private String toRequestString(String cmd, Object body) {
        Map<String, Object> params = new HashMap<>(20);

        params.put("cmd", cmd);
        params.put("encrypt", "");
        params.put("secret", this.secret);
        params.put("source", this.source);
        params.put("ticket", UUID.randomUUID().toString().toUpperCase());
        //在body中添加需要输入的参数
        params.put("body", body);
        params.put("version", this.version);
        params.put("timestamp", System.currentTimeMillis() / 1000);


        String sign = SignUtil.getSign(params);
        params.put("sign", sign);
        params.remove("secret");
        params.put("body", JSON.toJSONString(params.get("body")));
        StringBuilder requestparams = new StringBuilder();
        for (Map.Entry<String, Object> map : params.entrySet()) {
            requestparams.append(map.getKey() + "=" + map.getValue() + "&");
        }
        return requestparams.substring(0, requestparams.length() - 1);
    }

    public Map<String, Object> wrapDownResponse(me.ele.sdk.down.Response response) {
        Map<String, Object> params = new HashMap<>(20);

        params.put("cmd", response.getCmd());
        params.put("secret", this.secret);
        params.put("source", this.source);
        params.put("ticket", UUID.randomUUID().toString().toUpperCase());
        //在body中添加需要输入的参数
        params.put("body", response);
        params.put("version", this.version);
        params.put("timestamp", System.currentTimeMillis() / 1000);


        String sign = SignUtil.getSign(params);
        params.put("sign", sign);
        //params.put("body", JSON.toJSONString(response));
        params.remove("secret");
        return params;
    }

}
