package dada.com.response;

import java.util.List;

public class CityCodeResponse extends Response<List<CityCodeResponse.Result>> {
    public static class Result {
        private String cityName;
        private String cityCode;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "cityName='" + cityName + '\'' +
                    ", cityCode='" + cityCode + '\'' +
                    '}';
        }
    }
}
