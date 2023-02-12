package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.domain.OrderExtra;
import info.batcloud.wxc.core.enums.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class OrderDTO {

    private Long id;

    private String platOrderId;

    private Plat plat;

    private StoreDTO store;

    private String appPoiCode;

    private String wmOrderIdView;

    private String wmPoiName;

    private String wmPoiAddress;

    private String ddPeisongId;

    private String wmPoiPhone;

    private String recipientAddress;

    private String recipientPhone;

    private String recipientName;

    private Float shippingFee;

    private Float total;

    private Float originalPrice;

    private String caution;

    private String shipperPhone;

    private OrderStatus status;

    private OrderRefundType refundType;

    private OrderBizStatus bizStatus;

    private CansunType cansunType;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private PayType payType;

    private Integer daySeq;

    private Integer packageBagMoney;

    private OrderRefundStatus refundStatus;

    private Float refundMoney;

    /**
     * 成本金额
     */
    @NotNull
    private Float costTotal;

    /**
     * 成本退款金额
     */
    private Float costRefund;

    private float cansun;

    private Boolean settled;

    private DeliveryType deliveryType;

    private DeliveryStatus deliveryStatus;

    private String mtPeisongId;

    private String sfPeisongId;

    private String uuPeisongId;

    private String ssPeisongId;

    public String getUuPeisongId() {
        return uuPeisongId;
    }

    public void setUuPeisongId(String uuPeisongId) {
        this.uuPeisongId = uuPeisongId;
    }

    private Long psDeliveryId;

    private String psCourierName;

    private String psCourierPhone;

    private Integer psCancelReasonId;

    private String psCancelReason;

    private Long expectedDeliveryTime;

    private List<OrderExtra> extraList;

    private Long mtptId;

    private Long deliveryTime;

    private String eleRefundId;

    public String getDdPeisongId() {
        return ddPeisongId;
    }

    public void setDdPeisongId(String ddPeisongId) {
        this.ddPeisongId = ddPeisongId;
    }

    public List<OrderExtra> getExtraList() {
        return extraList;
    }

    public void setExtraList(List<OrderExtra> extraList) {
        this.extraList = extraList;
    }

    public Long getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Long expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSfPeisongId() {
        return sfPeisongId;
    }

    public void setSfPeisongId(String sfPeisongId) {
        this.sfPeisongId = sfPeisongId;
    }

    public String getMtPeisongId() {
        return mtPeisongId;
    }

    public void setMtPeisongId(String mtPeisongId) {
        this.mtPeisongId = mtPeisongId;
    }

    public Long getPsDeliveryId() {
        return psDeliveryId;
    }

    public void setPsDeliveryId(Long psDeliveryId) {
        this.psDeliveryId = psDeliveryId;
    }

    public String getPsCourierName() {
        return psCourierName;
    }

    public void setPsCourierName(String psCourierName) {
        this.psCourierName = psCourierName;
    }

    public String getPsCourierPhone() {
        return psCourierPhone;
    }

    public void setPsCourierPhone(String psCourierPhone) {
        this.psCourierPhone = psCourierPhone;
    }

    public Integer getPsCancelReasonId() {
        return psCancelReasonId;
    }

    public void setPsCancelReasonId(Integer psCancelReasonId) {
        this.psCancelReasonId = psCancelReasonId;
    }

    public String getPsCancelReason() {
        return psCancelReason;
    }

    public void setPsCancelReason(String psCancelReason) {
        this.psCancelReason = psCancelReason;
    }

    public String getDeliveryStatusTitle() {
        return getDeliveryStatus() == null ? null : deliveryStatus.getTitle();
    }

    public String getCansunTypeTitle() {
        return getCansunType() == null ? "无餐损" : cansunType.getTitle();
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryTypeTitle() {
        return deliveryType == null ? null : deliveryType.getTitle();
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    private List<OrderDetailDTO> orderDetailList;

    public OrderBizStatus getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(OrderBizStatus bizStatus) {
        this.bizStatus = bizStatus;
    }

    public OrderRefundType getRefundType() {
        return refundType;
    }

    public void setRefundType(OrderRefundType refundType) {
        this.refundType = refundType;
    }

    public List<OrderDetailDTO> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailDTO> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Boolean getSettled() {
        return settled;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

    public Float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Float costTotal) {
        this.costTotal = costTotal;
    }

    public Float getCostRefund() {
        return costRefund;
    }

    public void setCostRefund(Float costRefund) {
        this.costRefund = costRefund;
    }

    public OrderRefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(OrderRefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getPlatTitle() {
        return plat == null ? null : plat.getTitle();
    }

    public String getStatusTitle() {
        return status == null ? null : status.getTitle();
    }

    public String getBizStatusTitle() {
        return bizStatus == null ? null : bizStatus.getTitle();
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

    public String getWmOrderIdView() {
        return wmOrderIdView;
    }

    public void setWmOrderIdView(String wmOrderIdView) {
        this.wmOrderIdView = wmOrderIdView;
    }

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
    }

    public String getAppPoiCode() {
        return appPoiCode;
    }

    public void setAppPoiCode(String appPoiCode) {
        this.appPoiCode = appPoiCode;
    }


    public String getWmPoiName() {
        return wmPoiName;
    }

    public void setWmPoiName(String wmPoiName) {
        this.wmPoiName = wmPoiName;
    }

    public String getWmPoiAddress() {
        return wmPoiAddress;
    }

    public void setWmPoiAddress(String wmPoiAddress) {
        this.wmPoiAddress = wmPoiAddress;
    }

    public String getWmPoiPhone() {
        return wmPoiPhone;
    }

    public void setWmPoiPhone(String wmPoiPhone) {
        this.wmPoiPhone = wmPoiPhone;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Float getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Float shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
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

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Integer getDaySeq() {
        return daySeq;
    }

    public void setDaySeq(Integer daySeq) {
        this.daySeq = daySeq;
    }

    public Integer getPackageBagMoney() {
        return packageBagMoney;
    }

    public void setPackageBagMoney(Integer packageBagMoney) {
        this.packageBagMoney = packageBagMoney;
    }

    public String getEleRefundId() {
        return eleRefundId;
    }

    public void setEleRefundId(String eleRefundId) {
        this.eleRefundId = eleRefundId;
    }

    public String getSsPeisongId() {
        return ssPeisongId;
    }

    public void setSsPeisongId(String ssPeisongId) {
        this.ssPeisongId = ssPeisongId;
    }

    public Long getMtptId() {
        return mtptId;
    }

    public void setMtptId(Long mtptId) {
        this.mtptId = mtptId;
    }

    public float getCansun() {
        return cansun;
    }

    public void setCansun(float cansun) {
        this.cansun = cansun;
    }

    public CansunType getCansunType() {
        return cansunType;
    }

    public void setCansunType(CansunType cansunType) {
        this.cansunType = cansunType;
    }
}
