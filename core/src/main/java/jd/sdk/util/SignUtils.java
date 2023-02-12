package jd.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ClassName:SignUtils <br/>
 * Function: 签名规则计算工具类
 * Reason:	 1.所有请求参数按照字母先后顺序排列.
 * 			 2.把所有参数名和参数值进行拼装.
 * 			 3.把appSecret夹在字符串的两端.
 * 			 4.使用MD5进行加密，再转化成大写.
 * @author zhoudeming </br>
 * @version 1.0.0
 */
public class SignUtils {
	private final static Logger LOGGER = LoggerFactory.getLogger(SignUtils.class);
	
	/**
	 * 拼接所有业务参数
	 * concatParams </br>
	 * @param params2
	 * @return </br>
	 * String
	 */
	private static String concatParams(Map<String, String> params2) {
		Object[] key_arr = params2.keySet().toArray();
		Arrays.sort(key_arr);
		StringBuilder sb = new StringBuilder();
		for (Object key : key_arr) {
			String val = params2.get(key);
			sb.append(key).append(val);
		}
		return sb.toString();
	}

	/**
	 * 拼接对象属性
	 * getProperty </br>
	 * @param entityName
	 * @return
	 * @throws Exception </br>
	 * Map<String,String>
	 */
	public static Map<String,String> getProperty(Object entityName) throws Exception {
		Map<String,String> mapResult=new HashMap<String, String>();
		Class<? extends Object> c = entityName.getClass();
		Field field[] = c.getDeclaredFields();
		for (Field f : field) {
			int mod = f.getModifiers();
			if (!Modifier.isFinal(mod) && !Modifier.isStatic(mod)) {
				Object v = invokeMethod(entityName, f.getName(), null);
				if (v == null || "sign".equals(f.getName())) {
					continue;
				}
				mapResult.put(f.getName(), v.toString());
			}
		}
		return mapResult;
	}
	
	/**
	 * 获取属性值
	 * invokeMethod </br>
	 * @param owner
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception </br>
	 * Object
	 */
	private static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
		Class<? extends Object> ownerClass = owner.getClass();
		methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		}catch (Exception e) {
			LOGGER.error(" can't find 'get" + methodName + "' method");
			return null;
		}
		return method.invoke(owner);
	}
	
	/**
	 * 
	 * getSign 获取MD5签名
	 *
	 * @param appSecret 应用secret
	 * @return </br>
	 * String
	 */
	public static String getSignByMD5(Object param,String appSecret ) throws Exception{
		String sysStr = concatParams(getProperty(param));
		StringBuilder resultStr = new StringBuilder("");
		resultStr.append(appSecret).append(sysStr).append(appSecret);
		return MD5Util.getMD5String(resultStr.toString()).toUpperCase();
	}
	
	/**
	 * 
	 * getSign 获取SHA512签名
	 *
	 * @param appSecret 应用secret
	 * @return </br>
	 * String
	 */
	public static String getSignBySHA512(Object param,String appSecret ) throws Exception{
		String sysStr = concatParams(getProperty(param));
		StringBuilder resultStr = new StringBuilder("");
		resultStr.append(appSecret).append(sysStr).append(appSecret);
		return DigestUtils.sha512Hex(resultStr.toString()).toUpperCase();
	}	
	
}

