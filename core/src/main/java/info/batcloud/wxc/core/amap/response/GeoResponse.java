package info.batcloud.wxc.core.amap.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class GeoResponse {

    private int status;
    private String info;
    private int infocode;
    private int count;
    private List<GeoCode> geocodes;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getInfocode() {
        return infocode;
    }

    public void setInfocode(int infocode) {
        this.infocode = infocode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<GeoCode> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<GeoCode> geocodes) {
        this.geocodes = geocodes;
    }

    public static class GeoCode {
        @JSONField(name = "formatted_address")
        private String formattedAddress;
        private String country;
        private String province;
        @JSONField(name = "citycode")
        private String cityCode;
        private String city;
        private String district;
        private String location;

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
