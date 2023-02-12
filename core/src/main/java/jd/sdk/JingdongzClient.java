package jd.sdk;

import com.alibaba.fastjson.JSON;
import jd.sdk.util.AESUtils;
import jd.sdk.util.HttpsUtil;
import jd.sdk.util.SignUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JingdongzClient {
    private String token;
    private String appKey;
    private String v;
    private String appSecret;

    public <T extends Response> T request(Request<T> request) {
        String result = null;
        if (request.getMethod() == Request.Method.GET) {
            try {
                result = HttpsUtil.sendGet(request.getUrl(), toRequestString(request));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request.getMethod() == Request.Method.POST) {
            try {
                result = HttpsUtil.sendPost(request.getUrl(), toRequestString(request));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String s1 = result.replace("\\", "");
        String s2 = s1.replace("\"{", "{");
        String s = s2.replace("}\"", "}");

        String model = "{\"code\":\"0\",\"msg\":\"模板, UUID[0000]\",\"data\":{},\"encryptData\":\"\",\"success\":true}";
        T mt = JSON.parseObject(model, request.getResponseClass());

        T t = JSON.parseObject(s, request.getResponseClass());
        if (StringUtils.isNotBlank(t.getEncryptData())) {
            try {
                String json = AESUtils.decryptAES(t.getEncryptData(), appSecret.substring(0, 16), appSecret.substring(16, 32));
                String s3 = json.replace("\\", "");
                String s4 = s3.replace("\"{", "{");
                String s5 = s4.replace("}\"", "}");
                t.setData(JSON.parseObject(s5, mt.getData().getClass()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return t;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    private Map<String, Object> toRequestString(Request request) {
        Long time = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String res = simpleDateFormat.format(date);
        String sign = getSign(JSON.toJSONString(request), res);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("token", this.token);
        param.put("app_key", this.appKey);
        param.put("timestamp", res);
        param.put("sign", sign);
        param.put("format", "json");
        param.put("v", this.v);
        param.put("jd_param_json", JSON.toJSONString(request));
        return param;
    }

    public String getSign(String paramJson, String time) {
        WebRequestDTO webReqeustDTO = new WebRequestDTO();
        webReqeustDTO.setApp_key(this.appKey);
        webReqeustDTO.setFormat("json");
        webReqeustDTO.setJd_param_json(paramJson);
        webReqeustDTO.setTimestamp(time);
        webReqeustDTO.setToken(this.token);
        webReqeustDTO.setV(this.v);
        String sign = null;
        try {
            sign = SignUtils.getSignByMD5(webReqeustDTO, appSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }
}
