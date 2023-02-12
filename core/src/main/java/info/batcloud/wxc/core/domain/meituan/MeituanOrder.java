package info.batcloud.wxc.core.domain.meituan;

import com.alibaba.fastjson.annotation.JSONField;

public class MeituanOrder {

    @JSONField(name = "order_id")
    private Long orderId;

    @JSONField(name = "wm_order_id_view")
    private long wmOrderIdView;

    @JSONField(name = "app_poi_code")
    private String appPoiCode;

    @JSONField(name = "wm_poi_name")
    private String wmPoiName;

    @JSONField(name = "wm_poi_address")
    private String wmPoiAddress;

    @JSONField(name = "wm_poi_phone")
    private String wmPoiPhone;

    @JSONField(name = "recipient_address")
    private String recipientAddress;

    @JSONField(name = "recipient_phone")
    private String recipientPhone;

    @JSONField(name = "recipient_name")
    private String recipientName;

    @JSONField(name = "shipping_fee")
    private float shippingFee;

    private float total;

    @JSONField(name = "original_price")
    private float originalPrice;

    private String caution;

    @JSONField(name = "shipper_phone")
    private String shipperPhone;
    private int status;
    private long ctime;
    private long utime;

    @JSONField(name = "delivery_time")
    private long deliveryTime;

    @JSONField(name = "is_third_shipping")
    private int isThirdShipping;

    @JSONField(name = "pay_type")
    private int payType;

    @JSONField(name = "day_seq")
    private int daySeq;

    @JSONField(name = "is_favorites")
    private boolean isFavorites;

    @JSONField(name = "is_poi_first_order")
    private boolean isPoiFirstOrder;

    @JSONField(name = "dinners_number")
    private int dinnersNumber;

    @JSONField(name = "package_bag_money")
    private int packageBagMoney;

    private String detail;

    public int getPackageBagMoney() {
        return packageBagMoney;
    }

    public void setPackageBagMoney(int packageBagMoney) {
        this.packageBagMoney = packageBagMoney;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public long getWmOrderIdView() {
        return wmOrderIdView;
    }

    public void setWmOrderIdView(long wmOrderIdView) {
        this.wmOrderIdView = wmOrderIdView;
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

    public float getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(float shippingFee) {
        this.shippingFee = shippingFee;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getIsThirdShipping() {
        return isThirdShipping;
    }

    public void setIsThirdShipping(int isThirdShipping) {
        this.isThirdShipping = isThirdShipping;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getDaySeq() {
        return daySeq;
    }

    public void setDaySeq(int daySeq) {
        this.daySeq = daySeq;
    }

    public boolean isFavorites() {
        return isFavorites;
    }

    public void setFavorites(boolean favorites) {
        isFavorites = favorites;
    }

    public boolean isPoiFirstOrder() {
        return isPoiFirstOrder;
    }

    public void setPoiFirstOrder(boolean poiFirstOrder) {
        isPoiFirstOrder = poiFirstOrder;
    }

    public int getDinnersNumber() {
        return dinnersNumber;
    }

    public void setDinnersNumber(int dinnersNumber) {
        this.dinnersNumber = dinnersNumber;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
