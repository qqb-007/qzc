package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;
import shunfeng.response.GetOrderAddFeeRes;

public class GetOrderAddFeeReq extends Request<GetOrderAddFeeRes> {
    @JSONField(name = "order_id")
    private String orderId;
    @JSONField(name = "order_type")
    private int orderType;
    @JSONField(name = "shop_id")
    private String shopId;
    @JSONField(name = "shop_type")
    private int shopType;

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

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    @Override
    public String getEndpoint() {
        return "getordergratuityfee";
    }

    @Override
    public Class<GetOrderAddFeeRes> getResponseType() {
        return GetOrderAddFeeRes.class;
    }
}
