package uupt.src.com.uupt.openapi;

public class TextUtils {

	/**
	 * 判断字符串是否为空
	 *
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s == null || s.isEmpty()) {
			return true;
		}
		return false;
	}
}
