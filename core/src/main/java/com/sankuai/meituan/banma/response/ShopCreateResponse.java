package com.sankuai.meituan.banma.response;

/**
 * 创建订单响应类
 */
public class ShopCreateResponse extends AbstractResponse<ShopCreateResponse.Data> {

    public static class Data {
        private String shopId;
        private int status;

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
