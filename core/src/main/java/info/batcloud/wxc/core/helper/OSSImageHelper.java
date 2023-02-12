package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.aliyun.OSSConfig;
import info.batcloud.wxc.core.aliyun.OssImageConfig;
import org.apache.commons.lang.StringUtils;

public class OSSImageHelper {

    public static String toUrl(String path) {
        if(StringUtils.isBlank(path)) {
            return null;
        }
        if(path.startsWith("https://") || path.startsWith("http://")) {
            return path;
        }
        String domain = OSSConfig.getInstance().getDomain();
        if(!domain.endsWith("/")) {
            domain += "/";
        }
        return domain + path;
    }

    public static String toUrl(String path, String style) {
        if(StringUtils.isBlank(path)) {
            return null;
        }
        if(path.indexOf("?") != -1) {
            return path;
        }
        return toUrl(path) + "?" + style;
    }

    public static String toLargeUrl(String path) {
        return toUrl(path, OssImageConfig.getInstance().getLargeStyle());
    }

    public static String toMediumUrl(String path) {
        return toUrl(path, OssImageConfig.getInstance().getMediumStyle());
    }

    public static String toSmallUrl(String path) {
        return toUrl(path, OssImageConfig.getInstance().getSmallStyle());
    }

    public static String toTinyUrl(String path) {
        return toUrl(path, OssImageConfig.getInstance().getTinyStyle());
    }

}
