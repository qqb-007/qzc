package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.domain.OrderExtra;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.domain.meituan.RefundFood;
import info.batcloud.wxc.core.dto.OrderDTO;
import info.batcloud.wxc.core.dto.OrderDetailDTO;
import info.batcloud.wxc.core.dto.SupplierOrderDetailDTO;
import info.batcloud.wxc.core.dto.SupplierOrderDetailGroupDTO;
import info.batcloud.wxc.core.entity.Order;
import info.batcloud.wxc.core.enums.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface OrderService {

    void sycnStock(String platOrderId);

    boolean setOrderSelf(long id);

    boolean sycnOrderPiesong(long id);

    boolean updateDeliverySelf(long id, DeliveryStatus deliveryStatus);

    boolean callCourier(long id, DeliveryType deliveryType);

    void preparationMealComplete(Long orderId);

    void updateDeliveryState(Long id);

    boolean checkOrderDelivery(long id, Integer deliveryType);

    String downLoad();

    int refundNum(Long storeUserId);

    String getJddjFoodSku(String skuIdIsv);

    //给顺丰订单加小费
    boolean addShunFengOrderFee(double fee, Long id);

    boolean addCansun(float fee, Long id, CansunType type);

    void updateJddjOrderStatus(JdOrderInfo orderInfo);

    boolean sendWanteOrder(Long id);

    List<OrderDTO> fetchTop20ValidLatestOrder();

    boolean finishWanteOrder(Long id);

    List<OrderDTO> getWaitDeliveryOrder();

    String pullPhoneNumberByStoreId(long storeId);

    OrderSaveResult syncPlatOrder(Plat plat, String platOrderId, Boolean jd);

    void setPlatOrderStatus(Plat plat, String platOrderId, OrderStatus status);

    OrderSaveResult saveOrder(OrderParam param);

    void cancelOrderByPlat(Plat plat, String platOrderId, int cancelCode, String cancelReason);

    boolean sendDeliveryByOrderId(long orderId);

    boolean cancelDeliveryByOrderId(long orderId);

    void updateDeliveryCancelInfo(long orderId);

    DeliveryType updateOrderPeisongInfo(PeisongInfo peisongInfo);

    void updatePiesonInfoToPlat(PeisongInfo peisongInfo, DeliveryType otype);

    void confirmOrderByPlat(Plat plat, String platOrderId);

    boolean checkExistsByPlatAndPlatOrderId(Plat plat, String platOrderId);

    boolean checkOrder(long orderId);

    boolean checkOrder(Order order);

    Paging<OrderDTO> search(SearchParam param);

    boolean receiptOrder(long orderId);

    boolean cancelOrder(OrderCancelParam param);

    void refundOrder(Plat plat, String platOrderId, OrderRefundStatus status, String eleRefundId);

    void partRefund(OrderPartRefundParam param);

    void partRefunding(OrderPartRefundParam param);

    boolean agreeRefund(long orderId, String reason, String remarks, Double money);

    boolean rejectRefund(long orderId, String reason);

    List<OrderDetailDTO> findDetailByOrderId(long orderId);

    List<SupplierOrderDetailGroupDTO> groupSupplierDetailByOrderId(long orderId);

    List<SupplierOrderDetailDTO> findDetailByOrderIdAndFoodSupplierId(long orderId, long foodSupplierId);

    boolean printById(long id);

    boolean printCancelInfo(Plat plat, String platOrderId);

    OrderDTO toDTO(Order order, boolean fetchDetail);

    String export(ExportParam param) throws IOException;

    int countStoreUnFinishedOrder(List<Long> storeIdList, Date startTime, Date endTime);

    class SyncInfo {
        private String longitude;
        private String Latitude;
        private String psCode;
        private Float price;
        private String psOrderId;

        public String getPsOrderId() {
            return psOrderId;
        }

        public void setPsOrderId(String psOrderId) {
            this.psOrderId = psOrderId;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public String getPsCode() {
            return psCode;
        }

        public void setPsCode(String psCode) {
            this.psCode = psCode;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }
    }

    class JdOrderInfo {
        private String platOrderId;
        private String name;
        private String phone;
        private Boolean dadaTake;

        public String getPlatOrderId() {
            return platOrderId;
        }

        public void setPlatOrderId(String platOrderId) {
            this.platOrderId = platOrderId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Boolean getDadaTake() {
            return dadaTake;
        }

        public void setDadaTake(Boolean dadaTake) {
            this.dadaTake = dadaTake;
        }

        @Override
        public String toString() {
            return "JdOrderInfo{" +
                    "platOrderId='" + platOrderId + '\'' +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", dadaTake=" + dadaTake +
                    '}';
        }
    }

    class PeisongInfo {
        private DeliveryType type;
        private long deliveryId;
        private Plat plat;
        private String mtPeisongId;
        private String ddPeisongId;
        private String ptPeisongId;
        private String sfPeisongId;
        private String uuPeisongId;
        private String ssPeisongId;
        private String orderId;
        private DeliveryStatus status;
        private String courierName;
        private String courierPhone;
        private int cancelReasonId;
        private String cancelReason;
        private int exId;//异常id
        private String exContent;//异常详情
        private String expectTime;//新的期望送达时间	如果发生期望送达时间的更新此字段有值
        private String latitude;
        private String longitude;

        @Override
        public String toString() {
            return "PeisongInfo{" +
                    "type=" + type +
                    ", deliveryId=" + deliveryId +
                    ", plat=" + plat +
                    ", mtPeisongId='" + mtPeisongId + '\'' +
                    ", ddPeisongId='" + ddPeisongId + '\'' +
                    ", ptPeisongId='" + ptPeisongId + '\'' +
                    ", sfPeisongId='" + sfPeisongId + '\'' +
                    ", uuPeisongId='" + uuPeisongId + '\'' +
                    ", ssPeisongId='" + ssPeisongId + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", status=" + status +
                    ", courierName='" + courierName + '\'' +
                    ", courierPhone='" + courierPhone + '\'' +
                    ", cancelReasonId=" + cancelReasonId +
                    ", cancelReason='" + cancelReason + '\'' +
                    ", exId=" + exId +
                    ", exContent='" + exContent + '\'' +
                    ", expectTime='" + expectTime + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    '}';
        }

        public String getDdPeisongId() {
            return ddPeisongId;
        }

        public void setDdPeisongId(String ddPeisongId) {
            this.ddPeisongId = ddPeisongId;
        }

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }

        public String getPtPeisongId() {
            return ptPeisongId;
        }

        public void setPtPeisongId(String ptPeisongId) {
            this.ptPeisongId = ptPeisongId;
        }

        public long getDeliveryId() {
            return deliveryId;
        }

        public void setDeliveryId(long deliveryId) {
            this.deliveryId = deliveryId;
        }

        public String getMtPeisongId() {
            return mtPeisongId;
        }

        public void setMtPeisongId(String mtPeisongId) {
            this.mtPeisongId = mtPeisongId;
        }

        public String getSfPeisongId() {
            return sfPeisongId;
        }

        public void setSfPeisongId(String sfPeisongId) {
            this.sfPeisongId = sfPeisongId;
        }

        public String getUuPeisongId() {
            return uuPeisongId;
        }

        public void setUuPeisongId(String uuPeisongId) {
            this.uuPeisongId = uuPeisongId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public DeliveryStatus getStatus() {
            return status;
        }

        public void setStatus(DeliveryStatus status) {
            this.status = status;
        }

        public String getCourierName() {
            return courierName;
        }

        public void setCourierName(String courierName) {
            this.courierName = courierName;
        }

        public String getCourierPhone() {
            return courierPhone;
        }

        public void setCourierPhone(String courierPhone) {
            this.courierPhone = courierPhone;
        }

        public int getCancelReasonId() {
            return cancelReasonId;
        }

        public void setCancelReasonId(int cancelReasonId) {
            this.cancelReasonId = cancelReasonId;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(String cancelReason) {
            this.cancelReason = cancelReason;
        }

        public int getExId() {
            return exId;
        }

        public void setExId(int exId) {
            this.exId = exId;
        }

        public String getExContent() {
            return exContent;
        }

        public void setExContent(String exContent) {
            this.exContent = exContent;
        }

        public String getExpectTime() {
            return expectTime;
        }

        public void setExpectTime(String expectTime) {
            this.expectTime = expectTime;
        }

        public DeliveryType getType() {
            return type;
        }

        public void setType(DeliveryType type) {
            this.type = type;
        }

        public String getSsPeisongId() {
            return ssPeisongId;
        }

        public void setSsPeisongId(String ssPeisongId) {
            this.ssPeisongId = ssPeisongId;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }


    }

    class ExportParam {
        private List<Long> storeUserIdList;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startTime;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endTime;
        private OrderStatus status;
        private OrderBizStatus bizStatus;
        private Plat plat;
        private Integer type;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
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

        public OrderBizStatus getBizStatus() {
            return bizStatus;
        }

        public void setBizStatus(OrderBizStatus bizStatus) {
            this.bizStatus = bizStatus;
        }

        public List<Long> getStoreUserIdList() {
            return storeUserIdList;
        }

        public void setStoreUserIdList(List<Long> storeUserIdList) {
            this.storeUserIdList = storeUserIdList;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }
    }

    class OrderSaveResult extends Result {
        private long orderId;
        private Long storeUserId;
        private OrderStatus orderStatus;

        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }
    }

    class OrderPartRefundParam {
        private Plat plat;
        private String platOrderId;
        private Float refundMoney;
        private OrderRefundStatus status;
        private List<RefundFood> foodList;
        private String eleRefundId;

        public Float getRefundMoney() {
            return refundMoney;
        }

        public void setRefundMoney(Float refundMoney) {
            this.refundMoney = refundMoney;
        }

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }

        public String getPlatOrderId() {
            return platOrderId;
        }

        public void setPlatOrderId(String platOrderId) {
            this.platOrderId = platOrderId;
        }

        public OrderRefundStatus getStatus() {
            return status;
        }

        public void setStatus(OrderRefundStatus status) {
            this.status = status;
        }

        public List<RefundFood> getFoodList() {
            return foodList;
        }

        public void setFoodList(List<RefundFood> foodList) {
            this.foodList = foodList;
        }

        public String getEleRefundId() {
            return eleRefundId;
        }

        public void setEleRefundId(String eleRefundId) {
            this.eleRefundId = eleRefundId;
        }
    }

    class OrderCancelParam {
        private Long id;
        private String reason;
        private String reasonCode;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getReasonCode() {
            return reasonCode;
        }

        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }
    }

    class SearchParam extends PagingParam {
        private Boolean fentchStoreUser;
        private Boolean cansun;
        private Boolean opened;
        private String platOrderId;
        private Plat plat;
        private OrderStatus status;
        private Integer daySeq;
        private Long storeId;
        private Long storeUserId;
        private OrderBizStatus bizStatus;
        private String storeUserName;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endDate;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date date;
        private OrderStatusType statusType;
        private Long startExpectedDeliveryTime;
        private Long endExpectedDeliveryTime;
        private Long settlementSheetDetailId;
        private DeliveryType deliveryType;
        private DeliveryStatus deliveryStatus;
        private OrderRefundStatus refundStatus;

        private Long foodSupplierId;

        public Boolean getFentchStoreUser() {
            return fentchStoreUser;
        }

        public void setFentchStoreUser(Boolean fentchStoreUser) {
            this.fentchStoreUser = fentchStoreUser;
        }

        public Boolean getCansun() {
            return cansun;
        }

        public void setCansun(Boolean cansun) {
            this.cansun = cansun;
        }

        public OrderRefundStatus getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(OrderRefundStatus refundStatus) {
            this.refundStatus = refundStatus;
        }

        public Boolean getOpened() {
            return opened;
        }

        public void setOpened(Boolean opened) {
            this.opened = opened;
        }

        public Long getStartExpectedDeliveryTime() {
            return startExpectedDeliveryTime;
        }

        public void setStartExpectedDeliveryTime(Long startExpectedDeliveryTime) {
            this.startExpectedDeliveryTime = startExpectedDeliveryTime;
        }

        public Long getEndExpectedDeliveryTime() {
            return endExpectedDeliveryTime;
        }

        public void setEndExpectedDeliveryTime(Long endExpectedDeliveryTime) {
            this.endExpectedDeliveryTime = endExpectedDeliveryTime;
        }

        public Long getFoodSupplierId() {
            return foodSupplierId;
        }

        public void setFoodSupplierId(Long foodSupplierId) {
            this.foodSupplierId = foodSupplierId;
        }

        public DeliveryStatus getDeliveryStatus() {
            return deliveryStatus;
        }

        public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
        }

        public DeliveryType getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(DeliveryType deliveryType) {
            this.deliveryType = deliveryType;
        }

        public Long getSettlementSheetDetailId() {
            return settlementSheetDetailId;
        }

        public void setSettlementSheetDetailId(Long settlementSheetDetailId) {
            this.settlementSheetDetailId = settlementSheetDetailId;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public String getStoreUserName() {
            return storeUserName;
        }

        public void setStoreUserName(String storeUserName) {
            this.storeUserName = storeUserName;
        }

        public OrderBizStatus getBizStatus() {
            return bizStatus;
        }

        public void setBizStatus(OrderBizStatus bizStatus) {
            this.bizStatus = bizStatus;
        }

        private Boolean fetchDetail = false;

        public Boolean getFetchDetail() {
            return fetchDetail;
        }

        public void setFetchDetail(Boolean fetchDetail) {
            this.fetchDetail = fetchDetail;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public OrderStatusType getStatusType() {
            return statusType;
        }

        public void setStatusType(OrderStatusType statusType) {
            this.statusType = statusType;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Long getStoreId() {
            return storeId;
        }

        public void setStoreId(Long storeId) {
            this.storeId = storeId;
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

        public Integer getDaySeq() {
            return daySeq;
        }

        public void setDaySeq(Integer daySeq) {
            this.daySeq = daySeq;
        }

    }

    class OrderParam {
        private String platOrderId;

        private Plat plat;

        private String appPoiCode;

        private String wmOrderIdView;

        private String wmPoiName;

        private String wmPoiAddress;

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

        private Date createTime;

        private Date updateTime;

        private PayType payType;

        private PickType pickType;

        private Double lng;

        private Double lat;

        private Long deliveryTime;


        private Integer daySeq;

        private Integer packageBagMoney;

        private List<OrderExtra> extraList;

        private List<OrderDetailParam> detailParamList;

        private Long expectedDeliveryTime;

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

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Long getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Long deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public List<OrderExtra> getExtraList() {
            return extraList;
        }

        public void setExtraList(List<OrderExtra> extraList) {
            this.extraList = extraList;
        }

        public List<OrderDetailParam> getDetailParamList() {
            return detailParamList;
        }

        public void setDetailParamList(List<OrderDetailParam> detailParamList) {
            this.detailParamList = detailParamList;
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

        public String getAppPoiCode() {
            return appPoiCode;
        }

        public void setAppPoiCode(String appPoiCode) {
            this.appPoiCode = appPoiCode;
        }

        public String getWmOrderIdView() {
            return wmOrderIdView;
        }

        public void setWmOrderIdView(String wmOrderIdView) {
            this.wmOrderIdView = wmOrderIdView;
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
    }

    class OrderDetailParam {
        private String code;

        private String foodName;

        private String skuId;

        private Float quantity;

        private Float price;

        private Float boxNum;

        private Float boxPrice;

        private String unit;

        private String spec;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public Float getQuantity() {
            return quantity;
        }

        public void setQuantity(Float quantity) {
            this.quantity = quantity;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public Float getBoxNum() {
            return boxNum;
        }

        public void setBoxNum(Float boxNum) {
            this.boxNum = boxNum;
        }

        public Float getBoxPrice() {
            return boxPrice;
        }

        public void setBoxPrice(Float boxPrice) {
            this.boxPrice = boxPrice;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }

}
