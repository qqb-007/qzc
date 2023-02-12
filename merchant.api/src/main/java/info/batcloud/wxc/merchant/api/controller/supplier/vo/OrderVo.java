package info.batcloud.wxc.merchant.api.controller.supplier.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.*;

import java.util.Date;

/**
 * @author lvling
 * Date: 2020/2/3
 * Time: 13:08
 */
public class OrderVo {

    private Long id;

    private String platOrderId;

    private Plat plat;

    private OrderStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Integer daySeq;

    private OrderRefundStatus refundStatus;

    private Float totalMoneyForSupplier;

    private Float refundMoneyForSupplier;

    private Float remainMoneyForSupplier;

    public Float getRemainMoneyForSupplier() {
        return remainMoneyForSupplier;
    }

    public void setRemainMoneyForSupplier(Float remainMoneyForSupplier) {
        this.remainMoneyForSupplier = remainMoneyForSupplier;
    }

    public String getPlatTitle() {
        return plat == null ? null : plat.getTitle();
    }

    public String getStatusTitle() {
        return status == null ? null : status.getTitle();
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDaySeq() {
        return daySeq;
    }

    public void setDaySeq(Integer daySeq) {
        this.daySeq = daySeq;
    }

    public OrderRefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(OrderRefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Float getTotalMoneyForSupplier() {
        return totalMoneyForSupplier;
    }

    public void setTotalMoneyForSupplier(Float totalMoneyForSupplier) {
        this.totalMoneyForSupplier = totalMoneyForSupplier;
    }

    public Float getRefundMoneyForSupplier() {
        return refundMoneyForSupplier;
    }

    public void setRefundMoneyForSupplier(Float refundMoneyForSupplier) {
        this.refundMoneyForSupplier = refundMoneyForSupplier;
    }
}
