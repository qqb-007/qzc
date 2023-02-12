package com.sankuai.meituan.waimai.opensdk.util;

/**
 * Created by yangzhiqi on 15/11/10.
 */
public class StringUtil {

    public static boolean isBlank(Object o){
        if (o == null) return true;
        if ("".equals(trim(o.toString()))) return true;

        return false;
    }

    public static boolean isNotBlank(Object o){
        return o != null && !"".equals(o) && !"null".equals(o) && !"NULL".equals(o);
    }

    public static String trim(String s){
        String result = s.replaceAll(" +", "");
        return result;
    }

    public static void main(String[] args){
        trim("   ");
    }
}
