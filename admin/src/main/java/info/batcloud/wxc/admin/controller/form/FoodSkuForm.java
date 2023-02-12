package info.batcloud.wxc.admin.controller.form;

import java.util.List;

public class FoodSkuForm {

    private List<String> skuIdList;
    private List<String> specList;
    private List<Float> boxNumList;
    private List<Float> boxPriceList;
    private List<Float> priceRatioList;
    private List<Integer> weightList;
    private List<Boolean> ignoreList;

    private List<Float> quoteUnitRatioList;

    public List<Boolean> getIgnoreList() {
        return ignoreList;
    }

    public void setIgnoreList(List<Boolean> ignoreList) {
        this.ignoreList = ignoreList;
    }

    public List<Integer> getWeightList() {
        return weightList;
    }

    public void setWeightList(List<Integer> weightList) {
        this.weightList = weightList;
    }

    public List<Float> getQuoteUnitRatioList() {
        return quoteUnitRatioList;
    }

    public void setQuoteUnitRatioList(List<Float> quoteUnitRatioList) {
        this.quoteUnitRatioList = quoteUnitRatioList;
    }

    public List<String> getSkuIdList() {
        return skuIdList;
    }

    public void setSkuIdList(List<String> skuIdList) {
        this.skuIdList = skuIdList;
    }

    public List<String> getSpecList() {
        return specList;
    }

    public void setSpecList(List<String> specList) {
        this.specList = specList;
    }

    public List<Float> getBoxNumList() {
        return boxNumList;
    }

    public void setBoxNumList(List<Float> boxNumList) {
        this.boxNumList = boxNumList;
    }

    public List<Float> getBoxPriceList() {
        return boxPriceList;
    }

    public void setBoxPriceList(List<Float> boxPriceList) {
        this.boxPriceList = boxPriceList;
    }

    public List<Float> getPriceRatioList() {
        return priceRatioList;
    }

    public void setPriceRatioList(List<Float> priceRatioList) {
        this.priceRatioList = priceRatioList;
    }
}
