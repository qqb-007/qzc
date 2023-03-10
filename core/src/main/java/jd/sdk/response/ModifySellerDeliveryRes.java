package jd.sdk.response;

import jd.sdk.Response;

public class ModifySellerDeliveryRes extends Response<ModifySellerDeliveryRes.Data> {
    public static class Data{
        private String code;
        private String msg;
        private String detail;

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
}
