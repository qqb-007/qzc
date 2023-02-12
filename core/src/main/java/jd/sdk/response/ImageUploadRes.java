package jd.sdk.response;

import jd.sdk.Response;

public class ImageUploadRes extends Response<ImageUploadRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private String imageUrl;

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

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
