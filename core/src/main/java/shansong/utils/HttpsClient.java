package shansong.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class HttpsClient {
    public static String sendPost(String url, Map<String,Object> params) {
        String response = null;
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (String key : params.keySet()) {
                    pairs.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
            }
            CloseableHttpClient httpClient = null;
            CloseableHttpResponse httpResponse = null;
            try {
                httpClient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                if (pairs != null && pairs.size() > 0) {
                    httppost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
                }
                httpResponse = httpClient.execute(httppost);
                response = EntityUtils
                        .toString(httpResponse.getEntity());
                //System.out.println(response);
            } finally {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (httpResponse != null) {
                    httpResponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
