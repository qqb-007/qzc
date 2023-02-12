package com.sankuai.meituan.waimai.opensdk.vo;

public class AuditStatusParam {
    private String app_spu_code;
    private String name;

    private Integer is_sold_out;
    private Integer audit_status;
    private Long tag_id;

    public String getApp_spu_code() {
        return app_spu_code;
    }

    public void setApp_spu_code(String app_spu_code) {
        this.app_spu_code = app_spu_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIs_sold_out() {
        return is_sold_out;
    }

    public void setIs_sold_out(Integer is_sold_out) {
        this.is_sold_out = is_sold_out;
    }

    public Integer getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(Integer audit_status) {
        this.audit_status = audit_status;
    }

    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }


    @Override
    public String toString() {
        return "AuditStatusParam{" +
                "app_spu_code='" + app_spu_code + '\'' +
                ", name='" + name + '\'' +
                ", is_sold_out=" + is_sold_out +
                ", audit_status=" + audit_status +
                ", tag_id=" + tag_id +
                '}';
    }
}
