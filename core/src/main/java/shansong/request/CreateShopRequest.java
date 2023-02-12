package shansong.request;

import shansong.response.CreateShopResponse;

public class CreateShopRequest extends Request<CreateShopResponse> {


    private String storeName;
    private String cityName;
    private String address;
    private String addressDetail;
    private String latitude;
    private String longitude;
    private String phone;
    private String goodType;
    private String operationType;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }


    @Override
    public String toString() {
        return "CreateShopRequest{" +
                "storeName='" + storeName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", phone='" + phone + '\'' +
                ", goodType='" + goodType + '\'' +
                ", operationType='" + operationType + '\'' +
                '}';
    }

    @Override
    public String getUrl() {
        return "/openapi/developer/v5/storeOperation";
    }

    @Override
    public Class<CreateShopResponse> getResponseClass() {
        return CreateShopResponse.class;
    }
}
