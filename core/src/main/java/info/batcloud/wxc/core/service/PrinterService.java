package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.enums.Plat;

import java.util.Date;
import java.util.List;

public interface PrinterService {

    boolean delPrinter(long storeUserId);

    boolean addPrinter(long storeUserId, String sn, String key);

    boolean testPrinter(String sn);

    boolean print(String sn, String content);

    boolean print(String sn, OrderInfo orderInfo);

    boolean print(String sn, CancelInfo cancelInfo);

    void queuePrint(String sn, OrderInfo orderInfo);

    class PrinterParams {
        private String sn;
        private OrderInfo orderInfo;
    }

    class CancelInfo{
        private String OrderId;
        private String Date;
        private Plat plat;
        private String phone;
        private String address;
        private String name;
        private String cancelReson;
        private String daySeq;
        private String shopName;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getOrderId() {
            return OrderId;
        }

        public void setOrderId(String orderId) {
            OrderId = orderId;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCancelReson() {
            return cancelReson;
        }

        public void setCancelReson(String cancelReson) {
            this.cancelReson = cancelReson;
        }

        public String getDaySeq() {
            return daySeq;
        }

        public void setDaySeq(String daySeq) {
            this.daySeq = daySeq;
        }
    }

    class OrderInfo {
        private String orderId;
        private int daySeq;
        private Plat plat;
        private Date createTime;
        private String storeName;
        private Date excepTime;
        private List<OrderDetailInfo> detailList;

        private String recipientAddress;
        private String recipientPhone;
        private String caution;
        private String recipientName;
        private List<String> giftName;
        private List<Integer> giftNum;
        private Long deliveryTime;

        public Long getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Long deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Date getExcepTime() {
            return excepTime;
        }

        public void setExcepTime(Date excepTime) {
            this.excepTime = excepTime;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public List<String> getGiftName() {
            return giftName;
        }

        public void setGiftName(List<String> giftName) {
            this.giftName = giftName;
        }

        public List<Integer> getGiftNum() {
            return giftNum;
        }

        public void setGiftNum(List<Integer> giftNum) {
            this.giftNum = giftNum;
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

        public String getCaution() {
            return caution;
        }

        public void setCaution(String caution) {
            this.caution = caution;
        }

        public String getRecipientName() {
            return recipientName;
        }

        public void setRecipientName(String recipientName) {
            this.recipientName = recipientName;
        }

        public int getDaySeq() {
            return daySeq;
        }

        public void setDaySeq(int daySeq) {
            this.daySeq = daySeq;
        }

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public List<OrderDetailInfo> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<OrderDetailInfo> detailList) {
            this.detailList = detailList;
        }
    }

    class OrderDetailInfo {
        private String foodName;
        private String foodSpec;
        private float quantity;

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getFoodSpec() {
            return foodSpec;
        }

        public void setFoodSpec(String foodSpec) {
            this.foodSpec = foodSpec;
        }

        public float getQuantity() {
            return quantity;
        }

        public void setQuantity(float quantity) {
            this.quantity = quantity;
        }
    }
}
