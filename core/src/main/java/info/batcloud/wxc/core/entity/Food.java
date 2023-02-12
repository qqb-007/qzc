package info.batcloud.wxc.core.entity;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Food implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    //商品编码
    private String code;

    @NotNull
    private Boolean deleted;

    //是否开启报价状态
    @NotNull
    private Boolean quotable;
    //饿了么品牌名
    private String eleBrandName;

    private Float boxNum;

    private Float boxPrice;
    //分类
    @NotNull
    private String categoryName;

    private String parentCategoryName;

    @NotNull
    private int minOrderCount;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Float price;

    /**
     * 警戒价
     */
    @NotNull
    private Float warningPrice;

    @NotNull
    private String unit;

    @NotNull
    private String picture;
    private String jdPicture;
    private String clbPicture;
    private String video;

    private String pictureId;
    private String clbPictureId;

    @NotNull
    private String skuJson;

    //报价规格
    private String quoteUnit;

    private Date createTime;

    private Date updateTime;

    private String zhName;

    private String productName;

    private String originName;

    private String flavour;

    @NotNull
    private Integer isSp = 0;

    private String pictureContent;

    //加价百分比
    @NotNull
    private Float perIncrease;

    //美团类目ID
    private Long meituanTagId;

    private String meituanTagName;

    @Version
    private Integer version;

    @NotNull
    private Integer idx;

    private Long actualFoodId;

    @NotNull
    private boolean promotional;

    private String jddjSkuMapJson;

    private String propertieJson;

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

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMeituanTagName() {
        return meituanTagName;
    }

    public void setMeituanTagName(String meituanTagName) {
        this.meituanTagName = meituanTagName;
    }

    public Long getMeituanTagId() {
        return meituanTagId;
    }

    public void setMeituanTagId(Long meituanTagId) {
        this.meituanTagId = meituanTagId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Float getPerIncrease() {
        return perIncrease;
    }

    public void setPerIncrease(Float perIncrease) {
        this.perIncrease = perIncrease;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public String getSkuJson() {
        return skuJson;
    }

    public void setSkuJson(String skuJson) {
        this.skuJson = skuJson;
    }

    public Map<String, String> getJddjSkuMap() {
        return JSON.parseObject(StringUtils.isBlank(jddjSkuMapJson) ? "{}" : jddjSkuMapJson, HashMap.class);
    }

    public void setJddjSkuMap(Map<String, String> jddjSkuMap) {
        this.jddjSkuMapJson = JSON.toJSONString(jddjSkuMap);
    }

    public String getJddjSkuMapJson() {
        return jddjSkuMapJson;
    }

    public void setJddjSkuMapJson(String jddjSkuMapJson) {
        this.jddjSkuMapJson = jddjSkuMapJson;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getClbPictureId() {
        return clbPictureId;
    }

    public void setClbPictureId(String clbPictureId) {
        this.clbPictureId = clbPictureId;
    }
}
