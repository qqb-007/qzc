package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

public class GetActListParam {
    private Integer total_count;
    private List<ActListParam> act_list;

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public List<ActListParam> getAct_list() {
        return act_list;
    }

    public void setAct_list(List<ActListParam> act_list) {
        this.act_list = act_list;
    }

    @Override
    public String toString() {
        return "GetActListParam{" +
                "total_count=" + total_count +
                ", act_list=" + act_list +
                '}';
    }
}
