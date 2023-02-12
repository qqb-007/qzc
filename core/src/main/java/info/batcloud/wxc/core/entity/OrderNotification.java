package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.Plat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class OrderNotification {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String platOrderId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Plat plat;

    @NotNull
    private Boolean success;

    private String errMsg;

    private Date createTime;

    @NotNull
    private String data;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderNotificationType type;

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
