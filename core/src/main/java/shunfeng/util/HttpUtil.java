package shunfeng.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpUtil {
    private static final int DEFAULT_TIMEOUT = 10000;
    private static final Log logger = LogFactory.getLog(HttpUtil.class);
    public static String sendPost(String json, String URL) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Content-Type", "application/json");
        String result;
        try {
            StringEntity s = new StringEntity(json, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            httpPost.setEntity(s);
            // 发送请求
            HttpResponse httpResponse = client.execute(httpPost);
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
                logger.info(String.format("response data: %s", result));
            } else {
                System.out.println("请求服务端失败");
                logger.info(String.format("response data: %s", result));
            }
        } catch (Exception e) {
            logger.error("请求异常：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }
}
