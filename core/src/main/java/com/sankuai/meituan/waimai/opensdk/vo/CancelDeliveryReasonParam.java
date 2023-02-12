package com.sankuai.meituan.waimai.opensdk.vo;

import java.util.List;

public class CancelDeliveryReasonParam {
    private String code;
    private String title;
    private String msg;
    private int deliveryStatus;
    private List<CancelReson> reasonList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public List<CancelReson> getReasonList() {
        return reasonList;
    }

    public void setReasonList(List<CancelReson> reasonList) {
        this.reasonList = reasonList;
    }

    @Override
    public String toString() {
        return "CancelDeliveryReasonParam{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                ", reasonList=" + reasonList +
                '}';
    }
}
