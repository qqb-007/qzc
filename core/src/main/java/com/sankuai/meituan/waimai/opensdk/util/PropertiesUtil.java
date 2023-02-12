package com.sankuai.meituan.waimai.opensdk.util;

import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import org.apache.http.client.config.RequestConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yangzhiqi on 15/11/2.
 */
public class PropertiesUtil {
    private static int socketTimeOut = 5000;
    private static int connectTimeOut = 3000;
    private static int connectRequestTimeOut = 3000;
    private static int maxTotal = 20;
    private static int defaultMaxPerRoute = 20;
    private static int maxPerRoute = 20;

    public static RequestConfig.Builder getRequestConfig() throws ApiSysException{
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectTimeout(PropertiesUtil.getConnectTimeOut());
        requestConfigBuilder.setConnectionRequestTimeout(PropertiesUtil.getConnectRequestTimeOut());
        requestConfigBuilder.setSocketTimeout(PropertiesUtil.getSocketTimeOut());
        return requestConfigBuilder;
    }

    public static String getEnvironmentMode() throws ApiSysException{

        Properties prop = new Properties();
        InputStream inputStream = null;
        String env = "";
        try {
//            inputStream = ClassLoader.getSystemResourceAsStream("environment.properties");
            inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("environment.properties");
            prop.load(inputStream);
            env = prop.getProperty("env");
            return env;
        } catch (FileNotFoundException e) {
            throw new ApiSysException("未找到'environment.properties'环境配置文件", e);
        } catch (IOException e) {
            throw new ApiSysException(e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ApiSysException(e);
                }
            }
        }

    }

    public static String getAPIUrl() throws ApiSysException{

        Properties prop = new Properties();
        InputStream inputStream = null;
        String url = "";
        try {
            inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("environment.properties");
            prop.load(inputStream);
            url = prop.getProperty("url");
            return url;
        } catch (FileNotFoundException e) {
            throw new ApiSysException("未找到'environment.properties'环境配置文件", e);
        } catch (IOException e) {
            throw new ApiSysException(e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ApiSysException(e);
                }
            }
        }

    }


    public static int getSocketTimeOut()throws ApiSysException{

        Properties prop = new Properties();
        InputStream inputStream = null;
        String env = "";
        int socketTimeOut = 0;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream("environment.properties");
            prop.load(inputStream);
            env = prop.getProperty("socketTimeOut");
            socketTimeOut = Integer.parseInt(env);
            if(socketTimeOut > 0){
                return socketTimeOut;
            }else{
                return PropertiesUtil.socketTimeOut;
            }
        } catch (Exception e) {
            return PropertiesUtil.socketTimeOut;
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ApiSysException(e);
                }
            }
        }

    }

    public static int getConnectTimeOut() throws ApiSysException{

        Properties prop = new Properties();
        InputStream inputStream = null;
        String env = "";
        int connectTimeOut = 0;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream("environment.properties");
            prop.load(inputStream);
            env = prop.getProperty("connectTimeOut");
            connectTimeOut = Integer.parseInt(env);
            if(connectTimeOut > 0){
                return connectTimeOut;
            }else{
                return PropertiesUtil.connectTimeOut;
            }
        } catch (Exception e) {
            return PropertiesUtil.connectTimeOut;
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ApiSysException(e);
                }
            }
        }

    }

    public static int getConnectRequestTimeOut() throws ApiSysException{
        Properties prop = new Properties();
        InputStream inputStream = null;
        String env = "";
        int connectRequestTimeOut = 0;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream("environment.properties");
            prop.load(inputStream);
            env = prop.getProperty("connectRequestTimeOut");
            connectRequestTimeOut = Integer.parseInt(env);
            if(connectRequestTimeOut > 0){
                return connectRequestTimeOut;
            }else{
                return PropertiesUtil.connectRequestTimeOut;
            }
        } catch (Exception e) {
            return PropertiesUtil.connectRequestTimeOut;
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new ApiSysException(e);
                }
            }
        }

    }

    public static int getHttpClientPoolMaxTotal() {
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream("environment.properties");
            prop.load(inputStream);
            String env = prop.getProperty("maxTotal");
            int maxTotal = Integer.parseInt(env);
            if (maxTotal > 0) {
                return maxTotal;
            } else {
                return PropertiesUtil.maxTotal;
            }
        } catch (Exception e) {
            return PropertiesUtil.maxTotal;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getHttpClientPoolDefaultMaxPerRoute() {
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream("environment.properties");
            prop.load(inputStream);
            String env = prop.getProperty("defaultMaxPerRoute");
            int defaultMaxPerRoute = Integer.parseInt(env);
            if (defaultMaxPerRoute > 0) {
                return defaultMaxPerRoute;
            } else {
                return PropertiesUtil.defaultMaxPerRoute;
            }
        } catch (Exception e) {
            return PropertiesUtil.defaultMaxPerRoute;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getHttpClientPoolMaxPerRoute() {
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = ClassLoader.getSystemResourceAsStream("environment.properties");
            prop.load(inputStream);
            String env = prop.getProperty("maxPerRoute");
            int maxTotal = Integer.parseInt(env);
            if (maxTotal > 0) {
                return maxTotal;
            } else {
                return PropertiesUtil.maxPerRoute;
            }
        } catch (Exception e) {
            return PropertiesUtil.maxPerRoute;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
