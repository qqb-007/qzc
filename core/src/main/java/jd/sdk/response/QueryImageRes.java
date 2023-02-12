package jd.sdk.response;

import jd.sdk.Response;

import java.util.List;

public class QueryImageRes extends Response<QueryImageRes.Data> {
    public static class Data{
        private String code;
        private String msg;
        private List<ImgHandleQueryResult> result;

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

        public List<ImgHandleQueryResult> getResult() {
            return result;
        }

        public void setResult(List<ImgHandleQueryResult> result) {
            this.result = result;
        }
    }
    public static class ImgHandleQueryResult{
        private String id;
        private String skuId;
        private Integer handleStatus;
        private String handleStatusStr;
        private String handleRemark;
        private String handleErrLog;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public Integer getHandleStatus() {
            return handleStatus;
        }

        public void setHandleStatus(Integer handleStatus) {
            this.handleStatus = handleStatus;
        }

        public String getHandleStatusStr() {
            return handleStatusStr;
        }

        public void setHandleStatusStr(String handleStatusStr) {
            this.handleStatusStr = handleStatusStr;
        }

        public String getHandleRemark() {
            return handleRemark;
        }

        public void setHandleRemark(String handleRemark) {
            this.handleRemark = handleRemark;
        }

        public String getHandleErrLog() {
            return handleErrLog;
        }

        public void setHandleErrLog(String handleErrLog) {
            this.handleErrLog = handleErrLog;
        }
    }
}
