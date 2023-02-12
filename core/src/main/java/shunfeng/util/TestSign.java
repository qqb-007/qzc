package shunfeng.util;

import com.alibaba.fastjson.annotation.JSONField;

public class TestSign {
    @JSONField(name = "dev_id")
    private Long devId;
    @JSONField(name = "shop_id")
    private String shopId;
    @JSONField(name = "shop_order_id")
    private String shopOrderId;


    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
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
}
