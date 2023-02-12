package jd.sdk.response;

import jd.sdk.Response;

public class OpenApproveRes extends Response<OpenApproveRes.Data> {
    public static class Data{
        private String code;
        private String msg;
        private Boolean success;

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

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }
    }
}
