package jd.sdk.response;

import jd.sdk.Response;

public class DelCategoryRes extends Response<DelCategoryRes.Data> {
    public static class Data{
        private String code;
        private String msg;

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
    }
}
