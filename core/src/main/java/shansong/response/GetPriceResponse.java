package shansong.response;

public class GetPriceResponse extends Response<GetPriceResponse.Data> {
    public static class Data {
        private Integer totalDistance;
        private String orderNumber;
        private Integer totalAmount;
        private Integer totalFeeAfterSave;

        public Integer getTotalDistance() {
            return totalDistance;
        }

        public void setTotalDistance(Integer totalDistance) {
            this.totalDistance = totalDistance;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public Integer getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Integer totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Integer getTotalFeeAfterSave() {
            return totalFeeAfterSave;
        }

        public void setTotalFeeAfterSave(Integer totalFeeAfterSave) {
            this.totalFeeAfterSave = totalFeeAfterSave;
        }
    }
}
