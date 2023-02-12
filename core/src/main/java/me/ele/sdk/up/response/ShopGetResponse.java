package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

public class ShopGetResponse extends Response<ShopGetResponse.Shop, String> {

    public static class Shop {
        @JSONField(name = "shop_id")
        private String shopId;
        @JSONField(name = "baidu_shop_id")
        private String baiduShopId;
        @JSONField(name = "name")
        private String name;
        @JSONField(name = "status")
        private Integer status;
        @JSONField(name = "sys_status")
        private Integer sysStatus;
        @JSONField(name = "order_push")
        private Integer orderPush;
        @JSONField(name = "order_status_push")
        private Integer orderStatusPush;

        @JSONField(name = "shop_logo")
        private String shopLogo;

        private String province;

        private String city;

        private String county;

        private String address;

        @JSONField(name = "service_phone")
        private String servicePhone;

        public String getShopLogo() {
            return shopLogo;
        }

        public void setShopLogo(String shopLogo) {
            this.shopLogo = shopLogo;
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

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getServicePhone() {
            return servicePhone;
        }

        public void setServicePhone(String servicePhone) {
            this.servicePhone = servicePhone;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getSysStatus() {
            return sysStatus;
        }

        public void setSysStatus(Integer sysStatus) {
            this.sysStatus = sysStatus;
        }

        public Integer getOrderPush() {
            return orderPush;
        }

        public void setOrderPush(Integer orderPush) {
            this.orderPush = orderPush;
        }

        public Integer getOrderStatusPush() {
            return orderStatusPush;
        }

        public void setOrderStatusPush(Integer orderStatusPush) {
            this.orderStatusPush = orderStatusPush;
        }
    }

}
