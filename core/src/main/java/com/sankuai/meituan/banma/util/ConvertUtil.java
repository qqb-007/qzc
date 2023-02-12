package com.sankuai.meituan.banma.util;

import com.alibaba.fastjson.JSON;
import com.sankuai.meituan.banma.annotation.Param;
import com.sankuai.meituan.waimai.opensdk.constants.ErrorEnum;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by yangzhiqi on 15/10/16.
 */
public class ConvertUtil {

    /**
     * 对象转化成map
     * @param object
     * @return
     */
    public static Map<String,String> convertToMap(Object object){
        Map<String,String> resultMap = new HashMap<>();
        try{
            Class clazz = object.getClass();
            while(clazz != Object.class) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields){
                    field.setAccessible(true);
                    Class typeClazz = field.getType();
                    Param param = field.getAnnotation(Param.class);
                    if (param == null) {
                        continue;
                    }
                    String key = param.value();
                    String val = null;
                    if(param.json()){
                        val = JSON.toJSONString(field.get(object));
                    } else {
                        val = String.valueOf(field.get(object));
                    }
                    if (val != null && !"".equals(val) && !"null".equals(val) && !"NULL".equals(val)) {
                        resultMap.put(key, val);
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return resultMap;
    }

    /**
     * 系统参数转化成map
     * @param systemParam
     * @return
     */
    public static Map<String,String> convertSystemParamsToMap(SystemParam systemParam){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        resultMap.put("app_id",systemParam.getAppId());
        resultMap.put("appSecret",systemParam.getAppSecret());
        return resultMap;
    }

    /**
     * 对象转化为参数列表
     * @param applicationParamsMap
     * @return
     */
    public static List<BasicNameValuePair> convertToEntity(Map<String,String> applicationParamsMap)
        throws ApiSysException{
        List<BasicNameValuePair> formParam = new ArrayList<>();
        try{
            if(applicationParamsMap != null){
                for (Map.Entry<String, String> entry : applicationParamsMap.entrySet()) {
                    BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                    formParam.add(nameValuePair);
                }
            }
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }

        return formParam;
    }

    /**
     * 对象转化为参数列表
     * @param applicationParamsMap
     * @return
     */
    public static List<NameValuePair> convertToEntityBasic(Map<String,String> applicationParamsMap)
        throws ApiSysException{
        List<NameValuePair> formParam = new ArrayList<>();
        try{
            if(applicationParamsMap != null){
                Iterator<Map.Entry<String, String>> iterator = applicationParamsMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), JSON.toJSONString(entry.getValue()));
                    formParam.add(nameValuePair);
                }
            }
        }catch (Exception e){
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }

        return formParam;
    }

}
