package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.DeliveryinfoSyncResponse;

import java.util.List;

public class DeliveryinfoSyncRequest extends Request<DeliveryinfoSyncResponse> {

    @JSONField(name = "shop_id")
    private String shopId;


    @JSONField(name = "product_id")
    private String productId;


    @JSONField(name = "delivery_areas")
    private List<DeliveryAreas> deliveryAreas;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<DeliveryAreas> getDeliveryAreas() {
        return deliveryAreas;
    }

    public void setDeliveryAreas(List<DeliveryAreas> deliveryAreas) {
        this.deliveryAreas = deliveryAreas;
    }

    public static class DeliveryAreas {

        private String uuid;

        @JSONField(name = "area_type")
        private String areaType;

        @JSONField(name = "delivery_price")
        private String deliveryPrice;

        @JSONField(name = "agent_fee")
        private String agentFee;

        @JSONField(name = "max_weight")
        private String maxWeight;

        private List<Coordinates> coordinates;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getAreaType() {
            return areaType;
        }

        public void setAreaType(String areaType) {
            this.areaType = areaType;
        }

        public String getDeliveryPrice() {
            return deliveryPrice;
        }

        public void setDeliveryPrice(String deliveryPrice) {
            this.deliveryPrice = deliveryPrice;
        }

        public String getAgentFee() {
            return agentFee;
        }

        public void setAgentFee(String agentFee) {
            this.agentFee = agentFee;
        }

        public String getMaxWeight() {
            return maxWeight;
        }

        public void setMaxWeight(String maxWeight) {
            this.maxWeight = maxWeight;
        }

        public List<Coordinates> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Coordinates> coordinates) {
            this.coordinates = coordinates;
        }
    }

    public static class Coordinates {
        private String longitude;
        private String latitude;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }


    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "shop.deliveryinfo.sync";
    }

    @Override
    public Class<DeliveryinfoSyncResponse> getResponseClass() {
        return DeliveryinfoSyncResponse.class;
    }
}
