package wante.sdk.up;
import com.alibaba.fastjson.JSON;
import wante.sdk.up.util.HttpRequestUtil;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class WanteClient {

    public <T> T execute(AbstractRequest req) {
        String result = null;

        if (req.getMethod() == AbstractRequest.Method.GET) {
            result = HttpRequestUtil.sendGet(req.getURL(), getParams(req),req.getSecret());
        } else if (req.getMethod() == AbstractRequest.Method.POST) {
            if (req.isJson()) {
                result = HttpRequestUtil.sendJsonPost(req.getJson(), req.getURL(),req.getSecret());
            } else {
                try {
                    result = HttpRequestUtil.sendPost(req.getURL(), postParams(req),req.getSecret());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

        }
        return (T) JSON.parseObject(result, req.getResponseType());
    }

    private String getParams(Object clazz) {
        // 遍历属性类、属性值
        Field[] fields = clazz.getClass().getDeclaredFields();

        StringBuilder requestURL = new StringBuilder();
        try {
            boolean flag = true;
            String property, value;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                // 允许访问私有变量
                field.setAccessible(true);

                // 属性名
                property = field.getName();
                // 属性值
                value = field.get(clazz).toString();

                String params = property + "=" + value;
                if (flag) {
                    requestURL.append(params);
                    flag = false;
                } else {
                    requestURL.append("&" + params);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return requestURL.toString();
    }

    private Map<String, String> postParams(Object clazz) {
        // 遍历属性类、属性值
        Field[] fields = clazz.getClass().getDeclaredFields();

        Map<String, String> map = new HashMap<>();
        try {
            String property, value;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                // 允许访问私有变量
                field.setAccessible(true);
                // 属性名
                property = field.getName();
                // 属性值
                value = field.get(clazz).toString();
                map.put(property, value);
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return map;
    }
}
