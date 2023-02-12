package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.domain.aliyun.OssFileType;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

public class OssHelper {

    public static OssFileType determineType(String key) {
        if (key.endsWith("/")) {
            return OssFileType.DIRECTORY;
        }
        return OssFileType.IMAGE;
    }

    public static String genKey(String folder, String suffix) {
        if (!folder.startsWith("/")) {
            folder = "/" + folder;
        }
        if (!suffix.startsWith(".")) {
            suffix = "." + suffix;
        }
        return folder + "/" + DateFormatUtils.format(new Date(), "yyyy/mm/dd") + suffix;
    }

}
