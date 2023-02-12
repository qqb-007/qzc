package inf.batcloud.fanli.core.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlCleanTest {

    private static Pattern pattern = Pattern.compile("\\{[^\\}]+\\}");

    @Test
    public void testMatcher() {
        String url = "http://www.028tg.cn/a{id}-{tt}-123";
        Matcher matcher = pattern.matcher(url);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "000");
        }
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }

}
