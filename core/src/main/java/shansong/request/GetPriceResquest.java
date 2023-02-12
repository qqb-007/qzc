package shansong.request;

import shansong.response.GetPriceResponse;

import java.util.List;

public class GetPriceResquest extends Request<GetPriceResponse> {


    private String cityName;
    private Integer appointType;
    private String storeName;
    private String appointmentDate;
    //private Long storeId
    private Sender sender;
    private List<Receiver> receiverList;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getAppointType() {
        return appointType;
    }

    public void setAppointType(Integer appointType) {
        this.appointType = appointType;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public List<Receiver> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<Receiver> receiverList) {
        this.receiverList = receiverList;
    }

    @Override
    public String getUrl() {
        return "/openapi/merchants/v5/orderCalculate";
    }

    @Override
    public Class<GetPriceResponse> getResponseClass() {
        return GetPriceResponse.class;
    }

    public static class Sender{
        private String fromAddress;
        //private String fromAddressDetail;
        private String fromSenderName;
        private String fromMobile;
        private String fromLatitude;
        private String fromLongitude;

        public String getFromAddress() {
            return fromAddress;
        }

        public void setFromAddress(String fromAddress) {
            this.fromAddress = fromAddress;
        }

        public String getFromSenderName() {
            return fromSenderName;
        }

        public void setFromSenderName(String fromSenderName) {
            this.fromSenderName = fromSenderName;
        }

        public String getFromMobile() {
            return fromMobile;
        }

        public void setFromMobile(String fromMobile) {
            this.fromMobile = fromMobile;
        }

        public String getFromLatitude() {
            return fromLatitude;
        }

        public void setFromLatitude(String fromLatitude) {
            this.fromLatitude = fromLatitude;
        }

        public String getFromLongitude() {
            return fromLongitude;
        }

        public void setFromLongitude(String fromLongitude) {
            this.fromLongitude = fromLongitude;
        }
    }

    public static class Receiver{
        private String orderNo;
        private String toAddress;
        private String toLatitude;
        private String toLongitude;
        private String toReceiverName;
        private String toMobile;
        private String goodType;
        private String weight;
        private String remarks;
        private String orderingSourceType;
        private String orderingSourceNo;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getToAddress() {
            return toAddress;
        }

        public void setToAddress(String toAddress) {
            this.toAddress = toAddress;
        }

        public String getToLatitude() {
            return toLatitude;
        }

        public void setToLatitude(String toLatitude) {
            this.toLatitude = toLatitude;
        }

        public String getToLongitude() {
            return toLongitude;
        }

        public void setToLongitude(String toLongitude) {
            this.toLongitude = toLongitude;
        }

        public String getToReceiverName() {
            return toReceiverName;
        }

        public void setToReceiverName(String toReceiverName) {
            this.toReceiverName = toReceiverName;
        }

        public String getToMobile() {
            return toMobile;
        }

        public void setToMobile(String toMobile) {
            this.toMobile = toMobile;
        }

        public String getGoodType() {
            return goodType;
        }

        public void setGoodType(String goodType) {
            this.goodType = goodType;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getOrderingSourceType() {
            return orderingSourceType;
        }

        public void setOrderingSourceType(String orderingSourceType) {
            this.orderingSourceType = orderingSourceType;
        }

        public String getOrderingSourceNo() {
            return orderingSourceNo;
        }

        public void setOrderingSourceNo(String orderingSourceNo) {
            this.orderingSourceNo = orderingSourceNo;
        }
    }
}
