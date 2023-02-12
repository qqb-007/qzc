package wante.sdk.up.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {
    private static final int DEFAULT_TIMEOUT = 30000;
    private static final Log logger = LogFactory.getLog(HttpRequestUtil.class);


    public static String sendGet(String url, String param, String secret) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            System.out.println(urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", secret);
            connection.setRequestProperty("REMEMBER-ME", "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
            connection.setRequestProperty("X-CSRF-TOKEN", "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
            connection.setRequestProperty("ADMIN-TOKEN", "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
//            connection.setRequestProperty("SECRET-TOKEN", "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            logger.info(String.format("response data: %s", result));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    public static String sendPost(String url, Map<String, String> params, String secret) throws IOException {
        if (StringUtils.isEmpty(url) || params == null || params.isEmpty()) {
            return "";
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;

        try {
            if (params.size() > 0) {
                url = url + "?";
                for (Map.Entry<String, String> entity : params.entrySet()) {
                    url = url + entity.getKey() + "=" + URLEncoder.encode(entity.getValue(), "UTF-8") + "&";
                }
                url = url.substring(0, url.length() - 1);
                System.out.println(url);
            }
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setSocketTimeout(DEFAULT_TIMEOUT)
                    .setConnectTimeout(DEFAULT_TIMEOUT)
                    .build();//设置请求和传输超时时间

            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("accept", "*/*");
            httpPost.setHeader("Authorization", secret);
            httpPost.setHeader("REMEMBER-ME",
                    "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
            httpPost.setHeader("X-CSRF-TOKEN", "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
            httpPost.setHeader("ADMIN-TOKEN", "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
//            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<>();
//            System.out.println("================================");
//            for (Map.Entry<String, String> entity : params.entrySet()) {
////                basicNameValuePairs.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
////                System.out.println(entity.getKey()+"="+ entity.getValue());
////            }
//            System.out.println("================================");
//            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(basicNameValuePairs, Consts.UTF_8);
//            httpPost.setEntity(urlEncodedFormEntity);
            response = httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, Consts.UTF_8);
            logger.info(String.format("response data: %s", result));

            return result == null ? "" : result.trim();

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error("close http client failed", e);
            }
        }

    }

    public static String sendJsonPost(String json, String URL, String secret) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("accept", "*/*");
        httpPost.setHeader("Authorization", secret);
        httpPost.setHeader("REMEMBER-ME",
                "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
        httpPost.setHeader("X-CSRF-TOKEN", "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
        httpPost.setHeader("ADMIN-TOKEN", "b5fd81eb-40a8-4bfb-8099-b58f8c6ff6bd");
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
