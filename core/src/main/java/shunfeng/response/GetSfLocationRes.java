package shunfeng.response;

import com.alibaba.fastjson.annotation.JSONField;

public class GetSfLocationRes extends Response<GetSfLocationRes.Result> {
    public static class Result {
        @JSONField(name = "rider_lng")
        private String riderLng;

        @JSONField(name = "rider_lat")
        private String riderLat;//顺分价格单位是分

        public String getRiderLng() {
            return riderLng;
        }

        public void setRiderLng(String riderLng) {
            this.riderLng = riderLng;
        }

        public String getRiderLat() {
            return riderLat;
        }

        public void setRiderLat(String riderLat) {
            this.riderLat = riderLat;
        }
    }
}
