package jd.sdk.response;

import jd.sdk.Response;

import java.util.Map;

public class UpdateStoreInfoRes extends Response<UpdateStoreInfoRes.Data> {
    public static class Data {
        private String code;
        private String msg;
        private String result;
        private Result data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public Result getData() {
            return data;
        }

        public void setData(Result data) {
            this.data = data;
        }
    }

    public static class Result {
        private Byte deliveryRangeType;
        private String coordinatePoints;

        public Byte getDeliveryRangeType() {
            return deliveryRangeType;
        }

        public void setDeliveryRangeType(Byte deliveryRangeType) {
            this.deliveryRangeType = deliveryRangeType;
        }

        public String getCoordinatePoints() {
            return coordinatePoints;
        }

        public void setCoordinatePoints(String coordinatePoints) {
            this.coordinatePoints = coordinatePoints;
        }
    }
}
