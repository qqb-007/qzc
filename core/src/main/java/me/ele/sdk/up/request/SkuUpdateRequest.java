package me.ele.sdk.up.request;

import com.alibaba.fastjson.annotation.JSONField;
import me.ele.sdk.up.Request;
import me.ele.sdk.up.response.SkuUpdateResponse;

import java.util.List;
import java.util.Map;

public class SkuUpdateRequest extends Request<SkuUpdateResponse> {

    @JSONField(name = "shop_id")
    private String shopId;

    private String upc;

    private String name;

    private int status;

    @JSONField(name = "shelf_number")
    private String shelfNumber;

    @JSONField(name = "brand_id")
    private Long brandId;

    @JSONField(name = "brand_name")
    private String brandName;

    @JSONField(name = "category_id")
    private Long categoryId;

    @JSONField(name = "cate1_id")
    private Long cate1Id;
    @JSONField(name = "cate2_id")
    private Long cate2Id;
    @JSONField(name = "cate3_id")
    private Long cate3Id;

    @JSONField(name = "predict_cat")
    private Integer predictCat;

    private List<Photo> photos;

    private String desc;

    private String rtf;

    @JSONField(name = "left_num")
    private Integer leftNum;

    @JSONField(name = "sale_price")
    private Integer salePrice;

    @JSONField(name = "market_price")
    private Integer marketPrice;

    private Integer weight;

    @JSONField(name = "purchase_limit")
    private Integer purchaseLimit;

    @JSONField(name = "custom_sku_id")
    private String customSkuId;

    private String summary;

    @JSONField(name = "sale_unit")
    private String saleUnit;

    @JSONField(name = "duration_sale_flag")
    private Boolean durationSaleFlag;

    @JSONField(name = "repeat_date")
    private List<String> repeatDate;

    @JSONField(name = "effect_time_map")
    private Map<String,String> effectTimeMap;

    public Boolean getDurationSaleFlag() {
        return durationSaleFlag;
    }

    public void setDurationSaleFlag(Boolean durationSaleFlag) {
        this.durationSaleFlag = durationSaleFlag;
    }

    public List<String> getRepeatDate() {
        return repeatDate;
    }

    public void setRepeatDate(List<String> repeatDate) {
        this.repeatDate = repeatDate;
    }

    public Map<String, String> getEffectTimeMap() {
        return effectTimeMap;
    }

    public void setEffectTimeMap(Map<String, String> effectTimeMap) {
        this.effectTimeMap = effectTimeMap;
    }

    public String getSaleUnit() {
        return saleUnit;
    }

    public void setSaleUnit(String saleUnit) {
        this.saleUnit = saleUnit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(String shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCate1Id() {
        return cate1Id;
    }

    public void setCate1Id(Long cate1Id) {
        this.cate1Id = cate1Id;
    }

    public Long getCate2Id() {
        return cate2Id;
    }

    public void setCate2Id(Long cate2Id) {
        this.cate2Id = cate2Id;
    }

    public Long getCate3Id() {
        return cate3Id;
    }

    public void setCate3Id(Long cate3Id) {
        this.cate3Id = cate3Id;
    }

    public Integer getPredictCat() {
        return predictCat;
    }

    public void setPredictCat(Integer predictCat) {
        this.predictCat = predictCat;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRtf() {
        return rtf;
    }

    public void setRtf(String rtf) {
        this.rtf = rtf;
    }

    public Integer getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPurchaseLimit() {
        return purchaseLimit;
    }

    public void setPurchaseLimit(Integer purchaseLimit) {
        this.purchaseLimit = purchaseLimit;
    }

    public String getCustomSkuId() {
        return customSkuId;
    }

    public void setCustomSkuId(String customSkuId) {
        this.customSkuId = customSkuId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public static class Photo {
        @JSONField(name = "is_master")
        private int isMaster;
        private String url;

        public int getIsMaster() {
            return isMaster;
        }

        public void setIsMaster(int isMaster) {
            this.isMaster = isMaster;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getCmd() {
        return "sku.update";
    }

    @Override
    public Class<SkuUpdateResponse> getResponseClass() {
        return SkuUpdateResponse.class;
    }
}
