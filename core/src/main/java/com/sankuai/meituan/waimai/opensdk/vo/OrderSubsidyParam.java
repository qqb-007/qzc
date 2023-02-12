package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class OrderSubsidyParam {
    private Long order_id;
    private Float order_total;
    private Float subsidy;
    private List<OrderSubsidyExtraParam> extras;

    public Long getOrder_id() {
        return order_id;
    }

    public OrderSubsidyParam setOrder_id(Long order_id) {
        this.order_id = order_id;
        return this;
    }

    public Float getOrder_total() {
        return order_total;
    }

    public OrderSubsidyParam setOrder_total(Float order_total) {
        this.order_total = order_total;
        return this;
    }

    public Float getSubsidy() {
        return subsidy;
    }

    public OrderSubsidyParam setSubsidy(Float subsidy) {
        this.subsidy = subsidy;
        return this;
    }

    public List<OrderSubsidyExtraParam> getExtras() {
        return extras;
    }

    public OrderSubsidyParam setExtras(List<OrderSubsidyExtraParam> extras) {
        this.extras = extras;
        return this;
    }

    @Override
    public String toString() {
        return "OrderSubsidyParam [" +
                "order_id=" + order_id +
                ", order_total=" + order_total +
                ", subsidy=" + subsidy +
                ", extras=" + extras +
                ']';
    }
}
