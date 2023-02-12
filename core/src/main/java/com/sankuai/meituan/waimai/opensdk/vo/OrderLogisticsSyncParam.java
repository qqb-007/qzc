package com.sankuai.meituan.waimai.opensdk.vo;

public class OrderLogisticsSyncParam {

    private String order_id;
    private String third_carrier_order_id;
    private String courier_name;
    private String courier_phone;
    private String logistics_provider_code;
    private Integer logistics_status;
    private String latitude;
    private String longitude;

    public String getThird_carrier_order_id() {
        return third_carrier_order_id;
    }

    public void setThird_carrier_order_id(String third_carrier_order_id) {
        this.third_carrier_order_id = third_carrier_order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }

    public String getCourier_phone() {
        return courier_phone;
    }

    public void setCourier_phone(String courier_phone) {
        this.courier_phone = courier_phone;
    }

    public String getLogistics_provider_code() {
        return logistics_provider_code;
    }

    public void setLogistics_provider_code(String logistics_provider_code) {
        this.logistics_provider_code = logistics_provider_code;
    }

    public Integer getLogistics_status() {
        return logistics_status;
    }

    public void setLogistics_status(Integer logistics_status) {
        this.logistics_status = logistics_status;
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
}
