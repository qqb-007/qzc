package me.ele.sdk.up.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SkuOfflineResponse extends Response<String, String> {

    // {"failed_list":[{"error_no":1,"error_msg":"\u5546\u54c1\u4e0d\u5b58\u5728","custom_sku_id":"2380"}],"success_list":[]}

    public Error getErrorInfo() {
        if (StringUtils.isBlank(this.getError())) {
            return null;
        } else {
            return JSON.parseObject(this.getError(), Error.class);
        }
    }

    public static class Error {
        @JSONField(name = "failed_list")
        private List<ErrorItem> failedList;
        @JSONField(name = "success_list")
        private List<ErrorItem> successList;

        public List<ErrorItem> getFailedList() {
            return failedList;
        }

        public void setFailedList(List<ErrorItem> failedList) {
            this.failedList = failedList;
        }

        public List<ErrorItem> getSuccessList() {
            return successList;
        }

        public void setSuccessList(List<ErrorItem> successList) {
            this.successList = successList;
        }
    }

    public static class ErrorItem {
        @JSONField(name = "error_no")
        private Integer errorNo;

        @JSONField(name = "error_msg")
        private String errorMsg;

        public Integer getErrorNo() {
            return errorNo;
        }

        public void setErrorNo(Integer errorNo) {
            this.errorNo = errorNo;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }
}
