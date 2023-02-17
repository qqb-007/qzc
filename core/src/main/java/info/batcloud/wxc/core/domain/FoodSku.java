package info.batcloud.wxc.core.domain;

public class FoodSku {

    private String skuId;
    private String spec;
    //售价比例
    private float priceRatio;
    //报价规格比例
    private float quoteUnitRatio;
    private String boxNum;
    private String boxPrice;

    private Integer weight;

    private Integer stock;

    private String warehouseIds;


    public String getWarehouseIds() {
        return warehouseIds;
    }

    public void setWarehouseIds(String warehouseIds) {
        this.warehouseIds = warehouseIds;
    }

    /**
     * 忽略发布
     */
    private boolean ignore;

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public float getQuoteUnitRatio() {
        return quoteUnitRatio;
    }

    public void setQuoteUnitRatio(float quoteUnitRatio) {
        this.quoteUnitRatio = quoteUnitRatio;
    }

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

    public String getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(String boxNum) {
        this.boxNum = boxNum;
    }

    public String getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(String boxPrice) {
        this.boxPrice = boxPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
