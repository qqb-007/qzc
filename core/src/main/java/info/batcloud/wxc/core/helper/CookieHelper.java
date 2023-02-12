package info.batcloud.wxc.core.helper;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class CookieHelper {

    public static Cookie lookup(CookieStore cookieStore, String domain, String name) {
        for (Cookie cookie : cookieStore.getCookies()) {
            if(cookie.getName().equals(name) && cookie.getDomain().equals(domain)) {
                return cookie;
            }
        }
        return null;
    }

    public static String lookupValue(CookieStore cookieStore, String domain, String name) {
        Cookie cookie = lookup(cookieStore, domain, name);
        if(cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

}
