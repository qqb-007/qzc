package jd.sdk.response;

import jd.sdk.Response;

public class AddCategoryRes extends Response<AddCategoryRes.Data> {
    public static class Data {
        private ShopCategoryPartnerResponse result;
        private String code;
        private String msg;
        private String detail;

        public ShopCategoryPartnerResponse getResult() {
            return result;
        }

        public void setResult(ShopCategoryPartnerResponse result) {
            this.result = result;
        }

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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

    public static class ShopCategoryPartnerResponse {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
