package info.batcloud.wxc.core.printers.zhongwu;


import jd.sdk.util.MD5Util;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.*;

public class Test {
    private final static Logger LOGGER = LoggerFactory.getLogger(Test.class);

    /**
     * 默认编码为UTF-8
     */
    private static final String HTTP_CONTENT_CHARSET = "UTF-8";

    public static final Integer CONN_TIME_OUT = 3000;

    public static final Integer READ_TIME_OUT = 3000;

    public static HttpClient client = null;

    /**
     * @param args
     */
    public static void main(String[] args) {
//        String s = "http://imgs.wangxiaocai.cn/adv/033ef55b-2f0a-4171-b6b9-70c872e1d322.jpg";
//        int i = s.lastIndexOf("cn/");
//        String fullname = s.substring(i + 3);
//        int i1 = fullname.lastIndexOf("/");
//        String name = fullname.substring(i1 + 1);
//        String bucketName = "tgyx";
//        String newName = "sy1" + name;
//        String targetImage = fullname.substring(0, i1 + 1) + newName;
//        StringBuilder sbStyle = new StringBuilder();
//        sbStyle.delete(0, sbStyle.length());
//        String styleType = "image/watermark,image_YWR2LzIwMjAwODE4LzQucG5n,t_100,g_south,x_0,y_0";
//        Formatter styleFormatter = new Formatter(sbStyle);
//        styleFormatter.format("%s|sys/saveas,o_%s,b_%s", styleType,
//                BinaryUtil.toBase64String(targetImage.getBytes()),
//                BinaryUtil.toBase64String(bucketName.getBytes()));
//        ProcessObjectRequest request = new ProcessObjectRequest(bucketName, fullname, sbStyle.toString());
//        GenericResult processResult = ossClient.processObject(request);
//        String json = null;
//        try {
//            json = com.aliyun.oss.common.utils.IOUtils.readStreamAsString(processResult.getResponse().getContent(), "UTF-8");
//            processResult.getResponse().getContent().close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("菜老板图片添加成功" + json);
//        System.out.println(s.substring(0, i + 3) + targetImage);


        String STIME = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = getSing(STIME);
        Map<String, String> params = new HashMap<>();
        params.put("appid", "8000705");
        params.put("sign", sign);
        params.put("timestamp", STIME);
        params.put("deviceid", "12422607");
        params.put("devicesecret", "te7jqciy");
        params.put("printdata", "测试打印");
        String execute = execute("http://api.zhongwuyun.com", params);
        System.out.println(execute);
        //logger.info("添加易联云打印机返回" + execute);
        //return JSON.parseObject(execute, ZhongwuAPI.addResult.class);


    }

    private static String getSing(String STIME){

        String sign = "appid8000705" +
                 "deviceid12422607"
                + "devicesecrette7jqciy"
                + "printdata测试打印"
                + "timestamp" + STIME
                + "47081b94ddae4e7ed74bd08f9a653ef9";
        String md5String = MD5Util.getMD5String(sign);
        return md5String;
    }

    public static String sendPost(String url, Map<String, Object> param) throws Exception{
        HttpPost post = new HttpPost(url);
        String result = "";
        try {
            if (param != null && !param.isEmpty()) {
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                Set<Map.Entry<String, Object>> entrySet = param.entrySet();
                for (Map.Entry<String, Object> entry : entrySet) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), ((String) entry.getValue())));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                post.setEntity(entity);
            }
            // 设置参数
            RequestConfig.Builder customReqConf = RequestConfig.custom();
            if (CONN_TIME_OUT != null) {
                customReqConf.setConnectTimeout(CONN_TIME_OUT);
            }
            if (READ_TIME_OUT != null) {
                customReqConf.setSocketTimeout(READ_TIME_OUT);
            }
            post.setConfig(customReqConf.build());
            HttpResponse res = client.execute(post);
            result = IOUtils.toString(res.getEntity().getContent(), HTTP_CONTENT_CHARSET);
        } catch (ConnectTimeoutException e) {
            result = "{\"code\":\"-1\", \"msg\":\"请求连接超时\"}";
            LOGGER.error("调用HttpsUtil.sendPost ConnectTimeoutException, url=" + url, e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (SocketTimeoutException e) {
            result = "{\"code\":\"-1\", \"msg\":\"请求响应超时\"}";
            LOGGER.error("调用HttpsUtil.sendPost SocketTimeoutException, url=" + url, e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            result = "{\"code\":\"-1\", \"msg\":\"请求响应超时\"}";
            LOGGER.error("调用HttpsUtil.sendPost IOException, url=" + url, e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            result = "{\"code\":\"-1\", \"msg\":\"服务开小差，系统努力修复中\"}";
            LOGGER.error("调用HttpsUtil.sendPost Exception, url=" + url, e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                try {
                    ((CloseableHttpClient) client).close();
                } catch (IOException e) {
                    LOGGER.error("调用HttpsUtil.sendPost关闭连接 IOException, url=" + url, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return result;
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
        //nvps.add(new BasicNameValuePair("id", UUID.randomUUID().toString()));
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

    public static String getBASE64(String str) {
        if (str == null) return null;
        return (new BASE64Encoder()).encode( str.getBytes() );
    }
    /**
     * 中文转unicode,如果参数中有中文，需要转一下
     *
     * @param gbString
     * @return
     */
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    public static byte[] image2byte(String path) {
        byte[] data = null;
        URL url = null;
        InputStream input = null;
        try{
            url = new URL(path);
            HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            input = httpUrl.getInputStream();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[2147483647];
        int numBytesRead = 0;
        try {
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();

            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }



    public static byte[] getBytes(File file) throws Exception {
        byte[] bytes = null;
        if (file != null) {
            try {
                InputStream fileInputStream = new FileInputStream(file);
                Throwable var3 = null;

                Object var5;
                try {
                    int length = (int)file.length();
                    if (length <= 2147483647) {
                        bytes = new byte[length];
                        int offset = 0;

                        int numRead;
                        for(boolean var6 = false; offset < length && (numRead = fileInputStream.read(bytes, offset, length - offset)) >= 0; offset += numRead) {
                            ;
                        }

                        if (offset >= length) {
                            return bytes;
                        }

                        Object var7 = null;
                        return (byte[])var7;
                    }

                    var5 = null;
                } catch (Throwable var19) {
                    var3 = var19;
                    throw var19;
                } finally {
                    if (fileInputStream != null) {
                        if (var3 != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable var18) {
                                var3.addSuppressed(var18);
                            }
                        } else {
                            fileInputStream.close();
                        }
                    }

                }

                return (byte[])var5;
            } catch (IOException var21) {
                throw var21;
            }
        } else {
            return bytes;
        }
    }
}


