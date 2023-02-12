package com.sankuai.meituan.waimai.opensdk.factory;

import com.sankuai.meituan.waimai.opensdk.api.*;

/**
 * Created by yangzhiqi on 15/10/15.
 * 接口工厂
 */
public class APIFactory {

    private static PoiAPI poiAPI = new PoiAPI();
    private static ShippingAPI shippingAPI = new ShippingAPI();
    private static FoodAPI foodAPI = new FoodAPI();
    private static MedicineAPI medicineAPI = new MedicineAPI();
    private static NewRetailApi newRetailApi = new NewRetailApi();
    private static RetailApi retailApi = new RetailApi();
    private static ActApi actApi = new ActApi();
    private static OrderAPI orderAPI = new OrderAPI();
    private static ImageApi imageApi = new ImageApi();
    private static MemberApi memberApi = new MemberApi();
    private static ActRetailApi actRetailApi = new ActRetailApi();

    public static ActRetailApi getActRetailApi(){return actRetailApi;}
    public static PoiAPI getPoiAPI() {
        return poiAPI;
    }
    public static MemberApi getMemberApi() {
        return memberApi;
    }

    public static ShippingAPI getShippingAPI() {
        return shippingAPI;
    }

    public static FoodAPI getFoodAPI() {
        return foodAPI;
    }

    public static MedicineAPI getMedicineAPI() {
        return medicineAPI;
    }

    public static NewRetailApi getNewRetailApi() {
        return newRetailApi;
    }

    public static RetailApi getRetailApi() {
        return retailApi;
    }

    public static ActApi getActApi() {
        return actApi;
    }

    public static OrderAPI getOrderAPI() {
        return orderAPI;
    }

    public static ImageApi getImageApi() {
        return imageApi;
    }
}
