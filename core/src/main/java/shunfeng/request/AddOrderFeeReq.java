package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;
import shunfeng.response.AddOrderFeeRes;

public class AddOrderFeeReq extends Request<AddOrderFeeRes> {
    @JSONField(name = "order_id")
    private String orderId;
    @JSONField(name = "order_type")
    private int orderType;
    @JSONField(name = "shop_id")
    private String shopId;
    @JSONField(name = "shop_type")
    private int shopType;
    @JSONField(name = "gratuity_fee")
    private int gratuityFee;//订单小费，单位分，加小费最低不能少于100分

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

    public int getGratuityFee() {
        return gratuityFee;
    }

    public void setGratuityFee(int gratuityFee) {
        this.gratuityFee = gratuityFee;
    }

    @Override
    public String getEndpoint() {
        return "addordergratuityfee";
    }

    @Override
    public Class<AddOrderFeeRes> getResponseType() {
        return AddOrderFeeRes.class;
    }
}
