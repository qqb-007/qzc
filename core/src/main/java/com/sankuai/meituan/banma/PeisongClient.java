package com.sankuai.meituan.banma;

import com.alibaba.fastjson.JSON;
import com.sankuai.meituan.banma.constants.RequestConstant;
import com.sankuai.meituan.banma.request.AbstractRequest;
import com.sankuai.meituan.banma.response.AbstractResponse;
import com.sankuai.meituan.banma.sign.SignHelper;
import com.sankuai.meituan.banma.util.ConvertUtil;
import com.sankuai.meituan.banma.util.DateUtil;
import com.sankuai.meituan.banma.util.HttpClient;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class PeisongClient {

    private String appKey;
    private String secret;

    public static final String API_URL = "https://peisongopen.meituan.com/api";

    public <T extends AbstractResponse, R extends AbstractRequest<T>> T execute(R req) {
        req.setAppkey(appKey);
        req.setTimestamp(DateUtil.unixTime());
        req.setVersion("1.0");
        Map<String, String> params = ConvertUtil.convertToMap(req);
        String sign = null;
        try {
            sign = SignHelper.generateSign(params, secret);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        params.put("sign", sign);

        try {
            String res = HttpClient.post(API_URL + req.getEndpoint(), params);
            return JSON.parseObject(res, req.getResponseType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
