package wante.sdk.up.request;

import com.alibaba.fastjson.JSONObject;
import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.ProductSaveRes;

import java.util.List;

public class ProductSaveReq extends AbstractRequest<ProductSaveRes> {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getURL() {
        return "http://product.mall.wangxiaocai.cn/api/product/save";
    }

    @Override
    public Boolean isJson() {
        return true;
    }

    @Override
    public String getJson() {
        return JSONObject.toJSONString(this.product);
    }

    @Override
    public String getSecret() {
        return "Basic bWFsbDpMOUlCb0pkMUpSdTViVHlv";
    }

    @Override
    public Class<ProductSaveRes> getResponseType() {
        return ProductSaveRes.class;
    }

    public static class Product {
        private String productName;//商品名称
        private Integer enable;//是否启用
        private Integer brandId;//品牌id
        private Integer productCategoryId;//分类id
        private Integer productType;//商品类型
        private String productContent;//商品详情
        private Float originalPrice;//原价
        private Float sellingPrice;//售价
        private Float costPrice;//成本价
        private Integer status;//状态( 0，上架； 1，下架)默认0
        private Integer storeId;//店铺id;
        private Integer productStock;//库存
        private List<String> images;//图片
        private String productQuality;
        private String weight;

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getProductQuality() {
            return productQuality;
        }

        public void setProductQuality(String productQuality) {
            this.productQuality = productQuality;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
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
