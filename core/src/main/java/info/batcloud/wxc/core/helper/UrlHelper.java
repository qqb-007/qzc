package info.batcloud.wxc.core.helper;

import org.apache.commons.lang.StringUtils;

public class UrlHelper {

    public static String toUrl(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        if (path.startsWith("//")) {
            return "http:" + path;
        }
        return path;
    }

}
