package wante.sdk.up.response;


import java.util.List;

public class OrderGetRes {
    private String message;//订单不存在的时候报错信息
    private Integer id;//订单id
    private Integer addressId;//收货地址id
    private String createTime;
    private String editTime;
    private String deliveryType;//EXPRESS, QUICK_DELIVERY
    private List<Delivery> delivery;//配送信息
    private Boolean enable;
    private Boolean first;//是否首单
    private Float firstDiscountMoney;
    private ReceivingAddress receivingAddress;//收货地址
    private String paymentTime;//付款时间
    private List<Refund> refund;//商品订单退款信息
    private String scenes;//付款方式ON_LINE, OFFLINE
    private String status;//订单状态COMPILE, WAIT_PAYMENT, TO_BE_RECEIVED, PAYMENT, TO_BE_EVALUATED, CANCEL
    private Integer storeId;
    private List<Good> goods;
    private String userNotes;//备注
    private Number purchasePrice;//实际支付金额
    private Number totalPrice;//原金额
    private Number freight;//配送费
    private String sameDayNumber;//当日订单流水号
    private String deliveryTimeCustom;//预订单期望送达时间

    public String getSameDayNumber() {
        return sameDayNumber;
    }

    public void setSameDayNumber(String sameDayNumber) {
        this.sameDayNumber = sameDayNumber;
    }

    public String getDeliveryTimeCustom() {
        return deliveryTimeCustom;
    }

    public void setDeliveryTimeCustom(String deliveryTimeCustom) {
        this.deliveryTimeCustom = deliveryTimeCustom;
    }

    public Number getFreight() {
        return freight;
    }

    public void setFreight(Number freight) {
        this.freight = freight;
    }

    public Number getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Number purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Number getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Number totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public List<Delivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<Delivery> delivery) {
        this.delivery = delivery;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Float getFirstDiscountMoney() {
        return firstDiscountMoney;
    }

    public void setFirstDiscountMoney(Float firstDiscountMoney) {
        this.firstDiscountMoney = firstDiscountMoney;
    }

    public ReceivingAddress getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(ReceivingAddress receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public List<Refund> getRefund() {
        return refund;
    }

    public void setRefund(List<Refund> refund) {
        this.refund = refund;
    }

    public String getScenes() {
        return scenes;
    }

    public void setScenes(String scenes) {
        this.scenes = scenes;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public static class Good {
        private Integer goodsId;
        private Float originalPrice;
        private Float price;
        private List<Specs> specs;
        private Integer number;

        public Integer getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(Integer goodsId) {
            this.goodsId = goodsId;
        }

        public Float getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Float originalPrice) {
            this.originalPrice = originalPrice;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public List<Specs> getSpecs() {
            return specs;
        }

        public void setSpecs(List<Specs> specs) {
            this.specs = specs;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }

    public static class Specs {
        private Integer id;
        private Integer idSpec;//规格id
        private String name;
        private String value;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getIdSpec() {
            return idSpec;
        }

        public void setIdSpec(Integer idSpec) {
            this.idSpec = idSpec;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ReceivingAddress {
        private String phone;
        private String fullAddressIntro;
        private String detailedAddress;
        private String name;
        private Integer id;
        private Coordinate coordinate;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFullAddressIntro() {
            return fullAddressIntro;
        }

        public void setFullAddressIntro(String fullAddressIntro) {
            this.fullAddressIntro = fullAddressIntro;
        }

        public String getDetailedAddress() {
            return detailedAddress;
        }

        public void setDetailedAddress(String detailedAddress) {
            this.detailedAddress = detailedAddress;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
        }
    }

    public static class Coordinate {
        private String createTime;
        private String editTime;
        private Double latitude;
        private Double longitude;
        private Integer id;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEditTime() {
            return editTime;
        }

        public void setEditTime(String editTime) {
            this.editTime = editTime;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public static class Refund {
        private String createTime;
        private String editTime;
        private Integer id;
        private Float money;
        private String orderStatus;//COMPILE, WAIT_PAYMENT, TO_BE_RECEIVED, PAYMENT, TO_BE_EVALUATED, CANCEL
        private String remarks;//退款备注
        private String status;//退款状态TO_EXAMINE, REJECT, SUCCESS

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEditTime() {
            return editTime;
        }

        public void setEditTime(String editTime) {
            this.editTime = editTime;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Float getMoney() {
            return money;
        }

        public void setMoney(Float money) {
            this.money = money;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Delivery {
        private Integer id;
        private String createTime;
        private String editTime;
        private Boolean enable;
        private String name;
        private String phone;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEditTime() {
            return editTime;
        }

        public void setEditTime(String editTime) {
            this.editTime = editTime;
        }

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
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
    }

}
