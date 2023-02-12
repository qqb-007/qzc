package me.ele.sdk.down;

import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {
    /**
     * 根据参数获取签名
     *
     * @param
     * @return String
     */
    public static String getSign(PushForm form, String secret) {
        TreeMap<String, Object> arr = new TreeMap<String, Object>();
        arr.put("body", form.getBody());
        arr.put("cmd", form.getCmd());
        arr.put("encrypt", form.getEncrypt());
        arr.put("secret", secret);
        arr.put("source", form.getSource());
        arr.put("ticket", form.getTicket());
        arr.put("timestamp", form.getTimestamp());
        arr.put("version", form.getVersion());
        StringBuilder strSignTmp = new StringBuilder();
        Iterator it = arr.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            strSignTmp.append(key + "=" + arr.get(key) + "&");
        }
        String strSign = strSignTmp.toString().substring(0, strSignTmp.length() - 1);
        String sign = getMD5(strSign);
        return sign;
    }

    /**
     * 校验签名是否正确
     *
     * @param
     * @return boolean
     */
    public static boolean checkSign(PushForm form, String secret) {
        String signFrom = getSign(form, secret);
        String sign = form.getSign();
        if (signFrom.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取MD5
     *
     * @param
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
