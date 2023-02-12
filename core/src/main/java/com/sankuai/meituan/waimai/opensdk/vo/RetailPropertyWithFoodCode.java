package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by wangshiyao on 2018/06/14.
 */
public class RetailPropertyWithFoodCode {
    private String app_food_code;
    private List<RetailProperty> properties;

    public String getApp_food_code() {
        return app_food_code;
    }

    public RetailPropertyWithFoodCode setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
        return this;
    }

    public List<RetailProperty> getProperties() {
        return properties;
    }

    public RetailPropertyWithFoodCode setProperties(List<RetailProperty> properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        return "RetailPropertyWithFoodCode{" +
                "app_food_code='" + app_food_code + '\'' +
                ", properties=" + properties +
                '}';
    }
}
