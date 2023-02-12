package info.batcloud.wxc.core.printers.xinyeyun.opensdk.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import info.batcloud.wxc.core.printers.xinyeyun.opensdk.vo.*;

public class Test {
    private static String BASE_URL = "https://open.xpyun.net/api/openapi";

    public static void main(String[] args) {
        PrintRequest restRequest = new PrintRequest();
        restRequest.setSn("00R4LJWU9W8CD4A");
        restRequest.setContent("<CB>王小菜测试打印</CB>");
        Config.createRequestHeader(restRequest);
        String url = BASE_URL + "/xprinter/print";
        String jsonRequest = JSON.toJSONString(restRequest);
        String resp = HttpClientUtil.doPostJSON(url, jsonRequest);
        ObjectRestResponse<String> result = JSON.parseObject(resp, new TypeReference<ObjectRestResponse<String>>(){});
        System.out.println(result.toString());;
    }
}
