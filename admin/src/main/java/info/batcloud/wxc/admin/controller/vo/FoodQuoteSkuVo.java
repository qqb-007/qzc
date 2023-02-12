package info.batcloud.wxc.admin.controller.vo;

public class FoodQuoteSkuVo {

    private String skuId;
    private String spec;
    //售价比例
    private float priceRatio;

    private float salePrice;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public float getPriceRatio() {
        return priceRatio;
    }

    public void setPriceRatio(float priceRatio) {
        this.priceRatio = priceRatio;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }
}
