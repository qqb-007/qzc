package jd.sdk.response;

import jd.sdk.Response;

public class AddSkuRes extends Response<AddSkuRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private OpenPmsNSkuResult openPmsNSkuResult;

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

        public OpenPmsNSkuResult getOpenPmsNSkuResult() {
            return openPmsNSkuResult;
        }

        public void setOpenPmsNSkuResult(OpenPmsNSkuResult openPmsNSkuResult) {
            this.openPmsNSkuResult = openPmsNSkuResult;
        }
    }

    public static class OpenPmsNSkuResult {
        private Long skuId;
        private Long outSkuId;
        private String resultCode;
        private String resultMsg;

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public Long getOutSkuId() {
            return outSkuId;
        }

        public void setOutSkuId(Long outSkuId) {
            this.outSkuId = outSkuId;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
    }
}
