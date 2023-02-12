package jd.sdk.response;

import jd.sdk.Response;

public class GetStoreInfoRes extends Response<GetStoreInfoRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private StoreInfo result;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public StoreInfo getResult() {
            return result;
        }

        public void setResult(StoreInfo result) {
            this.result = result;
        }
    }

    public static class StoreInfo {
        private Long id;
        private String venderId;//商家编码
        private String venderName;//商家名称
        private String stationName;//门店名称
        private String stationNo;//门店编码
        private String outSystemId;//商家门店编码
        private Double lat;
        private Double lng;
        private String coordinateAddress;//地址
        private String coordinate;
        private String mobile;
        private String stationAddress;//详细地址
        private Long createTime;
        private Byte yn;
        private Integer closeStatus;
        private Integer isAutoOrder;//是否自动接单

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getVenderId() {
            return venderId;
        }

        public void setVenderId(String venderId) {
            this.venderId = venderId;
        }

        public String getVenderName() {
            return venderName;
        }

        public void setVenderName(String venderName) {
            this.venderName = venderName;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getStationNo() {
            return stationNo;
        }

        public void setStationNo(String stationNo) {
            this.stationNo = stationNo;
        }

        public String getOutSystemId() {
            return outSystemId;
        }

        public void setOutSystemId(String outSystemId) {
            this.outSystemId = outSystemId;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public String getCoordinateAddress() {
            return coordinateAddress;
        }

        public void setCoordinateAddress(String coordinateAddress) {
            this.coordinateAddress = coordinateAddress;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStationAddress() {
            return stationAddress;
        }

        public void setStationAddress(String stationAddress) {
            this.stationAddress = stationAddress;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Byte getYn() {
            return yn;
        }

        public void setYn(Byte yn) {
            this.yn = yn;
        }

        public Integer getCloseStatus() {
            return closeStatus;
        }

        public void setCloseStatus(Integer closeStatus) {
            this.closeStatus = closeStatus;
        }

        public Integer getIsAutoOrder() {
            return isAutoOrder;
        }

        public void setIsAutoOrder(Integer isAutoOrder) {
            this.isAutoOrder = isAutoOrder;
        }
    }
}
