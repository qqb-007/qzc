package dada.com.response;

import java.util.List;

public class CreateShopResponse extends Response<CreateShopResponse.Result> {
    public static class Result {
        private Integer success;

        private List<String> failedList;

        public Integer getSuccess() {
            return success;
        }

        public void setSuccess(Integer success) {
            this.success = success;
        }

        public List<String> getFailedList() {
            return failedList;
        }

        public void setFailedList(List<String> failedList) {
            this.failedList = failedList;
        }
    }
}
