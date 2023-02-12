package me.ele.sdk.up;

import com.alibaba.fastjson.JSON;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {
	/**
	 * 根据参数获取签名
	 * @return String
	 */
	public static String getSign(Map<String, Object> data) {
		TreeMap<String, Object> arr = new TreeMap<String, Object>();
		arr.put("body", JSON.toJSONString(data.get("body")));
		arr.put("cmd", data.get("cmd"));
		arr.put("encrypt", "");
		arr.put("secret", data.get("secret"));
		arr.put("source", data.get("source"));
		arr.put("ticket", data.get("ticket"));
		arr.put("timestamp", data.get("timestamp"));
		arr.put("version", data.get("version"));
		StringBuilder strSignTmp = new StringBuilder("");
		Iterator it = arr.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			strSignTmp.append(key + "=" + arr.get(key) + "&");
		}
		String strSign = strSignTmp.toString().substring(0, strSignTmp.length() - 1);
		String sign = getMD5(strSign.toString());
		return sign;
	}

	/**
	 * 获取MD5
	 * @return String
	 */
	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext.toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
