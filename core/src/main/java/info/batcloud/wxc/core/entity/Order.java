package info.batcloud.wxc.core.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import info.batcloud.wxc.core.domain.OrderExtra;
import info.batcloud.wxc.core.enums.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Table(name = "wxc_order")
@Entity
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String platOrderId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Plat plat;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Store store;

    @NotNull
    private String appPoiCode;

    private String wmOrderIdView;

    private String wmPoiName;

    private String wmPoiAddress;

    private String wmPoiPhone;

    @NotNull
    private String recipientAddress;

    @NotNull
    private String recipientPhone;

    @NotNull
    private String recipientName;

    @NotNull
    private Float shippingFee;

    @NotNull
    private Float total;

    @NotNull
    private Float originalPrice;

    @NotNull
    private String caution;

    @NotNull
    private String shipperPhone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderBizStatus bizStatus;

    @Enumerated(EnumType.STRING)
    private OrderRefundStatus refundStatus;

    @Enumerated(EnumType.STRING)
    private CansunType cansunType;

    @Enumerated(EnumType.STRING)
    private OrderRefundType refundType;

    private String eleRefundId;

    @NotNull
    private Date createTime;

    @NotNull
    private Date updateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PayType payType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PickType pickType;

    @NotNull
    private Integer daySeq;

    private Integer packageBagMoney;

    private String ddPeisongId;

    private float refundMoney;

    private float cansun;

    private Boolean preparation;

    /**
     * 成本金额
     */
    @NotNull
    private float costTotal;

    /**
     * 成本退款金额
     */
    private float costRefund;

    @Version
    private Integer version;

    private String extraListJson;

    @Transient
    private List<OrderExtra> extraList;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private SettlementSheetDetail settlementSheetDetail;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderDetail> detailList;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    private Long deliveryTime;

    private Long expectedDeliveryTime;

    private Double lat;

    private Double lng;

    private Integer cancelReasonCode;
    private String cancelReason;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String mtPeisongId;
    private String sfPeisongId;
    private String uuPeisongId;
    private Long mtptId;

    private String ssPeisongId;

    private Long psDeliveryId;

    private String psCourierName;

    private String psCourierPhone;

    private Integer psCancelReasonId;

    private String psCancelReason;
    private Boolean dadaAccept;

    public String getDdPeisongId() {
        return ddPeisongId;
    }

    public void setDdPeisongId(String ddPeisongId) {
        this.ddPeisongId = ddPeisongId;
    }

    public Long getMtptId() {
        return mtptId;
    }

    public void setMtptId(Long mtptId) {
        this.mtptId = mtptId;
    }

    public String getSsPeisongId() {
        return ssPeisongId;
    }

    public void setSsPeisongId(String ssPeisongId) {
        this.ssPeisongId = ssPeisongId;
    }

    public String getUuPeisongId() {
        return uuPeisongId;
    }

    public void setUuPeisongId(String uuPeisongId) {
        this.uuPeisongId = uuPeisongId;
    }

    public Boolean getDadaAccept() {
        return dadaAccept == null ? false : dadaAccept;
    }

    public void setDadaAccept(Boolean dadaAccept) {
        this.dadaAccept = dadaAccept;
    }

    public String getSfPeisongId() {
        return sfPeisongId;
    }

    public void setSfPeisongId(String sfPeisongId) {
        this.sfPeisongId = sfPeisongId;
    }

    public Integer getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(Integer cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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

    public String getMtPeisongId() {
        return mtPeisongId;
    }

    public void setMtPeisongId(String mtPeisongId) {
        this.mtPeisongId = mtPeisongId;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Long getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Long expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public PickType getPickType() {
        return pickType;
    }

    public void setPickType(PickType pickType) {
        this.pickType = pickType;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public OrderBizStatus getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(OrderBizStatus bizStatus) {
        this.bizStatus = bizStatus;
    }

    public List<OrderExtra> getExtraList() {
        if (StringUtils.isEmpty(this.extraListJson)) {
            return Collections.EMPTY_LIST;
        }
        return extraList == null ? JSON.parseObject(this.extraListJson, new TypeReference<List<OrderExtra>>() {
        }) : extraList;
    }

    public void setExtraList(List<OrderExtra> extraList) {
        this.extraList = extraList;
        this.extraListJson = JSON.toJSONString(extraList);
    }

    public SettlementSheetDetail getSettlementSheetDetail() {
        return settlementSheetDetail;
    }

    public void setSettlementSheetDetail(SettlementSheetDetail settlementSheetDetail) {
        this.settlementSheetDetail = settlementSheetDetail;
    }

    public List<OrderDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OrderDetail> detailList) {
        this.detailList = detailList;
    }


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(float costTotal) {
        this.costTotal = costTotal;
    }

    public float getCostRefund() {
        return costRefund;
    }

    public void setCostRefund(float costRefund) {
        this.costRefund = costRefund;
    }

    public String getExtraListJson() {
        return extraListJson;
    }

    public void setExtraListJson(String extraListJson) {
        this.extraListJson = extraListJson;
    }

    public OrderRefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(OrderRefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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

    public OrderRefundType getRefundType() {
        return refundType;
    }

    public void setRefundType(OrderRefundType refundType) {
        this.refundType = refundType;
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

    public Boolean getPreparation() {
        return preparation;
    }

    public void setPreparation(Boolean preparation) {
        this.preparation = preparation;
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
