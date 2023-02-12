package com.sankuai.meituan.waimai.opensdk.vo;

public class CancelReson {
    private int code;
    private String content;
    private int preCancelCode;
    private String preCancelMsg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPreCancelCode() {
        return preCancelCode;
    }

    public void setPreCancelCode(int preCancelCode) {
        this.preCancelCode = preCancelCode;
    }

    public String getPreCancelMsg() {
        return preCancelMsg;
    }

    public void setPreCancelMsg(String preCancelMsg) {
        this.preCancelMsg = preCancelMsg;
    }

    @Override
    public String toString() {
        return "CancelReson{" +
                "code=" + code +
                ", content='" + content + '\'' +
                ", preCancelCode=" + preCancelCode +
                ", preCancelMsg='" + preCancelMsg + '\'' +
                '}';
    }
}
