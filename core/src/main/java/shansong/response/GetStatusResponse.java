package shansong.response;

public class GetStatusResponse extends Response<GetStatusResponse.Data> {
    public static class Data {
        private Integer orderStatus;

        private Integer totalFeeAfterSave;

        public Integer getTotalFeeAfterSave() {
            return totalFeeAfterSave;
        }

        public void setTotalFeeAfterSave(Integer totalFeeAfterSave) {
            this.totalFeeAfterSave = totalFeeAfterSave;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "orderStatus=" + orderStatus +
                    ", totalFeeAfterSave=" + totalFeeAfterSave +
                    '}';
        }
    }
}
