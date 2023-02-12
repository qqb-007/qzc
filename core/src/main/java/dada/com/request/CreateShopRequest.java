package dada.com.request;

import com.alibaba.fastjson.annotation.JSONField;
import dada.com.response.CreateShopResponse;

public class CreateShopRequest extends Request<CreateShopResponse> {

    @JSONField(name = "station_name")
    private String stationName;

    private Integer business;

    @JSONField(name = "station_address")
    private String stationAddress;

    private Double lng;

    private Double lat;

    @JSONField(name = "contact_name")
    private String contacName;

    private String phone;

    @JSONField(name = "origin_shop_id")
    private String originShopId;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getContacName() {
        return contacName;
    }

    public void setContacName(String contacName) {
        this.contacName = contacName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOriginShopId() {
        return originShopId;
    }

    public void setOriginShopId(String originShopId) {
        this.originShopId = originShopId;
    }

    @Override
    public String getUrl() {
        return "/api/shop/add";
    }

    @Override
    public Class<CreateShopResponse> getResponseClass() {
        return CreateShopResponse.class;
    }
}
