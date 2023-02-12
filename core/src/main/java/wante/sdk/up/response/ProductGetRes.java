package wante.sdk.up.response;

import java.util.List;

public class ProductGetRes {
    private Integer code;
    private String msg;
    private ProductInfo data;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ProductInfo getData() {
        return data;
    }

    public void setData(ProductInfo data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static class ProductInfo{
        private Integer id;
        private String productName;
        private Float originalPrice;
        private Float sellingPrice;
        private Integer status;
        private String productCategoryName;
        private Integer productCategoryId;
        private String storeName;
        private Integer storeId;
        private List<ProductStandard> productStandards;//规格信息，只有一个

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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getProductCategoryName() {
            return productCategoryName;
        }

        public void setProductCategoryName(String productCategoryName) {
            this.productCategoryName = productCategoryName;
        }

        public Integer getProductCategoryId() {
            return productCategoryId;
        }

        public void setProductCategoryId(Integer productCategoryId) {
            this.productCategoryId = productCategoryId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public List<ProductStandard> getProductStandards() {
            return productStandards;
        }

        public void setProductStandards(List<ProductStandard> productStandards) {
            this.productStandards = productStandards;
        }
    }
    public static class ProductStandard{
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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
