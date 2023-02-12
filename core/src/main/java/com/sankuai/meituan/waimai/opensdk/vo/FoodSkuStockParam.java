package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class FoodSkuStockParam {
    private String app_food_code;
    private List<skuStockParam> skus;

    public String getApp_food_code() {
        return app_food_code;
    }

    public FoodSkuStockParam setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
        return this;
    }

    public List<skuStockParam> getSkus() {
        return skus;
    }

    public FoodSkuStockParam setSkus(List<skuStockParam> skus) {
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
