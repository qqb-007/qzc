package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class FoodCatParam {
    private String app_poi_code;
    private String name;
    private Integer sequence;
    private Long ctime;
    private Long utime;

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public FoodCatParam setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
        return this;
    }

    public String getName() {
        return name;
    }

    public FoodCatParam setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public FoodCatParam setSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Long getCtime() {
        return ctime;
    }

    public FoodCatParam setCtime(Long ctime) {
        this.ctime = ctime;
        return this;
    }

    public Long getUtime() {
        return utime;
    }

    public FoodCatParam setUtime(Long utime) {
        this.utime = utime;
        return this;
    }

    @Override
    public String toString() {
        return "FoodCatParam [" +
                "app_poi_code='" + app_poi_code + '\'' +
                ", name='" + name + '\'' +
                ", sequence=" + sequence +
                ", ctime=" + ctime +
                ", utime=" + utime +
                ']';
    }
}
