package info.batcloud.wxc.core.amap;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.amap.response.GeoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AMapClient {

    @Value("${amap.webApiKey}")
    private String webApiKey;

    public GeoResponse geo(String address) throws IOException {
        String url = "https://restapi.amap.com/v3/geocode/geo";
        Map<String, String> params = new HashMap<>();
        params.put("key", this.webApiKey);
        params.put("address", address);
        String res = HttpClient.get(url, params);
        return JSON.parseObject(res, GeoResponse.class);
    }

}
