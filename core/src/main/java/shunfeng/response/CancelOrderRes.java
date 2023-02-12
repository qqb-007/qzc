package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

public class CancelOrderRes extends Response<CancelOrderRes.Result> {
    public static class Result {
        @JSONField(name = "sf_order_id")
        private String sfOrderId;
        @JSONField(name = "shop_order_id")
        private String shopOrderId;

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
    }
}
