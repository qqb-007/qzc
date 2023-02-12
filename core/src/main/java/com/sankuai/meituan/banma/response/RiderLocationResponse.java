package com.sankuai.meituan.banma.response;

public class RiderLocationResponse extends AbstractResponse<RiderLocationResponse.Data> {

    public static class Data {
        private Long lat;
        private Long lng;

        public Long getLat() {
            return lat;
        }

        public void setLat(Long lat) {
            this.lat = lat;
        }

        public Long getLng() {
            return lng;
        }

        public void setLng(Long lng) {
            this.lng = lng;
        }
    }
}
