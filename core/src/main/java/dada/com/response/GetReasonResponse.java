package dada.com.response;

import java.util.List;

public class GetReasonResponse extends Response<List<GetReasonResponse.Result>> {
    public static class Result {
        private Integer id;
        private String reason;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
