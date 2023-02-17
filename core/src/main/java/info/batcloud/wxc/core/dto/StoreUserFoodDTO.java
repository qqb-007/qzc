package info.batcloud.wxc.core.dto;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.enums.PublishStatus;
import info.batcloud.wxc.core.enums.QuoteStatus;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreUserFoodDTO {

    private Long id;

    private Long unlockTime;

    private StoreUser storeUser;

    private Date createTime;

    private Date updateTime;

    private String saleTime;

    private List<StoreUserFoodSkuDTO> skuList;

    private Float quotePrice;

    private Float originalQuotePrice;

    private Boolean quotePriceLock;

    private Float salePrice;

    private Long cityId;

    private FoodDTO food;

    private FoodSupplierDTO foodSupplier;

    private Float supplierQuotePrice;

    private Float supplierAlterQuotePrice;

    private Boolean sale;

    private String specialSkuIdList;

    private Float priceIncrease;

    private QuoteStatus quoteStatus;

    private Float alterQuotePrice;

    private String publishMsg;

    private Float warningPrice;
    private Float refPrice;

    private Float maxQuotePrice;
    private Float minQuotePrice;
    private Float avgQuotePrice;

    private Long meituanVideoId;
    private Long clbmVideoId;

    private FoodCategory category;

    private String eleSkuId;

    private PublishStatus meituanPublishStatus;
    private PublishStatus elePublishStatus;
    private PublishStatus wantePublishStatus;
    private PublishStatus jddjPublishStatus;
    private PublishStatus clbmPublishStatus;
    private Integer activeType;
    private String warehouseName;

    private Float supplierIncrease;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

//    public List<FoodSku> getSkuList() {
//        return skuList;
//    }
//
//    public void setSkuList(List<FoodSku> skuList) {
//        this.skuList = skuList;
//    }


    public List<StoreUserFoodSkuDTO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<StoreUserFoodSkuDTO> skuList) {
        this.skuList = skuList;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    public Integer getActiveType() {
        return activeType;
    }

    public void setActiveType(Integer activeType) {
        this.activeType = activeType;
    }

    public Long getMeituanVideoId() {
        return meituanVideoId;
    }

    public void setMeituanVideoId(Long meituanVideoId) {
        this.meituanVideoId = meituanVideoId;
    }

    public Long getClbmVideoId() {
        return clbmVideoId;
    }

    public void setClbmVideoId(Long clbmVideoId) {
        this.clbmVideoId = clbmVideoId;
    }

    public Float getSupplierAlterQuotePrice() {
        return supplierAlterQuotePrice;
    }

    public void setSupplierAlterQuotePrice(Float supplierAlterQuotePrice) {
        this.supplierAlterQuotePrice = supplierAlterQuotePrice;
    }

    public Map<String, String> getSaleTimeMap() {
        return JSON.parseObject(StringUtils.isBlank(saleTime) ? "{}" : saleTime, HashMap.class);
    }

    public void setSaleTimeMap(Map<String, String> saleTimeMap) {
        this.saleTime = JSON.toJSONString(saleTimeMap);
    }

    public Float getSupplierIncrease() {
        return supplierIncrease;
    }

    public void setSupplierIncrease(Float supplierIncrease) {
        this.supplierIncrease = supplierIncrease;
    }

    public Float getSupplierQuotePrice() {
        return supplierQuotePrice;
    }

    public void setSupplierQuotePrice(Float supplierQuotePrice) {
        this.supplierQuotePrice = supplierQuotePrice;
    }

    public FoodSupplierDTO getFoodSupplier() {
        return foodSupplier;
    }

    public void setFoodSupplier(FoodSupplierDTO foodSupplier) {
        this.foodSupplier = foodSupplier;
    }

    public String getEleSkuId() {
        return eleSkuId;
    }

    public void setEleSkuId(String eleSkuId) {
        this.eleSkuId = eleSkuId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Float getWarningPrice() {
        return warningPrice;
    }

    public void setWarningPrice(Float warningPrice) {
        this.warningPrice = warningPrice;
    }

    public Float getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(Float refPrice) {
        this.refPrice = refPrice;
    }

    public Float getMaxQuotePrice() {
        return maxQuotePrice;
    }

    public void setMaxQuotePrice(Float maxQuotePrice) {
        this.maxQuotePrice = maxQuotePrice;
    }

    public Float getMinQuotePrice() {
        return minQuotePrice;
    }

    public void setMinQuotePrice(Float minQuotePrice) {
        this.minQuotePrice = minQuotePrice;
    }

    public Float getAvgQuotePrice() {
        return avgQuotePrice;
    }

    public void setAvgQuotePrice(Float avgQuotePrice) {
        this.avgQuotePrice = avgQuotePrice;
    }

    public PublishStatus getClbmPublishStatus() {
        return clbmPublishStatus;
    }

    public void setClbmPublishStatus(PublishStatus clbmPublishStatus) {
        this.clbmPublishStatus = clbmPublishStatus;
    }

    public PublishStatus getJddjPublishStatus() {
        return jddjPublishStatus;
    }

    public void setJddjPublishStatus(PublishStatus jddjPublishStatus) {
        this.jddjPublishStatus = jddjPublishStatus;
    }

    public PublishStatus getMeituanPublishStatus() {
        return meituanPublishStatus;
    }

    public PublishStatus getWantePublishStatus() {
        return wantePublishStatus;
    }

    public void setWantePublishStatus(PublishStatus wantePublishStatus) {
        this.wantePublishStatus = wantePublishStatus;
    }

    public void setMeituanPublishStatus(PublishStatus meituanPublishStatus) {
        this.meituanPublishStatus = meituanPublishStatus;
    }

    public PublishStatus getElePublishStatus() {
        return elePublishStatus;
    }

    public String getMeituanPublishStatusTitle() {
        return meituanPublishStatus == null ? PublishStatus.WAIT.getTitle() : meituanPublishStatus.getTitle();
    }

    public String getElePublishStatusTitle() {
        return elePublishStatus == null ? PublishStatus.WAIT.getTitle() : elePublishStatus.getTitle();
    }

    public String getClbmPublishStatusTitle() {
        return clbmPublishStatus == null ? PublishStatus.WAIT.getTitle() : clbmPublishStatus.getTitle();
    }

    public String getWantePublishStatusTitle() {
        return wantePublishStatus == null ? PublishStatus.WAIT.getTitle() : wantePublishStatus.getTitle();
    }

    public String getJddjPublishStatusTitle() {
        return jddjPublishStatus == null ? PublishStatus.WAIT.getTitle() : jddjPublishStatus.getTitle();
    }

    public void setElePublishStatus(PublishStatus elePublishStatus) {
        this.elePublishStatus = elePublishStatus;
    }

    public String getSpecialSkuIdList() {
        return specialSkuIdList;
    }

    public void setSpecialSkuIdList(String specialSkuIdList) {
        this.specialSkuIdList = specialSkuIdList;
    }

    public String getPublishMsg() {
        return publishMsg;
    }

    public void setPublishMsg(String publishMsg) {
        this.publishMsg = publishMsg;
    }

    public Float getAlterQuotePrice() {
        return alterQuotePrice;
    }

    public void setAlterQuotePrice(Float alterQuotePrice) {
        this.alterQuotePrice = alterQuotePrice;
    }

    public String getQuoteStatusTitle() {
        return quoteStatus == null ? null : quoteStatus.getTitle();
    }

    public QuoteStatus getQuoteStatus() {
        return quoteStatus;
    }

    public void setQuoteStatus(QuoteStatus quoteStatus) {
        this.quoteStatus = quoteStatus;
    }

    public Float getPriceIncrease() {
        return priceIncrease;
    }

    public void setPriceIncrease(Float priceIncrease) {
        this.priceIncrease = priceIncrease;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public String getFoodUnit() {
        return food.getQuoteUnit();
    }

    public Boolean getSale() {
        return sale;
    }

    public void setSale(Boolean sale) {
        this.sale = sale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreUser getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUser storeUser) {
        this.storeUser = storeUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Float getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(Float quotePrice) {
        this.quotePrice = quotePrice;
    }

    public FoodDTO getFood() {
        return food;
    }

    public void setFood(FoodDTO food) {
        this.food = food;
    }

    public Boolean getQuotePriceLock() {
        return quotePriceLock;
    }

    public void setQuotePriceLock(Boolean quotePriceLock) {
        this.quotePriceLock = quotePriceLock;
    }

    public Float getOriginalQuotePrice() {
        return originalQuotePrice;
    }

    public void setOriginalQuotePrice(Float originalQuotePrice) {
        this.originalQuotePrice = originalQuotePrice;
    }

    public Long getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(Long unlockTime) {
        this.unlockTime = unlockTime;
    }

    public static class StoreUser {
        private Long id;

        private String name;

        private boolean meituanOpened;
        private boolean eleOpened;
        private boolean wanteOpened;
        private boolean jddjOpened;
        private boolean clbmOpened;

        public boolean isClbmOpened() {
            return clbmOpened;
        }

        public void setClbmOpened(boolean clbmOpened) {
            this.clbmOpened = clbmOpened;
        }

        public boolean isJddjOpened() {
            return jddjOpened;
        }

        public void setJddjOpened(boolean jddjOpened) {
            this.jddjOpened = jddjOpened;
        }

        public boolean isWanteOpened() {
            return wanteOpened;
        }

        public void setWanteOpened(boolean wanteOpened) {
            this.wanteOpened = wanteOpened;
        }

        public boolean isMeituanOpened() {
            return meituanOpened;
        }

        public void setMeituanOpened(boolean meituanOpened) {
            this.meituanOpened = meituanOpened;
        }

        public boolean isEleOpened() {
            return eleOpened;
        }

        public void setEleOpened(boolean eleOpened) {
            this.eleOpened = eleOpened;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
