package dada.com.response;

public class AddOrderResponse extends Response<AddOrderResponse.Result> {
    public static class Result {
        private Double distance;
        private Double fee;
        private Double deliverFee;
        private Double couponFee;
        private Double tips;
        private Double insuranceFee;

        @Override
        public String toString() {
            return "Result{" +
                    "distance=" + distance +
                    ", fee=" + fee +
                    ", deliverFee=" + deliverFee +
                    ", couponFee=" + couponFee +
                    ", tips=" + tips +
                    ", insuranceFee=" + insuranceFee +
                    '}';
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Double getFee() {
            return fee;
        }

        public void setFee(Double fee) {
            this.fee = fee;
        }

        public Double getDeliverFee() {
            return deliverFee;
        }

        public void setDeliverFee(Double deliverFee) {
            this.deliverFee = deliverFee;
        }

        public Double getCouponFee() {
            return couponFee;
        }

        public void setCouponFee(Double couponFee) {
            this.couponFee = couponFee;
        }

        public Double getTips() {
            return tips;
        }

        public void setTips(Double tips) {
            this.tips = tips;
        }

        public Double getInsuranceFee() {
            return insuranceFee;
        }

        public void setInsuranceFee(Double insuranceFee) {
            this.insuranceFee = insuranceFee;
        }
    }
}
