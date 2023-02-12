package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

import java.util.List;

public class ShopListResponse extends Response<List<ShopListResponse.Shop>, String> {

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
