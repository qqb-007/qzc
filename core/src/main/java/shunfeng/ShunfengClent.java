package shunfeng;


import com.alibaba.fastjson.JSON;
import shunfeng.request.Request;
import shunfeng.response.Response;
import shunfeng.util.HttpUtil;
import shunfeng.util.SignUtil;

import java.io.UnsupportedEncodingException;

public class ShunfengClent {
    private Integer devId;
    private String devKey;

    public static final String URL = "https://commit-openic.sf-express.com/open/api/external/";

    public <T extends Response, R extends Request<T>> T execute(R req) {
        req.setDevId(devId);
        req.setPushTime(System.currentTimeMillis() / 1000);
        String json = JSON.toJSONString(req);
        String sign = null;
        try {
            sign = SignUtil.sign(json, devKey, devId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String res = HttpUtil.sendPost(json, URL + req.getEndpoint() + "?sign=" + sign);
        return JSON.parseObject(res, req.getResponseType());
    }

    public Integer getDevId() {
        return devId;
    }

    public void setDevId(Integer devId) {
        this.devId = devId;
    }

    public String getDevKey() {
        return devKey;
    }

    public void setDevKey(String devKey) {
        this.devKey = devKey;
    }
}
