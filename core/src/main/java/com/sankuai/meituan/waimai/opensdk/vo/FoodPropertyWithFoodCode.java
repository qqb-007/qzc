package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by songxu on 2017/08/07
 */
public class FoodPropertyWithFoodCode {
    private String app_food_code;
    private List<FoodProperty> properties;

    public String getApp_food_code() {
        return app_food_code;
    }

    public FoodPropertyWithFoodCode setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
        return this;
    }

    public List<FoodProperty> getProperties() {
        return properties;
    }

    public FoodPropertyWithFoodCode setProperties(List<FoodProperty> properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public String toString() {
        return "FoodPropertyWithFoodCode [" +
                "app_food_code='" + app_food_code + '\'' +
                ", properties=" + properties +
                ']';
    }
}
