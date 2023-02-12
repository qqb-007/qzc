package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by songxu on 2017/08/07
 */
public class FoodProperty {
    private String property_name;
    private List<String> values;

    public String getProperty_name() {
        return property_name;
    }

    public FoodProperty setProperty_name(String property_name) {
        this.property_name = property_name;
        return this;
    }

    public List<String> getValues() {
        return values;
    }

    public FoodProperty setValues(List<String> values) {
        this.values = values;
        return this;
    }

    @Override
    public String toString() {
        return "FoodProperty [" +
                "property_name='" + property_name + '\'' +
                ", values=" + values +
                ']';
    }
}
