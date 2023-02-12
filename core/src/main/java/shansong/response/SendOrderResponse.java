package shansong.response;

public class SendOrderResponse extends Response<SendOrderResponse.Data> {
    public static class Data {
        private String orderNumber;
        private String totalDistance;
        private String totalWeight;
        private String totalFeeAfterSave;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getTotalDistance() {
            return totalDistance;
        }

        public void setTotalDistance(String totalDistance) {
            this.totalDistance = totalDistance;
        }

        public String getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(String totalWeight) {
            this.totalWeight = totalWeight;
        }

        public String getTotalFeeAfterSave() {
            return totalFeeAfterSave;
        }

        public void setTotalFeeAfterSave(String totalFeeAfterSave) {
            this.totalFeeAfterSave = totalFeeAfterSave;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orderNumber='" + orderNumber + '\'' +
                    ", totalDistance='" + totalDistance + '\'' +
                    ", totalWeight='" + totalWeight + '\'' +
                    ", totalFeeAfterSave='" + totalFeeAfterSave + '\'' +
                    '}';
        }
    }
}
