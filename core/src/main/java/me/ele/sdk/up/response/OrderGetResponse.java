package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

import java.util.Date;
import java.util.List;

public class OrderGetResponse extends Response<OrderGetResponse.Data, String> {

    public static class Data {
        private String source;
        private Order order;

        private User user;
        private Shop shop;

        private List<List<Product>> products;

        public List<List<Product>> getProducts() {
            return products;
        }

        public void setProducts(List<List<Product>> products) {
            this.products = products;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Shop getShop() {
            return shop;
        }

        public void setShop(Shop shop) {
            this.shop = shop;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }
    }

    public static class Order {
        @JSONField(name = "order_id")
        private String orderId;
        @JSONField(name = "eleme_order_id")
        private String elemeOrderId;
        @JSONField(name = "order_from")
        private Integer orderFrom;
        @JSONField(name = "send_immediately")
        private Integer sendImmediately;
        @JSONField(name = "order_index")
        private Integer orderIndex;
        @JSONField(name = "is_cold_box_order")
        private Integer isColdBoxOrder;
        @JSONField(name = "is_private")
        private Integer isPrivate;
        @JSONField(name = "down_flag")
        private Integer downFlag;
        private int status;
        @JSONField(name = "order_flag")
        private int orderFlag;
        @JSONField(name = "expect_time_mode")
        private int expectTimeMode;
        @JSONField(name = "send_time")
        private String sendTime;
        @JSONField(name = "latest_send_time")
        private String latestSendTime;
        @JSONField(name = "pickup_time")
        private String pickupTime;
        @JSONField(name = "atshop_time")
        private String atshopTime;
        @JSONField(name = "delivery_time")
        private String deliveryTime;
        @JSONField(name = "delivery_phone")
        private String deliveryPhone;
        @JSONField(name = "finished_time")
        private String finishedTime;
        @JSONField(name = "confirm_time")
        private String confirmTime;
        @JSONField(name = "cancel_time")
        private String cancelTime;
        @JSONField(name = "send_fee")
        private int sendFee;
        @JSONField(name = "package_fee")
        private int packageFee;
        @JSONField(name = "discount_fee")
        private int discountFee;
        @JSONField(name = "shop_fee")
        private int shopFee;
        @JSONField(name = "total_fee")
        private int totalFee;
        @JSONField(name = "user_fee")
        private int userFee;
        @JSONField(name = "cold_box_fee")
        private int coldBoxFee;
        @JSONField(name = "pay_type")
        private int payType;
        @JSONField(name = "pay_status")
        private int payStatus;
        private String remark;
        @JSONField(name = "delivery_party")
        private int deliveryParty;
        @JSONField(name = "create_time")
        private long createTime;
        private Ext ext;





        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getElemeOrderId() {
            return elemeOrderId;
        }

        public void setElemeOrderId(String elemeOrderId) {
            this.elemeOrderId = elemeOrderId;
        }

        public Integer getOrderFrom() {
            return orderFrom;
        }

        public void setOrderFrom(Integer orderFrom) {
            this.orderFrom = orderFrom;
        }

        public Integer getSendImmediately() {
            return sendImmediately;
        }

        public void setSendImmediately(Integer sendImmediately) {
            this.sendImmediately = sendImmediately;
        }

        public Integer getOrderIndex() {
            return orderIndex;
        }

        public void setOrderIndex(Integer orderIndex) {
            this.orderIndex = orderIndex;
        }

        public Integer getIsColdBoxOrder() {
            return isColdBoxOrder;
        }

        public void setIsColdBoxOrder(Integer isColdBoxOrder) {
            this.isColdBoxOrder = isColdBoxOrder;
        }

        public Integer getIsPrivate() {
            return isPrivate;
        }

        public void setIsPrivate(Integer isPrivate) {
            this.isPrivate = isPrivate;
        }

        public Integer getDownFlag() {
            return downFlag;
        }

