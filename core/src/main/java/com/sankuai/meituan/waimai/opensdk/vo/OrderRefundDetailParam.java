package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

public class OrderRefundDetailParam {

    private long wm_order_id_view;
    private long order_id;
    private int ctime;
    private int utime;
    private int refund_type;
    private String res_reason;
    private int res_type;
    private int apply_type;
    private String apply_reason;
    private Double money;

    private List<OrderPartRefundParam> wmAppRetailForOrderPartRefundList;

    public long getWm_order_id_view() {
        return wm_order_id_view;
    }

    public void setWm_order_id_view(long wm_order_id_view) {
        this.wm_order_id_view = wm_order_id_view;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    public int getUtime() {
        return utime;
    }

    public void setUtime(int utime) {
        this.utime = utime;
    }

    public int getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(int refund_type) {
        this.refund_type = refund_type;
    }

    public String getRes_reason() {
        return res_reason;
    }

    public void setRes_reason(String res_reason) {
        this.res_reason = res_reason;
    }

    public int getRes_type() {
        return res_type;
    }

    public void setRes_type(int res_type) {
        this.res_type = res_type;
    }

    public int getApply_type() {
        return apply_type;
    }

    public void setApply_type(int apply_type) {
        this.apply_type = apply_type;
    }

    public String getApply_reason() {
        return apply_reason;
    }

    public void setApply_reason(String apply_reason) {
        this.apply_reason = apply_reason;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<OrderPartRefundParam> getWmAppRetailForOrderPartRefundList() {
        return wmAppRetailForOrderPartRefundList;
    }

    public void setWmAppRetailForOrderPartRefundList(List<OrderPartRefundParam> wmAppRetailForOrderPartRefundList) {
        this.wmAppRetailForOrderPartRefundList = wmAppRetailForOrderPartRefundList;
    }
}
