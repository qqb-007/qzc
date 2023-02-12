package info.batcloud.wxc.core.printers.yilianyun;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class YilianyunAPI {

    public static final String clientId = "1038022204";

    public static final String clientSecret = "7581c8658261a4be2dec4d1ba35a1f4f";

    private static final Logger logger = LoggerFactory.getLogger(YilianyunAPI.class);

    public static void main(String[] args) throws Exception {

    }

    public static addResult addPrinter(String machineCode, String msign) {
        String STIME = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = getSign(STIME);
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("machine_code", machineCode);
        params.put("msign", msign);
        params.put("scope", "all");
        params.put("sign", sign);
        params.put("timestamp", STIME);
        String execute = execute("https://open-api.10ss.net/oauth/scancodemodel", params);
        logger.info("添加易联云打印机返回" + execute);
        return JSON.parseObject(execute, addResult.class);
    }

    public static delResult delPrinter(String access_token, String machine_code) {
        String STIME = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = getSign(STIME);
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("machine_code", machine_code);
        params.put("access_token", access_token);
        params.put("sign", sign);
        params.put("timestamp", STIME);
        String execute = execute("https://open-api.10ss.net/printer/deleteprinter", params);
        logger.info("删除易联云打印机返回" + execute);
        return JSON.parseObject(execute, delResult.class);
    }

    public static printResult print(String access_token, String content, String origin_id, String machine_code) {
        String STIME = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = getSign(STIME);
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("machine_code", machine_code);
        params.put("access_token", access_token);
        params.put("content", content);
        params.put("origin_id", origin_id);
        params.put("sign", sign);
        params.put("timestamp", STIME);
        String execute = execute("https://open-api.10ss.net/print/index", params);
        logger.info("易联云打印机打印返回" + execute);
        return JSON.parseObject(execute, printResult.class);
    }

    public static TokenResult getToken(String refresh_token	){
        String STIME = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = getSign(STIME);
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("grant_type", "refresh_token");
        params.put("scope", "all");
        params.put("sign", sign);
        params.put("refresh_token", refresh_token);
        params.put("timestamp", STIME);
        String execute = execute("https://open-api.10ss.net/oauth/oauth", params);
        logger.info("易联云打印机获取token返回" + execute);
        return JSON.parseObject(execute,TokenResult.class);
    }


    //生成签名字符串
    private static String getSign(String STIME) {

        String md5String = MD5Util.getMD5String(clientId + STIME + clientSecret);
        return md5String;
    }

    private static String execute(String url, Map<String, String> params) {
        //通过POST请求，发送打印信息到服务器
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)//读取超时
                .setConnectTimeout(30000)//连接超时
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("id", UUID.randomUUID().toString()));
        params.forEach((k, v) -> nvps.add(new BasicNameValuePair(k, v)));
        CloseableHttpResponse response = null;
        String result = null;
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps, "utf-8");
            post.setEntity(urlEncodedFormEntity);
            response = httpClient.execute(post);
            int stateCode = response.getStatusLine().getStatusCode();
            if (stateCode == 200) {
                HttpEntity httpentity = response.getEntity();
                if (httpentity != null) {
                    //服务器返回的JSON字符串，建议要当做日志记录起来
                    result = EntityUtils.toString(httpentity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                post.abort();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static class TokenResult{
        private int error;
        private String error_description;
        private TokenBody body;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }

        public TokenBody getBody() {
            return body;
        }

        public void setBody(TokenBody body) {
            this.body = body;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TokenResult that = (TokenResult) o;
            return Objects.equals(error, that.error) &&
                    Objects.equals(error_description, that.error_description) &&
                    Objects.equals(body, that.body);
        }

        @Override
        public int hashCode() {
            return Objects.hash(error, error_description, body);
        }

        @Override
        public String toString() {
            return "TokenResult{" +
                    "error='" + error + '\'' +
                    ", error_description='" + error_description + '\'' +
                    ", body=" + body +
                    '}';
        }
    }

    public static class TokenBody{
        private String access_token;
        private String refresh_token;
        private String machine_code;
        private String expires_in;
        private String scope;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getMachine_code() {
            return machine_code;
        }

        public void setMachine_code(String machine_code) {
            this.machine_code = machine_code;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TokenBody tokenBody = (TokenBody) o;
            return Objects.equals(access_token, tokenBody.access_token) &&
                    Objects.equals(refresh_token, tokenBody.refresh_token) &&
                    Objects.equals(machine_code, tokenBody.machine_code) &&
                    Objects.equals(expires_in, tokenBody.expires_in) &&
                    Objects.equals(scope, tokenBody.scope);
        }

        @Override
        public int hashCode() {
            return Objects.hash(access_token, refresh_token, machine_code, expires_in, scope);
        }

        @Override
        public String toString() {
            return "TokenBody{" +
                    "access_token='" + access_token + '\'' +
                    ", refresh_token='" + refresh_token + '\'' +
                    ", machine_code='" + machine_code + '\'' +
                    ", expires_in='" + expires_in + '\'' +
                    ", scope='" + scope + '\'' +
                    '}';
        }
    }

    public static class delResult {
        private int error;
        private String error_description;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            delResult delResult = (delResult) o;
            return Objects.equals(error, delResult.error) &&
                    Objects.equals(error_description, delResult.error_description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(error, error_description);
        }

        @Override
        public String toString() {
            return "delResult{" +
                    "error='" + error + '\'' +
                    ", error_description='" + error_description + '\'' +
                    '}';
        }
    }

    public static class addResult {
        private int error;
        private String error_description;
        private Body body;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            addResult addResult = (addResult) o;
            return Objects.equals(error, addResult.error) &&
                    Objects.equals(error_description, addResult.error_description) &&
                    Objects.equals(body, addResult.body);
        }

        @Override
        public int hashCode() {
            return Objects.hash(error, error_description, body);
        }

        @Override
        public String toString() {
            return "addResult{" +
                    "error='" + error + '\'' +
                    ", error_description='" + error_description + '\'' +
                    ", body=" + body +
                    '}';
        }
    }

    public static class Body {
        private String access_token;
        private String refresh_token;
        private String machine_code;
        private String expires_in;
        private String scope;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        public String getMachine_code() {
            return machine_code;
        }

        public void setMachine_code(String machine_code) {
            this.machine_code = machine_code;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Body body = (Body) o;
            return Objects.equals(access_token, body.access_token) &&
                    Objects.equals(refresh_token, body.refresh_token) &&
                    Objects.equals(machine_code, body.machine_code) &&
                    Objects.equals(expires_in, body.expires_in) &&
                    Objects.equals(scope, body.scope);
        }

        @Override
        public int hashCode() {
            return Objects.hash(access_token, refresh_token, machine_code, expires_in, scope);
        }

        @Override
        public String toString() {
            return "Body{" +
                    "access_token='" + access_token + '\'' +
                    ", refresh_token='" + refresh_token + '\'' +
                    ", machine_code='" + machine_code + '\'' +
                    ", expires_in='" + expires_in + '\'' +
                    ", scope='" + scope + '\'' +
                    '}';
        }
    }

    public static class printResult {
        private int error;
        private String error_description;
        private PrintBody body;

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getError_description() {
            return error_description;
        }

        public void setError_description(String error_description) {
            this.error_description = error_description;
        }

        public PrintBody getBody() {
            return body;
        }

        public void setBody(PrintBody body) {
            this.body = body;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            printResult that = (printResult) o;
            return Objects.equals(error, that.error) &&
                    Objects.equals(error_description, that.error_description) &&
                    Objects.equals(body, that.body);
        }

        @Override
        public int hashCode() {
            return Objects.hash(error, error_description, body);
        }

        @Override
        public String toString() {
            return "printResult{" +
                    "error='" + error + '\'' +
                    ", error_description='" + error_description + '\'' +
                    ", body=" + body +
                    '}';
        }
    }

    public static class PrintBody {
        private String id;
        private String origin_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrigin_id() {
            return origin_id;
        }

        public void setOrigin_id(String origin_id) {
            this.origin_id = origin_id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PrintBody printBody = (PrintBody) o;
            return Objects.equals(id, printBody.id) &&
                    Objects.equals(origin_id, printBody.origin_id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, origin_id);
        }

        @Override
        public String toString() {
            return "PrintBody{" +
                    "id='" + id + '\'' +
                    ", origin_id='" + origin_id + '\'' +
                    '}';
        }
    }
}
