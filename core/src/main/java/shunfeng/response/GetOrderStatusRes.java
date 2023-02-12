package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class GetOrderStatusRes extends Response<GetOrderStatusRes.Result> {
    public static class Result {
        @JSONField(name = "sf_order_id")
        private String sfOrderId;
        @JSONField(name = "shop_order_id")
        private String shopOrderId;
        @JSONField(name = "create_time")
        private Long createTime;
        @JSONField(name = "push_time")
        private Long pushTime;
        private List<Feed> feed;

        public String getSfOrderId() {
            return sfOrderId;
        }

        public void setSfOrderId(String sfOrderId) {
            this.sfOrderId = sfOrderId;
        }

        public String getShopOrderId() {
            return shopOrderId;
        }

        public void setShopOrderId(String shopOrderId) {
            this.shopOrderId = shopOrderId;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Long getPushTime() {
            return pushTime;
        }

        public void setPushTime(Long pushTime) {
            this.pushTime = pushTime;
        }

        public List<Feed> getFeed() {
            return feed;
        }

        public void setFeed(List<Feed> feed) {
            this.feed = feed;
        }
    }

    public static class Feed {
        @JSONField(name = "sf_order_id")
        private String sfOrderId;
        @JSONField(name = "shop_order_id")
        private String shopOrderId;
        @JSONField(name = "order_status")
        private int orderStatus;

        public String getSfOrderId() {
            return sfOrderId;
        }

        public void setSfOrderId(String sfOrderId) {
            this.sfOrderId = sfOrderId;
        }

        public String getShopOrderId() {
            return shopOrderId;
        }

        public void setShopOrderId(String shopOrderId) {
            this.shopOrderId = shopOrderId;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }
    }
}
