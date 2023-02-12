package info.batcloud.wxc.core.helper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class PictureHelper {
    /**
     * 图片转为byte数组
     *
     * @param path
     * @return
     */
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
        byte[] buf = new byte[1024];
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
    /**
     * 将网络图片转成Base64码，此方法可以解决解码后图片显示不完整的问题
     * @param imgURL
     * 例如：http://***.com/271025191524034.jpg
     * @return
     */
    public static String imgBase64(String imgURL) {
        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "fail";//连接失败/链接失效/图片不存在
            }
            InputStream inStream = conn.getInputStream();
            int len = -1;
            while ((len = inStream.read(data)) != -1) {
                outPut.write(data, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(outPut.toByteArray());
    }

    public static String getPicSuffix(String img_path){
        if (img_path == null || img_path.indexOf("/") == -1){
            return ""; //如果图片地址为null或者地址中没有"."就返回""
        }
        return img_path.substring(img_path.lastIndexOf("/") + 1).
                trim().toLowerCase();
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
