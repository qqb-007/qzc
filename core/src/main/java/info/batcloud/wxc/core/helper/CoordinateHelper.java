package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.domain.LocateInfo;

public class CoordinateHelper {
    public static double pi = 3.1415926535897932384626;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    public static LocateInfo wgs84_To_Gcj02(double lat, double lon) {
        LocateInfo info = new LocateInfo();
        if (outOfChina(lat, lon)) {
            info.setChina(false);
            info.setLatitude(lat);
            info.setLongitude(lon);
        } else {
            double dLat = transformLat(lon - 105.0, lat - 35.0);
            double dLon = transformLon(lon - 105.0, lat - 35.0);
            double radLat = lat / 180.0 * pi;
            double magic = Math.sin(radLat);
            magic = 1 - ee * magic * magic;
            double sqrtMagic = Math.sqrt(magic);
            dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
            dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
            double mgLat = lat + dLat;
            double mgLon = lon + dLon;
            info.setChina(true);
            info.setLatitude(mgLat);
            info.setLongitude(mgLon);
        }
        return info;
    }

    public static LocateInfo gcj02_To_Wgs84(double lat, double lon) {
        LocateInfo info = new LocateInfo();
        LocateInfo gps = transform(lat, lon);
        double lontitude = lon * 2 - gps.getLongitude();
        double latitude = lat * 2 - gps.getLatitude();
        info.setChina(gps.isChina());
        info.setLatitude(latitude);
        info.setLongitude(lontitude);
        return info;
    }

    /**
     * @param gg_lon
     * @param gg_lat
     * @return
     * @Description 火星坐标系 (GCJ-02) to 百度坐标系 (BD-09)
     */
    public static LocateInfo gcj02_To_Bd09(double gg_lon, double gg_lat) {
        LocateInfo info = new LocateInfo();
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        info.setLongitude(bd_lon);
        info.setLatitude(bd_lat);
        //return new double[]{bd_lon, bd_lat};
        return info;
    }

    /**
     * @param bd_lon
     * @param bd_lat
     * @return
     * @Description 百度坐标系 (BD-09) to 火星坐标系 (GCJ-02)
     */
    public static LocateInfo bd09_To_Gcj02(double bd_lon, double bd_lat) {
        LocateInfo info = new LocateInfo();
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        info.setLatitude(gg_lat);
        info.setLongitude(gg_lon);
        //return new double[]{gg_lon, gg_lat};
        return info;
    }


    private static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    private static LocateInfo transform(double lat, double lon) {
        LocateInfo info = new LocateInfo();
        if (outOfChina(lat, lon)) {
            info.setChina(false);
            info.setLatitude(lat);
            info.setLongitude(lon);
            return info;
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        info.setChina(true);
        info.setLatitude(mgLat);
        info.setLongitude(mgLon);

        return info;
    }

    public static String gpsToWgs84(String dms, String type) {
        if (dms == null || dms.equals("")) {
            return "0.0";
        }
        double result = 0.0D;
        String temp = "";

        if (type.equals("E")) {//经度
            String e1 = dms.substring(0, 3);//截取3位数字，经度共3位，最多180度
            //经度是一伦敦为点作南北两极的线为0度,所有往西和往东各180度
            String e2 = dms.substring(3, dms.length());//需要运算的小数

            result = Double.parseDouble(e1);
/*            System.out.println("e2===="+e2);
            System.out.println("===="+Double.parseDouble(e2) / 60.0D);*/
            result += (Double.parseDouble(e2) / 60.0D);
            temp = String.valueOf(result);
            if (temp.length() > 11) {
                temp = e1 + temp.substring(temp.indexOf("."), 11);
            }
        } else if (type.equals("N")) {        //纬度，纬度是以赤道为基准,相当于把地球分两半,两个半球面上的点和平面夹角0~90度
            String n1 = dms.substring(0, 2);//截取2位，纬度共2位，最多90度
            String n2 = dms.substring(2, dms.length());

            result = Double.parseDouble(n1);
/*            System.out.println("n2===="+n2);
            System.out.println("===="+Double.parseDouble(n2) / 60.0D);*/
            result += Double.parseDouble(n2) / 60.0D;
            temp = String.valueOf(result);
            if (temp.length() > 10) {
                temp = n1 + temp.substring(temp.indexOf("."), 10);
            }
        }
        return temp;
    }
}
