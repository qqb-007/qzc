package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;
import shunfeng.response.GetOrderStatusRes;

public class GetOrderStatusReq extends Request<GetOrderStatusRes> {
    @JSONField(name = "order_id")
    private String orderId;
    @JSONField(name = "order_type")
    private int orderType;
    @JSONField(name = "shop_id")
    private String shopId;
    @JSONField(name = "shop_type")
    private String shopType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    @Override
    public String getEndpoint() {
        return "listorderfeed";
    }

    @Override
    public Class<GetOrderStatusRes> getResponseType() {
        return GetOrderStatusRes.class;
    }
}
