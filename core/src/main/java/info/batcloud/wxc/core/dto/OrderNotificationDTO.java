package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.Plat;

import java.util.Date;

public class OrderNotificationDTO {

    private Long id;

    private String platOrderId;

    private Plat plat;

    private Boolean success;

    private String errMsg;

    private String data;

    private OrderNotificationType type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getTypeTitle() {
        return type == null ? null : type.getTitle();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public OrderNotificationType getType() {
        return type;
    }

    public void setType(OrderNotificationType type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPlatTitle() {
        return plat == null ? null : plat.getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatOrderId() {
        return platOrderId;
    }

    public void setPlatOrderId(String platOrderId) {
        this.platOrderId = platOrderId;
    }

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
