package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

import java.util.List;

public class ShopDeliveryinfoResponse extends Response<List<ShopDeliveryinfoResponse.Data>, String> {
    public static class Data {

        @JSONField(name = "product_id")
        private Long productId;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }
    }
}
