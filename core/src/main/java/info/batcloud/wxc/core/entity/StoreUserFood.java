
package info.batcloud.wxc.core.entity;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.enums.PublishStatus;
import info.batcloud.wxc.core.enums.QuoteStatus;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class StoreUserFood {

    @Id
    @GeneratedValue
    private Long id;
    private Long unlockTime;
    private Long meituanVideoId;
    private Long clbmVideoId;
    private Long cityId;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private StoreUser storeUser;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private FoodSupplier foodSupplier;

    @NotNull
    private Date createTime;

    @NotNull
    private Date updateTime;

    /**
     * 供应商报价
     */
    private Float supplierQuotePrice;

    /**
     * 供应商修改的报价
     */
    private Float supplierAlterQuotePrice;

    @NotNull
    private Float quotePrice;

    private Boolean quotePriceLock;

    private Float originalQuotePrice;

    @NotNull
    private Float salePrice;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Food food;

    @NotNull
    private Boolean sale;

    //报价单位
    @NotNull
    private String foodUnit;

//    @NotNull
//    private String foodSkuJson;

    private String saleTime;

    @NotNull
    private Float priceIncrease;

    /**
     * 供应商报价加价给门店老板,
     * 结算给门店老板的价格。
     */
    private Float supplierIncrease;

    @Enumerated(EnumType.STRING)
    @NotNull
    private QuoteStatus quoteStatus;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumns({@JoinColumn(name = "food_id", referencedColumnName = "foodId", insertable = false, updatable = false),
            @JoinColumn(name = "cityId", referencedColumnName = "city_id", insertable = false, updatable = false)})
    private FoodQuoteReport foodQuoteReport;

    /**
     * 改变的报价，需要审核的报价
     */
    private Float alterQuotePrice;

    private String publishMsg;

    //private String warehouseIds;

    private Integer foodVersion;

    private Integer activeType;
    private String wanteSkuMapJson;
    private String specialSkuIdList;

    private String eleSkuId;
    private Long wanteId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private FoodCategory category;

    @Enumerated(EnumType.STRING)
    private PublishStatus meituanPublishStatus;

    @Enumerated(EnumType.STRING)
    private PublishStatus elePublishStatus;

    @Enumerated(EnumType.STRING)
    private PublishStatus wantePublishStatus;

    @Enumerated(EnumType.STRING)
    private PublishStatus jddjPublishStatus;

    @Enumerated(EnumType.STRING)
    private PublishStatus clbmPublishStatus;

    private String elePhotosJson;

    private Integer minOrderCount;

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

    public Integer getMinOrderCount() {
        return minOrderCount;
    }

    public PublishStatus getClbmPublishStatus() {
        return clbmPublishStatus == null ? PublishStatus.WAIT : clbmPublishStatus;
    }

    public void setClbmPublishStatus(PublishStatus clbmPublishStatus) {
        this.clbmPublishStatus = clbmPublishStatus;
    }

    public void setMinOrderCount(Integer minOrderCount) {
        this.minOrderCount = minOrderCount;
    }

    public Map<Integer, String> getWanteSkuMap() {
        return JSON.parseObject(StringUtils.isBlank(wanteSkuMapJson) ? "{}" : wanteSkuMapJson, HashMap.class);
    }

    public void setWanteSkuMap(Map<Integer, String> wanteSkuMap) {
        this.wanteSkuMapJson = JSON.toJSONString(wanteSkuMap);
    }

    public Map<String, String> getSaleTimeMap() {
        return JSON.parseObject(StringUtils.isBlank(saleTime) ? "{}" : saleTime, HashMap.class);
    }

    public void setSaleTimeMap(Map<String, String> saleTimeMap) {
        this.saleTime = JSON.toJSONString(saleTimeMap);
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    public String getWanteSkuMapJson() {
        return wanteSkuMapJson;
    }

    public void setWanteSkuMapJson(String wanteSkuMapJson) {
        this.wanteSkuMapJson = wanteSkuMapJson;
    }

    public Long getWanteId() {
        return wanteId;
    }

    public void setWanteId(Long wanteId) {
        this.wanteId = wanteId;
    }

    public Float getSupplierAlterQuotePrice() {
        return supplierAlterQuotePrice;
    }


    public void setSupplierAlterQuotePrice(Float supplierAlterQuotePrice) {
        this.supplierAlterQuotePrice = supplierAlterQuotePrice;
    }

    public Float getSupplierQuotePrice() {
        return supplierQuotePrice;
    }

    public void setSupplierQuotePrice(Float supplierQuotePrice) {
        this.supplierQuotePrice = supplierQuotePrice;
    }

    public Float getSupplierIncrease() {
        return supplierIncrease;
    }

    public void setSupplierIncrease(Float supplierIncrease) {
        this.supplierIncrease = supplierIncrease;
    }

    public FoodSupplier getFoodSupplier() {
        return foodSupplier;
    }

    public void setFoodSupplier(FoodSupplier foodSupplier) {
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

    public FoodQuoteReport getFoodQuoteReport() {
        return foodQuoteReport;
    }

    public void setFoodQuoteReport(FoodQuoteReport foodQuoteReport) {
        this.foodQuoteReport = foodQuoteReport;
    }


//    public String getWarehouseIds() {
//        return warehouseIds;
//    }
//
//    public void setWarehouseIds(String warehouseIds) {
//        this.warehouseIds = warehouseIds;
//    }

    public Map<String, String> getElePhotos() {
        if (StringUtils.isBlank(getElePhotosJson())) {
            return new HashMap<>();
        } else {
            return JSON.parseObject(getElePhotosJson(), HashMap.class);
        }
    }

    public void setElePhotos(Map<String, String> elePhotos) {
        this.elePhotosJson = JSON.toJSONString(elePhotos);
    }

    public String getElePhotosJson() {
        return elePhotosJson;
    }

    public void setElePhotosJson(String elePhotosJson) {
        this.elePhotosJson = elePhotosJson;
    }

    public PublishStatus getMeituanPublishStatus() {
        return meituanPublishStatus;
    }

    public void setMeituanPublishStatus(PublishStatus meituanPublishStatus) {
        this.meituanPublishStatus = meituanPublishStatus;
    }

    public PublishStatus getJddjPublishStatus() {
        return jddjPublishStatus == null ? PublishStatus.WAIT : jddjPublishStatus;
    }

    public void setJddjPublishStatus(PublishStatus jddjPublishStatus) {
        this.jddjPublishStatus = jddjPublishStatus;
    }

    public PublishStatus getWantePublishStatus() {
        return wantePublishStatus == null ? PublishStatus.WAIT : wantePublishStatus;
    }

    public void setWantePublishStatus(PublishStatus wantePublishStatus) {
        this.wantePublishStatus = wantePublishStatus;
    }

    public PublishStatus getElePublishStatus() {
        return elePublishStatus == null ? PublishStatus.WAIT : elePublishStatus;
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

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public Integer getFoodVersion() {
        return foodVersion;
    }

    public void setFoodVersion(Integer foodVersion) {
        this.foodVersion = foodVersion;
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

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

//    public String getFoodSkuJson() {
//        return foodSkuJson;
//    }
//
//    public void setFoodSkuJson(String foodSkuJson) {
//        this.foodSkuJson = foodSkuJson;
//    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Boolean getQuotePriceLock() {
        return quotePriceLock == null ? false : quotePriceLock;
    }

    public void setQuotePriceLock(Boolean quotePriceLock) {
        this.quotePriceLock = quotePriceLock;
    }

    public Long getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(Long unlockTime) {
        this.unlockTime = unlockTime;
    }

    public Float getOriginalQuotePrice() {
        return originalQuotePrice;
    }

    public void setOriginalQuotePrice(Float originalQuotePrice) {
        this.originalQuotePrice = originalQuotePrice;
    }

    public Integer getActiveType() {
        return activeType;
    }

    public void setActiveType(Integer activeType) {
        this.activeType = activeType;
    }
}
