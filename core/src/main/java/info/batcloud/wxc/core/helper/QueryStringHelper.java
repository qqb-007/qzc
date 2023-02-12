package info.batcloud.wxc.core.helper;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryStringHelper {

    public static Map<String, String> parseUrl(String url) {
        String[] seg = url.split("\\?");
        if (seg.length == 1) {
            return new HashMap<>();
        }
        Map<String, String> map = new HashMap<>();
        String[] queryStringList = seg[1].split("&");
        for (String str : queryStringList) {
            String[] tmps = str.split("=");
            if (tmps.length > 1) {
                map.put(tmps[0], tmps[1]);
            } else {
                map.put(tmps[0], "");
            }
        }
        return map;
    }

    public static <T> T parseObject(Class<T> clazz, String queryString) {
        try {
            T obj = clazz.newInstance();
            populate(obj, queryString);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static <T> void populate(T t, String queryString)  {
        T obj = JSON.parseObject(JSON.toJSONString(populate(queryString)), (Class<T>) t.getClass());
        try {
            BeanUtils.copyProperties(t, obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> populate(String queryString)  {
        if (queryString.indexOf("?") != -1) {
            queryString = queryString.substring(queryString.indexOf("?") + 1);
        }
        Map<String, String> map = new HashMap<>();
        for (String seg : queryString.split("&")) {
            String[] strs = seg.split("=");
            map.put(strs[0], strs.length == 1 ? null : strs[1]);
        }
        return map;
    }

    public static String serialize(Map<String, String> map) {
        List<String> params = new ArrayList<>();
        map.forEach((key, val) -> params.add(key + "=" + val));
        return String.join("&", params);
    }
    public static String serialize(String url, Map<String, String> map) {
        Map<String, String> tmpMap = populate(url);
        map.forEach((k, v) -> tmpMap.put(k, v));
        return url.split("\\?")[0] + "?" + serialize(tmpMap);
    }
}
