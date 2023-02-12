package dada.com.request;

import com.alibaba.fastjson.annotation.JSONField;
import dada.com.response.AddOrderResponse;

import java.math.BigDecimal;

public class AddOrderRequest extends Request<AddOrderResponse> {

    @JSONField(name = "shop_no")
    private String shopNo;

    @JSONField(name = "origin_id")
    private String originId;

    @JSONField(name = "city_code")
    private String cityCode;

    @JSONField(name = "cargo_price")
    private Double cargoPrice;

    @JSONField(name = "is_prepay")
    private Integer isPrepay;

    @JSONField(name = "receiver_name")
    private String receiverName;

    @JSONField(name = "receiver_address")
    private String receiverAddress;

    @JSONField(name = "cargo_weight")
    private Double cargoWeight;

    @JSONField(name = "receiver_lat")
    private Double receiverLat;

    @JSONField(name = "receiver_lng")
    private Double receiverLng;

    private String callback;

    private String info;

    @JSONField(name = "receiver_phone")
    private String receiverPhone;

    @JSONField(name = "origin_mark_no")
    private String originMarkno;

    public String getOriginMarkno() {
        return originMarkno;
    }

    public void setOriginMarkno(String originMarkno) {
        this.originMarkno = originMarkno;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }



    public Integer getIsPrepay() {
        return isPrepay;
    }

    public void setIsPrepay(Integer isPrepay) {
        this.isPrepay = isPrepay;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }


    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public Double getCargoPrice() {
        return cargoPrice;
    }

    public void setCargoPrice(Double cargoPrice) {
        this.cargoPrice = cargoPrice;
    }

    public Double getReceiverLat() {
        return receiverLat;
    }

    public void setReceiverLat(Double receiverLat) {
        this.receiverLat = receiverLat;
    }

    public Double getReceiverLng() {
        return receiverLng;
    }

    public void setReceiverLng(Double receiverLng) {
        this.receiverLng = receiverLng;
    }

    public Double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(Double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    @Override
    public String toString() {
        return "AddOrderRequest{" +
                "shopNo='" + shopNo + '\'' +
                ", originId='" + originId + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cargoPrice=" + cargoPrice +
                ", isPrepay=" + isPrepay +
                ", receiverName='" + receiverName + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", cargoWeight=" + cargoWeight +
                ", receiverLat=" + receiverLat +
                ", receiverLng=" + receiverLng +
                ", callback='" + callback + '\'' +
                ", info='" + info + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", originMarkno='" + originMarkno + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/api/order/addOrder";
    }

    @Override
    public Class getResponseClass() {
        return AddOrderResponse.class;
    }
}
