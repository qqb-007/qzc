package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangyuanbo02 on 15/10/28.
 */
public class MedicineCatParam {
    private String app_poi_code;
    private String category_code;
    private String category_name;
    private String sequence;
    private String utime;
    private String ctime;
    private String standard_code;

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public MedicineCatParam setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
        return this;
    }

    public String getCategory_code() {
        return category_code;
    }

    public MedicineCatParam setCategory_code(String category_code) {
        this.category_code = category_code;
        return this;
    }

    public String getCategory_name() {
        return category_name;
    }

    public MedicineCatParam setCategory_name(String category_name) {
        this.category_name = category_name;
        return this;
    }

    public String getSequence() {
        return sequence;
    }

    public MedicineCatParam setSequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public String getUtime() {
        return utime;
    }

    public MedicineCatParam setUtime(String utime) {
        this.utime = utime;
        return this;
    }

    public String getCtime() {
        return ctime;
    }

    public MedicineCatParam setCtime(String ctime) {
        this.ctime = ctime;
        return this;
    }

    public String getStandard_code() {
        return standard_code;
    }

    public MedicineCatParam setStandard_code(String standard_code) {
        this.standard_code = standard_code;
        return this;
    }

    @Override
    public String toString() {
        return "MedicineCatParam [" +
                "app_poi_code='" + app_poi_code + '\'' +
                ", category_code='" + category_code + '\'' +
                ", category_name='" + category_name + '\'' +
                ", sequence='" + sequence + '\'' +
                ", utime='" + utime + '\'' +
                ", ctime='" + ctime + '\'' +
                ", standard_code='" + standard_code + '\'' +
                ']';
    }
}