        public void setDownFlag(Integer downFlag) {
            this.downFlag = downFlag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOrderFlag() {
            return orderFlag;
        }

        public void setOrderFlag(int orderFlag) {
            this.orderFlag = orderFlag;
        }

        public int getExpectTimeMode() {
            return expectTimeMode;
        }

        public void setExpectTimeMode(int expectTimeMode) {
            this.expectTimeMode = expectTimeMode;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getLatestSendTime() {
            return latestSendTime;
        }

        public void setLatestSendTime(String latestSendTime) {
            this.latestSendTime = latestSendTime;
        }

        public String getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
        }

        public String getAtshopTime() {
            return atshopTime;
        }

        public void setAtshopTime(String atshopTime) {
            this.atshopTime = atshopTime;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getDeliveryPhone() {
            return deliveryPhone;
        }

        public void setDeliveryPhone(String deliveryPhone) {
            this.deliveryPhone = deliveryPhone;
        }

        public String getFinishedTime() {
            return finishedTime;
        }

        public void setFinishedTime(String finishedTime) {
            this.finishedTime = finishedTime;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public int getSendFee() {
            return sendFee;
        }

        public void setSendFee(int sendFee) {
            this.sendFee = sendFee;
        }

        public int getPackageFee() {
            return packageFee;
        }

        public void setPackageFee(int packageFee) {
            this.packageFee = packageFee;
        }

        public int getDiscountFee() {
            return discountFee;
        }

        public void setDiscountFee(int discountFee) {
            this.discountFee = discountFee;
        }

        public int getShopFee() {
            return shopFee;
        }

        public void setShopFee(int shopFee) {
            this.shopFee = shopFee;
        }

        public int getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(int totalFee) {
            this.totalFee = totalFee;
        }

        public int getUserFee() {
            return userFee;
        }

        public void setUserFee(int userFee) {
            this.userFee = userFee;
        }

        public int getColdBoxFee() {
            return coldBoxFee;
        }

        public void setColdBoxFee(int coldBoxFee) {
            this.coldBoxFee = coldBoxFee;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getDeliveryParty() {
            return deliveryParty;
        }

        public void setDeliveryParty(int deliveryParty) {
            this.deliveryParty = deliveryParty;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }
    }

    public static class Ext {
        @JSONField(name = "online_cancel_status")
        private Integer onlineCancelStatus;
        @JSONField(name = "part_refund_status")
        private Integer partRefundStatus;

        public Integer getOnlineCancelStatus() {
            return onlineCancelStatus;
        }

        public void setOnlineCancelStatus(Integer onlineCancelStatus) {
            this.onlineCancelStatus = onlineCancelStatus;
        }

        public Integer getPartRefundStatus() {
            return partRefundStatus;
        }

        public void setPartRefundStatus(Integer partRefundStatus) {
            this.partRefundStatus = partRefundStatus;
        }
    }

    public static class Product {
        @JSONField(name = "custom_sku_id")
        private String customSkuId;
        @JSONField(name = "product_name")
        private String productName;
        @JSONField(name = "product_amount")
        private int productAmount;
        @JSONField(name = "product_price")
        private int productPrice;
        @JSONField(name = "product_fee")
        private int productFee;
        @JSONField(name = "package_fee")
        private int packageFee;
        @JSONField(name = "package_amount")
        private int packageAmount;
        @JSONField(name = "total_fee")
        private int totalFee;
        private String upc;

        public String getUpc() {
            return upc;
        }

        public void setUpc(String upc) {
            this.upc = upc;
        }

        public String getCustomSkuId() {
            return customSkuId;
        }

        public void setCustomSkuId(String customSkuId) {
            this.customSkuId = customSkuId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductAmount() {
            return productAmount;
        }

        public void setProductAmount(int productAmount) {
            this.productAmount = productAmount;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public int getProductFee() {
            return productFee;
        }

        public void setProductFee(int productFee) {
            this.productFee = productFee;
        }

        public int getPackageFee() {
            return packageFee;
        }

        public void setPackageFee(int packageFee) {
            this.packageFee = packageFee;
        }

        public int getPackageAmount() {
            return packageAmount;
        }

        public void setPackageAmount(int packageAmount) {
            this.packageAmount = packageAmount;
        }

        public int getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(int totalFee) {
            this.totalFee = totalFee;
        }
    }

    public static class Shop {
        private String id;
        @JSONField(name = "baidu_shop_id")
        private String baiduShopId;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBaiduShopId() {
            return baiduShopId;
        }

        public void setBaiduShopId(String baiduShopId) {
            this.baiduShopId = baiduShopId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class User {
        @JSONField(name = "user_id")
        private String userId;
        private String name;
        private String province;
        private String city;
        private String district;
        private String phone;
        @JSONField(name = "privacy_phone")
        private String privacyPhone;
        private String gender;
        private String address;
        @JSONField(name = "coord_amap")
        private CoordAmap coordAmap;


        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPrivacyPhone() {
            return privacyPhone;
        }

        public void setPrivacyPhone(String privacyPhone) {
            this.privacyPhone = privacyPhone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public CoordAmap getCoordAmap() {
            return coordAmap;
        }

        public void setCoordAmap(CoordAmap coordAmap) {
            this.coordAmap = coordAmap;
        }
    }

    public static class CoordAmap {
        private String longitude;//送餐地址高德经度

        private String latitude;//送餐地址高德纬度

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }

}
