package info.batcloud.wxc.core.bmap;


import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.amap.HttpClient;
import info.batcloud.wxc.core.bmap.response.MapResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BMapClient {
    public MapResponse HuoToBai(String lng, String lat) {
        String url = "https://api.map.baidu.com/geoconv/v1/";
        Map<String, String> params = new HashMap<>();
        params.put("ak", "9WCAsMHtveix0mFxqCPeNI5Ej9Y98CM7");
        params.put("coords", lng + "," + lat);
        params.put("from", "3");
        params.put("to", "5");
        String res = null;
        try {
            res = HttpUtils.get(url, params);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return JSON.parseObject(res, MapResponse.class);
    }

//    public MapResponse HuoToBai(String lng, String lat) {
//        String url = "https://api.map.baidu.com/geoconv/v1/";
//        Map<String, String> params = new HashMap<>();
//        params.put("ak", "9WCAsMHtveix0mFxqCPeNI5Ej9Y98CM7");
//        params.put("coords", lng + "," + lat);
//        params.put("from", "3");
//        params.put("to", "5");
//        String res = null;
//        try {
//            res = HttpUtils.get(url, params);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return JSON.parseObject(res, MapResponse.class);
//    }
}
