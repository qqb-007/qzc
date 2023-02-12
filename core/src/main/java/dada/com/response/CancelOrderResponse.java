package dada.com.response;

public class CancelOrderResponse extends Response<CancelOrderResponse.Result> {
    public static class Result {
        private Double deduct_fee;

        public Double getDeduct_fee() {
            return deduct_fee;
        }

        public void setDeduct_fee(Double deduct_fee) {
            this.deduct_fee = deduct_fee;
        }
    }
}
