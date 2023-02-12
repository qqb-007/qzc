package info.batcloud.wxc.core.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class URLDecodeHelper {

    public static void decode(Map<String, String> map) throws UnsupportedEncodingException {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map.put(entry.getKey(), URLDecoder.decode(entry.getValue().toString(), "utf8"));
        }
    }

}
