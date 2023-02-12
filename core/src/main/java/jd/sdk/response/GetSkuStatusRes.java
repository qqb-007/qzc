package jd.sdk.response;

import jd.sdk.Response;

import java.util.Date;

public class GetSkuStatusRes extends Response<GetSkuStatusRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private OpenStatusNResult result;

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

        public OpenStatusNResult getResult() {
            return result;
        }

        public void setResult(OpenStatusNResult result) {
            this.result = result;
        }
    }

    public static class OpenStatusNResult {
        private Long skuId;
        private Long outSkuId;
        private String resultCode;
        private String resultMsg;
        private Long operateTime;

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

        public Long getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(Long operateTime) {
            this.operateTime = operateTime;
        }
    }
}
