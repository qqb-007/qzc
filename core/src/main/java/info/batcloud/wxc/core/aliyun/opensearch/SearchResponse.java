package info.batcloud.wxc.core.aliyun.opensearch;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class SearchResponse<T> {

    private String status;
    @JSONField(name = "request_id")
    private String requestId;

    private Result<T> result;

    private List<Error> errors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Result<T> getResult() {
        return result;
    }

    public void setResult(Result<T> result) {
        this.result = result;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public static class Error {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class Result<T> {
        @JSONField(name = "searchtime")
        private float searchTime;
        private int total;
        private int num;
        @JSONField(name = "viewtotal")
        private int viewTotal;
        private List<T> items;

        public float getSearchTime() {
            return searchTime;
        }

        public void setSearchTime(float searchTime) {
            this.searchTime = searchTime;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getViewTotal() {
            return viewTotal;
        }

        public void setViewTotal(int viewTotal) {
            this.viewTotal = viewTotal;
        }

        public List<T> getItems() {
            return items;
        }

        public void setItems(List<T> items) {
            this.items = items;
        }
    }

}
