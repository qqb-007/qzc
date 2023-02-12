package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.ShopUpdateResponse;
import me.ele.sdk.up.response.SkuCreateResponse;

import java.util.List;

public class ShopUpdateRequest extends Request<ShopUpdateResponse> {

    @JSONField(name = "shop_id")
    private String shopId;

    @JSONField(name = "coord_type")
    private String coodType;

    @JSONField(name = "delivery_region")
    private List<DeliveryRegion> deliveryRegions;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCoodType() {
        return coodType;
    }

    public void setCoodType(String coodType) {
        this.coodType = coodType;
    }

    public List<DeliveryRegion> getDeliveryRegions() {
        return deliveryRegions;
    }

    public void setDeliveryRegions(List<DeliveryRegion> deliveryRegions) {
        this.deliveryRegions = deliveryRegions;
    }

    public static class DeliveryRegion {
        private String name;//配送区域名称;必须
        @JSONField(name = "delivery_time")//配送时长;单位:分钟;必须;
        private String deliveryTime;
        @JSONField(name = "delivery_fee")//配送费;单位:分;必须;
        private String deliveryFee;
        @JSONField(name = "min_buy_free")//满免配送费;单位:分;大于0;必须;
        private String minBuyFree;
        @JSONField(name = "min_order_price")//外卖起送价;单位:分;必须;
        private String minOrderPrice;
        private List<List<Region>> region;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(String deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public String getMinBuyFree() {
            return minBuyFree;
        }

        public void setMinBuyFree(String minBuyFree) {
            this.minBuyFree = minBuyFree;
        }

        public String getMinOrderPrice() {
            return minOrderPrice;
        }

        public void setMinOrderPrice(String minOrderPrice) {
            this.minOrderPrice = minOrderPrice;
        }

        public List<List<Region>> getRegion() {
            return region;
        }

        public void setRegion(List<List<Region>> region) {
            this.region = region;
        }
    }

    public static class Region {
        private Float longitude;//配送区域坐标点经度
        private Float latitude;//配送区域坐标点维度

        public Float getLongitude() {
            return longitude;
        }

        public void setLongitude(Float longitude) {
            this.longitude = longitude;
        }

        public Float getLatitude() {
            return latitude;
        }

        public void setLatitude(Float latitude) {
            this.latitude = latitude;
        }
    }


    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "shop.update";
    }

    @Override
    public Class<ShopUpdateResponse> getResponseClass() {
        return ShopUpdateResponse.class;
    }
}
