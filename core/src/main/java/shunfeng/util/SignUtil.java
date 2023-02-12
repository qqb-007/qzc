package shunfeng.util;


public class SignUtil {
    /**
     * 签名方法
     * 参数：content：要发送的JSON结构字符串
     * 返回值：签名信息字符串
     * 注意：签名计算结果可以到此页面进行验证：http://sftc.jsonce.com/sign/
     */
    public static String sign(String content, String key, Integer appId) throws java.io.UnsupportedEncodingException {
        // 假设原始内容JSON为 {"hello":"kitty"}
        // content : "{\"hello\":\"kitty\"}"

        String toSign = content + "&" + appId + "&" + key;
        // toSign : "{\"hello\":\"kitty\"}&1234567890&0123456789abcdef0123456789abcdef";

        String md5Result = md5(toSign.getBytes("utf-8"));
        // md5Result : "ef3435b1480e553480e19e3e162fb0be"

        String signResult = base64Encode(md5Result.getBytes("utf-8"));
        // signResult : "ZWYzNDM1YjE0ODBlNTUzNDgwZTE5ZTNlMTYyZmIwYmU="

        return signResult;
    }

    public static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    public static String md5(byte[] toSignBytes) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(toSignBytes);
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64Encode(byte[] md5ResultBytes) {
        java.util.Base64.Encoder be = java.util.Base64.getEncoder();
        String b64Result = be.encodeToString(md5ResultBytes);
        return b64Result;
    }
}
