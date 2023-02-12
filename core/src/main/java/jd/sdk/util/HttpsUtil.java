package jd.sdk.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTPS工具类
 * ClassName HttpsUtil </br>
 * 2016年9月23日 上午10:22:19 </br>
 * @version 1.0.0
 */
public class HttpsUtil {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpsUtil.class);

	/**
	 * 默认编码为UTF-8
	 */
	private static final String HTTP_CONTENT_CHARSET = "UTF-8";

	public static final Integer CONN_TIME_OUT = 3000;

	public static final Integer READ_TIME_OUT = 3000;

	public static HttpClient client = null;

	static {
		Security.addProvider(new BouncyCastleProvider());
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}
				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}
				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslsf).build();
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			cm.setMaxTotal(100);
			cm.setDefaultMaxPerRoute(20);
			client = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm).setConnectionManagerShared(true).build();
		} catch (Exception e) {
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(100);
			cm.setDefaultMaxPerRoute(20);
			client = HttpClients.custom().setConnectionManager(cm).build();
			e.printStackTrace();
		}
	}
	
	/**
	 * POST方式发送HTTPS请求
	 * sendPost </br>
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception </br>
	 * String
	 */
	public static String sendPost(String url, Map<String, Object> param) throws Exception{
		HttpPost post = new HttpPost(url);
		String result = "";
		try {
			if (param != null && !param.isEmpty()) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				Set<Entry<String, Object>> entrySet = param.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					formParams.add(new BasicNameValuePair(entry.getKey(), ((String) entry.getValue())));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
				post.setEntity(entity);
			}
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
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

	/**
	 * 提交form表单
	 * 
	 * @param url
	 * @param params
	 * @param connTimeout
	 * @param readTimeout
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception
	 */
	public static String postForm(String url, Map<String, String> params, Map<String, String> headers, Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException, SocketTimeoutException, Exception {
		HttpPost post = new HttpPost(url);
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				Set<Entry<String, String>> entrySet = params.entrySet();
				for (Entry<String, String> entry : entrySet) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
				post.setEntity(entity);
			}
			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, String> entry : headers.entrySet()) {
					post.addHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			if (connTimeout != null) {
				customReqConf.setConnectTimeout(connTimeout);
			}
			if (readTimeout != null) {
				customReqConf.setSocketTimeout(readTimeout);
			}
			post.setConfig(customReqConf.build());
			HttpResponse res = client.execute(post);
			return IOUtils.toString(res.getEntity().getContent(), "UTF-8");
		} finally {
			post.releaseConnection();
			if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
				((CloseableHttpClient) client).close();
			}
		}
	}

	/**
	 * GET方式发送HTTPS请求
	 * sendGet </br>
	 * @param url
	 * @param param
	 * @return
	 * @throws Exception </br>
	 * String
	 */
	public static String sendGet(String url, Map<String, Object> param) throws Exception{
		HttpGet get = null;
		String result = "";
		String paramStr = "";
		try {
			if (param != null) {
				StringBuilder sb = new StringBuilder();
				for (Entry<String, Object> entry : param.entrySet()) {
					if (entry.getValue() != null) {
						sb.append(entry.getKey()).append("=").append(URLEncoder.encode(((String) entry.getValue()), HTTP_CONTENT_CHARSET)).append("&");
					}
				}
				paramStr = sb.toString();
				paramStr = paramStr.substring(0, paramStr.length() - 1);
			}
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			if (CONN_TIME_OUT != null) {
				customReqConf.setConnectTimeout(CONN_TIME_OUT);
			}
			if (READ_TIME_OUT != null) {
				customReqConf.setSocketTimeout(READ_TIME_OUT);
			}
			get = new HttpGet(url + "?" + paramStr);
			get.setConfig(customReqConf.build());
			HttpResponse res = client.execute(get);
			result = IOUtils.toString(res.getEntity().getContent(), HTTP_CONTENT_CHARSET);
		} catch (IOException e) {
			result = "{\"code\":\"-1\", \"msg\":\"请求响应超时\"}";
			LOGGER.error("调用HttpsUtil.sendGet IOException, url=" + url, e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			result = "{\"code\":\"-1\", \"msg\":\"服务开小差，系统努力修复中\"}";
			LOGGER.error("调用HttpsUtil.sendGet Exception, url=" + url, e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			if(get!=null){
				get.releaseConnection();
			}
			if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
				try {
					((CloseableHttpClient) client).close();
				} catch (IOException e) {
					LOGGER.error("调用HttpsUtil.sendGet 关闭连接IOException, url=" + url, e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 判断url是否为https接口
	 * checkUrlIsHttps </br>
	 * @param url
	 * @return </br>
	 * boolean
	 */
	public static boolean checkUrlIsHttps(String url){
		boolean result = false;
		URI uri = null;
		try {
			URL ur = new URL(url);
			uri = ur.toURI();
		} catch (Exception e) {
			e.printStackTrace();
		}
        if (uri != null) {
        	if ("https".equalsIgnoreCase(uri.getScheme())) {
        		result = true;
        	}
        } else {
        	if (url.toLowerCase().indexOf("https") == 0) {
        		result = true;
        	}
        }
		return result;
	}
	
}