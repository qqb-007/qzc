package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;
import shunfeng.response.CreateOrderRes;

import java.util.List;

public class CreateOrderReq extends Request<CreateOrderRes> {
    @JSONField(name = "shop_id")
    private String shopId;
    @JSONField(name = "shop_order_id")
    private String shopOrderId;
    @JSONField(name = "order_source")
    private String orderSource;//1：美团；2：饿了么；3：百度；4：口碑；其他请直接填写中文字符串值
    @JSONField(name = "order_sequence")
    private String orderSequence;//订单流水号
    @JSONField(name = "pay_type")
    private int payType;//用户支付方式 1：已付款 0：货到付款
    @JSONField(name = "order_time")
    private Long orderTime;//用户下单时间
    @JSONField(name = "is_appoint")
    private int isAppoint;//0：非预约单；1：预约单
    @JSONField(name = "expect_time")
    private int expectTime;//预约单需必传,秒级时间戳
    @JSONField(name = "is_insured")
    private int isInsured;//是否保价，0：非保价；1：保价
    private String remark;//订单备注
    @JSONField(name = "return_flag")
    private int returnFlag;//全部返回为填入511
    private int version;//参照文档主版本号填写如：文档版本号1.7,version=17
    private Receive receive;
    @JSONField(name = "order_detail")
    private OrderDetail orderDetail;

    private Shop shop;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopOrderId() {
        return shopOrderId;
    }

    public void setShopOrderId(String shopOrderId) {
        this.shopOrderId = shopOrderId;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderSequence() {
        return orderSequence;
    }

    public void setOrderSequence(String orderSequence) {
        this.orderSequence = orderSequence;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public Long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Long orderTime) {
        this.orderTime = orderTime;
    }

    public int getIsAppoint() {
        return isAppoint;
    }

    public void setIsAppoint(int isAppoint) {
        this.isAppoint = isAppoint;
    }

    public int getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(int expectTime) {
        this.expectTime = expectTime;
    }

    public int getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(int isInsured) {
        this.isInsured = isInsured;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(int returnFlag) {
        this.returnFlag = returnFlag;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Receive getReceive() {
        return receive;
    }

    public void setReceive(Receive receive) {
        this.receive = receive;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public static class Shop {
        @JSONField(name = "shop_name")
        private String shopName;
        @JSONField(name = "shop_phone")
        private String shopPhone;
        @JSONField(name = "shop_address")
        private String shopAddress;
        @JSONField(name = "shop_lng")
        private String shopLng;
        @JSONField(name = "shop_lat")
        private String shopLat;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopPhone() {
            return shopPhone;
        }

        public void setShopPhone(String shopPhone) {
            this.shopPhone = shopPhone;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getShopLng() {
            return shopLng;
        }

        public void setShopLng(String shopLng) {
            this.shopLng = shopLng;
        }

        public String getShopLat() {
            return shopLat;
        }

        public void setShopLat(String shopLat) {
            this.shopLat = shopLat;
        }
    }

    public static class Receive {
        @JSONField(name = "user_name")
        private String userName;
        @JSONField(name = "user_phone")
        private String user_phone;
        @JSONField(name = "user_address")
        private String userAddress;
        @JSONField(name = "user_lng")
        private String userLng;
        @JSONField(name = "user_lat")
        private String userLat;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserLng() {
            return userLng;
        }

        public void setUserLng(String userLng) {
            this.userLng = userLng;
        }

        public String getUserLat() {
            return userLat;
        }

        public void setUserLat(String userLat) {
            this.userLat = userLat;
        }
    }

    public static class OrderDetail {
        @JSONField(name = "total_price")
        private int totalPrice;//用户订单总金额（单位：分
        @JSONField(name = "product_type")
        private int productType;//6
        @JSONField(name = "weight_gram")
        private int weightGram;//物品重量（单位：克）
        @JSONField(name = "product_num")
        private int productNum;//物品个数
        @JSONField(name = "product_type_num")
        private int productTypeNum;//物品种类个数
        @JSONField(name = "product_detail")
        private List<ProductDetail> productDetail;

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public int getWeightGram() {
            return weightGram;
        }

        public void setWeightGram(int weightGram) {
            this.weightGram = weightGram;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }

        public int getProductTypeNum() {
            return productTypeNum;
        }

        public void setProductTypeNum(int productTypeNum) {
            this.productTypeNum = productTypeNum;
        }

        public List<ProductDetail> getProductDetail() {
            return productDetail;
        }

        public void setProductDetail(List<ProductDetail> productDetail) {
            this.productDetail = productDetail;
        }
    }

    public static class ProductDetail {
        @JSONField(name = "product_name")
        private String productName;
        @JSONField(name = "product_num")
        private int productNum;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }
    }

    @Override
    public String getEndpoint() {
        return "createorder";
    }

    @Override
    public Class<CreateOrderRes> getResponseType() {
        return CreateOrderRes.class;
    }
}
