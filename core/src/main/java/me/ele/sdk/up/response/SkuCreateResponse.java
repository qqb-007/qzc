package me.ele.sdk.up.response;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;

public class SkuCreateResponse extends Response<SkuCreateResponse.Data, String> {

    public static class Data {
        @JSONField(name = "sku_id")
        private String skuId;

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }
    }

}
