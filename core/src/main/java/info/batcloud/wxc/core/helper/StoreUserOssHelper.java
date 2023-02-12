package info.batcloud.wxc.core.helper;

public class StoreUserOssHelper {

    public static String prefix(long userId) {
        return "store/" + userId + "/";
    }

    public static boolean checkPrefix(long userId, String s) {
        return s.startsWith(prefix(userId));
    }

}
