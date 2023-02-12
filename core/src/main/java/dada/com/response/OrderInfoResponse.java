package dada.com.response;

public class OrderInfoResponse extends Response<OrderInfoResponse.Result> {
    public static class Result {
        private Integer statusCode;
        private String statusMsg;
        private String orderId;
        private String transporterName;
        private String transporterPhone;
        private String transporterLng;
        private String transporterLat;
        private Double deliveryFee;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public void setStatusMsg(String statusMsg) {
            this.statusMsg = statusMsg;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTransporterName() {
            return transporterName;
        }

        public void setTransporterName(String transporterName) {
            this.transporterName = transporterName;
        }

        public String getTransporterPhone() {
            return transporterPhone;
        }

        public void setTransporterPhone(String transporterPhone) {
            this.transporterPhone = transporterPhone;
        }

        public String getTransporterLng() {
            return transporterLng;
        }

        public void setTransporterLng(String transporterLng) {
            this.transporterLng = transporterLng;
        }

        public String getTransporterLat() {
            return transporterLat;
        }

        public void setTransporterLat(String transporterLat) {
            this.transporterLat = transporterLat;
        }

        public Double getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(Double deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "statusCode=" + statusCode +
                    ", statusMsg='" + statusMsg + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", transporterName='" + transporterName + '\'' +
                    ", transporterPhone='" + transporterPhone + '\'' +
                    ", transporterLng='" + transporterLng + '\'' +
                    ", transporterLat='" + transporterLat + '\'' +
                    ", deliveryFee=" + deliveryFee +
                    '}';
        }
    }

}
