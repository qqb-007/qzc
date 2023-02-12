package shunfeng.request;

import com.alibaba.fastjson.annotation.JSONField;
import shunfeng.response.GetSfPriceRes;

public class GetSfPriceReq extends Request<GetSfPriceRes> {

    @JSONField(name = "shop_id")
    private String shopId;

    @JSONField(name = "user_lng")
    private String userLng;

    @JSONField(name = "user_lat")
    private String userlat;

    @JSONField(name = "user_address")
    private String userAddress;

    private Integer weight;

    @JSONField(name = "product_type")
    private Integer productType;

    @JSONField(name = "return_flag")
    private Integer returnFlag;


    public Integer getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(Integer returnFlag) {
        this.returnFlag = returnFlag;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserLng() {
        return userLng;
    }

    public void setUserLng(String userLng) {
        this.userLng = userLng;
    }

    public String getUserlat() {
        return userlat;
    }

    public void setUserlat(String userlat) {
        this.userlat = userlat;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "GetSfPriceReq{" +
                "shopId='" + shopId + '\'' +
                ", userLng='" + userLng + '\'' +
                ", userlat='" + userlat + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", weight=" + weight +
                ", productType=" + productType +
                '}';
    }

    @Override
    public String getEndpoint() {
        return "precreateorder";
    }

    @Override
    public Class<GetSfPriceRes> getResponseType() {
        return GetSfPriceRes.class;
    }
}
