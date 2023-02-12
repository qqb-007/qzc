package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class AvailableTimeParam {
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

    public AvailableTimeParam(String time){
        this.monday = time;
        this.tuesday = time;
        this.wednesday = time;
        this.thursday = time;
        this.friday = time;
        this.saturday = time;
        this.sunday = time;
    }

    public void reset(String time){
        this.monday = time;
        this.tuesday = time;
        this.wednesday = time;
        this.thursday = time;
        this.friday = time;
        this.saturday = time;
        this.sunday = time;
    }

    public AvailableTimeParam(){
    }

    public AvailableTimeParam(String monday, String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public AvailableTimeParam setMonday(String monday) {
        this.monday = monday;
        return this;
    }

    public String getTuesday() {
        return tuesday;
    }

    public AvailableTimeParam setTuesday(String tuesday) {
        this.tuesday = tuesday;
        return this;
    }

    public String getWednesday() {
        return wednesday;
    }

    public AvailableTimeParam setWednesday(String wednesday) {
        this.wednesday = wednesday;
        return this;
    }

    public String getThursday() {
        return thursday;
    }

    public AvailableTimeParam setThursday(String thursday) {
        this.thursday = thursday;
        return this;
    }

    public String getFriday() {
        return friday;
    }

    public AvailableTimeParam setFriday(String friday) {
        this.friday = friday;
        return this;
    }

    public String getSaturday() {
        return saturday;
    }

    public AvailableTimeParam setSaturday(String saturday) {
        this.saturday = saturday;
        return this;
    }

    public String getSunday() {
        return sunday;
    }

    public AvailableTimeParam setSunday(String sunday) {
        this.sunday = sunday;
        return this;
    }

    @Override
    public String toString() {
        return "AvailableTimeParam [" +
                "monday='" + monday + '\'' +
                ", tuesday='" + tuesday + '\'' +
                ", wednesday='" + wednesday + '\'' +
                ", thursday='" + thursday + '\'' +
                ", friday='" + friday + '\'' +
                ", saturday='" + saturday + '\'' +
                ", sunday='" + sunday + '\'' +
                ']';
    }
}
