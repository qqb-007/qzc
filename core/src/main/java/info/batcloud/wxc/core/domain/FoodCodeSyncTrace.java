package info.batcloud.wxc.core.domain;

import java.util.List;

public class FoodCodeSyncTrace {

    public List<StoreTrace> storeTraceList;

    public List<StoreTrace> getStoreTraceList() {
        return storeTraceList;
    }

    public void setStoreTraceList(List<StoreTrace> storeTraceList) {
        this.storeTraceList = storeTraceList;
    }

    public static class StoreTrace {
        private Long storeId;
        private String storeName;
        private Boolean success;
        private String msg;

        public Long getStoreId() {
            return storeId;
        }

        public void setStoreId(Long storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
