package wante.sdk.up.request;

import com.alibaba.fastjson.JSONObject;
import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.ProductUpdateRes;

import java.util.List;

public class ProductUpdateReq extends AbstractRequest<ProductUpdateRes> {

    private UpdateInfo updateInfo;

    public UpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(UpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getURL() {
        return "http://product.mall.wangxiaocai.cn/api/product/update";
    }

    @Override
    public Boolean isJson() {
        return true;
    }

    @Override
    public String getJson() {
        return JSONObject.toJSONString(this.updateInfo);
    }

    @Override
    public String getSecret() {
        return "Basic bWFsbDpMOUlCb0pkMUpSdTViVHlv";
    }

    @Override
    public Class<ProductUpdateRes> getResponseType() {
        return ProductUpdateRes.class;
    }

    public static class UpdateInfo {
        private Integer id;
        private String productName;
        private Integer enable;
        private Integer brandId;
        private Integer productCategoryId;
        private Integer productType;
        private String productContent;
        private Float originalPrice;
        private Float sellingPrice;
        private Float costPrice;
        private Integer status;
        private Integer storeId;
        private Integer productStock;
        private String productQuality;
        private String weight;
        private List<String> images;//图片
        private Boolean specDelete;

        public Boolean getSpecDelete() {
            return specDelete;
        }

        public void setSpecDelete(Boolean specDelete) {
            this.specDelete = specDelete;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getProductQuality() {
            return productQuality;
        }

        public void setProductQuality(String productQuality) {
            this.productQuality = productQuality;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getEnable() {
            return enable;
        }

        public void setEnable(Integer enable) {
            this.enable = enable;
        }

        public Integer getBrandId() {
            return brandId;
        }

        public void setBrandId(Integer brandId) {
            this.brandId = brandId;
        }

        public Integer getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(Integer productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public Integer getProductType() {
            return productType;
        }

        public void setProductType(Integer productType) {
            this.productType = productType;
        }

        public String getProductContent() {
            return productContent;
        }

        public void setProductContent(String productContent) {
            this.productContent = productContent;
        }

        public Float getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Float originalPrice) {
            this.originalPrice = originalPrice;
        }

        public Float getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(Float sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public Float getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(Float costPrice) {
            this.costPrice = costPrice;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public Integer getProductStock() {
            return productStock;
        }

        public void setProductStock(Integer productStock) {
            this.productStock = productStock;
        }

    }
}
