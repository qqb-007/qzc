package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by wangshiyao on 2018/06/14.
 */
public class RetailSkuStockParam {
    private String app_food_code;
    private List<skuStockParam> skus;

    public String getApp_food_code() {
        return app_food_code;
    }

    public RetailSkuStockParam setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
        return this;
    }

    public List<skuStockParam> getSkus() {
        return skus;
    }

    public RetailSkuStockParam setSkus(List<skuStockParam> skus) {
        this.skus = skus;
        return this;
    }

    @Override
    public String toString() {
        return "FoodSkuStockParam [" +
                "app_food_code='" + app_food_code + '\'' +
                ", skus=" + skus +
                ']';
    }
}
