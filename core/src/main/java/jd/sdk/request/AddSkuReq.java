package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.AddSkuRes;

import java.util.List;

public class AddSkuReq extends Request<AddSkuRes> {
    private String traceId;
    private String outSkuId;
    private String shopCategories;
    private Long categoryId;
    private Long brandId;
    private String skuName;
    private Long skuPrice;
    private Float weight;
    private List<String> images;
    private Integer fixedStatus;//上下架状态
    private Boolean isSale;//商品可售状态

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    public String getShopCategories() {
        return shopCategories;
    }

    public void setShopCategories(String shopCategories) {
        this.shopCategories = shopCategories;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(Long skuPrice) {
        this.skuPrice = skuPrice;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getFixedStatus() {
        return fixedStatus;
    }

    public void setFixedStatus(Integer fixedStatus) {
        this.fixedStatus = fixedStatus;
    }

    public Boolean getIsSale() {
        return isSale;
    }

    public void setIsSale(Boolean isSale) {
        this.isSale = isSale;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/pms/addSku";
    }

    @Override
    public Class getResponseClass() {
        return AddSkuRes.class;
    }
}
