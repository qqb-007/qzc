package me.ele.sdk.up.response;

import me.ele.sdk.up.Response;

public class ShopStatusResponse extends Response<ShopStatusResponse.data,String> {
    public static class data{
        private String shop_id;
        private String baidu_shop_id;
        private String shop_busstatus;

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getBaidu_shop_id() {
            return baidu_shop_id;
        }

        public void setBaidu_shop_id(String baidu_shop_id) {
            this.baidu_shop_id = baidu_shop_id;
        }

        public String getShop_busstatus() {
            return shop_busstatus;
        }

        public void setShop_busstatus(String shop_busstatus) {
            this.shop_busstatus = shop_busstatus;
        }
    }
}
