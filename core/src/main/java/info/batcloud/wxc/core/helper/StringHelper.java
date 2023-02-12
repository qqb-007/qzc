package info.batcloud.wxc.core.helper;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class StringHelper {

    public static String protect(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        //如果是手机号
        if (str.length() == 11) {
            return str.substring(0, 3) + "****" + str.substring(7);
        }
        int length = str.length();
        int subLength = length / 3;
        if (subLength <= 1) {
            return str.substring(0, 1) + StringUtils.repeat("*", length - 1);
        }
        return str.substring(0, subLength) + StringUtils.repeat("*", subLength) + str.substring(str.length() - subLength);
    }

    public static String evenProtect(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        StringBuffer sb = new StringBuffer();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (i % 2 == 1) {
                sb.append("*");
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static List<String> segment(String keywords) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        return segmenter.sentenceProcess(keywords);
    }

    public static String substring(String str, int length) {
        int count = 0;
        int offset;
        char[] c = str.toCharArray();
        int size = c.length;
        if(size >= length){
            for (int i = 0; i < c.length; i++) {
                if (c[i] > 256) {
                    offset = 2;
                    count += 2;
                } else {
                    offset = 1;
                    count++;
                }
                if (count == length) {
                    return str.substring(0, i + 1);
                }
                if ((count == length + 1 && offset == 2)) {
                    return str.substring(0, i);
                }
            }
        }else{
            return str;
        }
        return "";
    }
}
