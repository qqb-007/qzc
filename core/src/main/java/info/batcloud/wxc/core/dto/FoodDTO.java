package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.domain.Propertie;
import info.batcloud.wxc.core.helper.FoodHelper;

import java.util.Date;
import java.util.List;

public class FoodDTO {

    private Long id;

    private String parentCategoryName;

    private String code;

    private Float boxNum;

    private Float boxPrice;

    private String categoryName;

    private int minOrderCount;

    private String name;

    private String description;

    private Float price;

    private String unit;

    private String picture;

    private String eleBrandName;

    private String pictureId;

    private String clbPictureId;

    private String jdPicture;

    private String video;

    private String clbPicture;

    private List<FoodSku> skuList;

    private List<FoodSkuDTO> skus;

    //报价规格
    private String quoteUnit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Boolean quotable;

    private Float perIncrease;

    private String zhName;

    private String productName;

    private String originName;

    private String flavour;

    private Integer isSp;

    private String pictureContent;

    private Boolean deleted;

    private Long meituanTagId;

    private String meituanTagName;

    private Integer idx;

    private float avgQuotePrice;

    private float maxQuotePrice;

    private float minQuotePrice;

    private Long actualFoodId;

    private boolean promotional;

    private Float warningPrice;

    private String propertieJson;

    private List<Propertie> propertieList;

    public List<Propertie> getPropertieList() {
        return propertieList;
    }

    public void setPropertieList(List<Propertie> propertieList) {
        this.propertieList = propertieList;
    }

    public String getPropertieJson() {
        return propertieJson;
    }

    public void setPropertieJson(String propertieJson) {
        this.propertieJson = propertieJson;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getClbPicture() {
        return clbPicture;
    }

    public void setClbPicture(String clbPicture) {
        this.clbPicture = clbPicture;
    }

    public String getJdPicture() {
        return jdPicture;
    }

    public void setJdPicture(String jdPicture) {
        this.jdPicture = jdPicture;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getEleBrandName() {
        return eleBrandName;
    }

    public void setEleBrandName(String eleBrandName) {
        this.eleBrandName = eleBrandName;
    }

    public Float getWarningPrice() {
        return warningPrice;
    }

    public void setWarningPrice(Float warningPrice) {
        this.warningPrice = warningPrice;
    }

    public boolean isPromotional() {
        return promotional;
    }

    public void setPromotional(boolean promotional) {
        this.promotional = promotional;
    }

    public Long getActualFoodId() {
        return actualFoodId;
    }

    public void setActualFoodId(Long actualFoodId) {
        this.actualFoodId = actualFoodId;
    }

    public float getMaxQuotePrice() {
        return maxQuotePrice;
    }

    public void setMaxQuotePrice(float maxQuotePrice) {
        this.maxQuotePrice = maxQuotePrice;
    }

    public float getMinQuotePrice() {
        return minQuotePrice;
    }

    public void setMinQuotePrice(float minQuotePrice) {
        this.minQuotePrice = minQuotePrice;
    }

    public float getAvgQuotePrice() {
        return avgQuotePrice;
    }

    public void setAvgQuotePrice(float avgQuotePrice) {
        this.avgQuotePrice = avgQuotePrice;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Long getMeituanTagId() {
        return meituanTagId;
    }

    public void setMeituanTagId(Long meituanTagId) {
        this.meituanTagId = meituanTagId;
    }

    public String getMeituanTagName() {
        return meituanTagName;
    }

    public void setMeituanTagName(String meituanTagName) {
        this.meituanTagName = meituanTagName;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public Integer getIsSp() {
        return isSp;
    }

    public void setIsSp(Integer isSp) {
        this.isSp = isSp;
    }

    public String getPictureContent() {
        return pictureContent;
    }

    public void setPictureContent(String pictureContent) {
        this.pictureContent = pictureContent;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public FoodSku findSku(String skuId) {
        return FoodHelper.findSku(skuId, this.skuList);
    }

    public Float getPerIncrease() {
        return perIncrease;
    }

    public void setPerIncrease(Float perIncrease) {
        this.perIncrease = perIncrease;
    }

    public Boolean getQuotable() {
        return quotable;
    }

    public void setQuotable(Boolean quotable) {
        this.quotable = quotable;
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

    public String getQuoteUnit() {
        return quoteUnit;
    }

    public void setQuoteUnit(String quoteUnit) {
        this.quoteUnit = quoteUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Float boxNum) {
        this.boxNum = boxNum;
    }

    public Float getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(Float boxPrice) {
        this.boxPrice = boxPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getMinOrderCount() {
        return minOrderCount;
    }

    public void setMinOrderCount(int minOrderCount) {
        this.minOrderCount = minOrderCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<FoodSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<FoodSku> skuList) {
        this.skuList = skuList;
    }

    public String getClbPictureId() {
        return clbPictureId;
    }

    public void setClbPictureId(String clbPictureId) {
        this.clbPictureId = clbPictureId;
    }

    public List<FoodSkuDTO> getSkus() {
        return skus;
    }

    public void setSkus(List<FoodSkuDTO> skus) {
        this.skus = skus;
    }
}
