package info.batcloud.wxc.admin.controller.form;

import org.python.antlr.ast.Str;

import java.util.List;

public class FoodSkuForm {
    private List<Long> idList;
    private List<String> nameList;
    private List<String> upcList;
    private List<Integer> weightList;
    private List<String> specList;
    private List<Float> inputTaxList;
    private List<Float> outputTaxList;
    private List<Integer> minOrderCountList;
    private List<Integer> boxNumList;
    private List<Float> boxPriceList;

    private List<Float> quoteUnitRatioList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<String> getUpcList() {
        return upcList;
    }

    public void setUpcList(List<String> upcList) {
        this.upcList = upcList;
    }

    public List<Integer> getWeightList() {
        return weightList;
    }

    public void setWeightList(List<Integer> weightList) {
        this.weightList = weightList;
    }

    public List<String> getSpecList() {
        return specList;
    }

    public void setSpecList(List<String> specList) {
        this.specList = specList;
    }

    public List<Float> getInputTaxList() {
        return inputTaxList;
    }

    public void setInputTaxList(List<Float> inputTaxList) {
        this.inputTaxList = inputTaxList;
    }

    public List<Float> getOutputTaxList() {
        return outputTaxList;
    }

    public void setOutputTaxList(List<Float> outputTaxList) {
        this.outputTaxList = outputTaxList;
    }

    public List<Integer> getMinOrderCountList() {
        return minOrderCountList;
    }

    public void setMinOrderCountList(List<Integer> minOrderCountList) {
        this.minOrderCountList = minOrderCountList;
    }

    public List<Integer> getBoxNumList() {
        return boxNumList;
    }

    public void setBoxNumList(List<Integer> boxNumList) {
        this.boxNumList = boxNumList;
    }

    public List<Float> getBoxPriceList() {
        return boxPriceList;
    }

    public void setBoxPriceList(List<Float> boxPriceList) {
        this.boxPriceList = boxPriceList;
    }

    public List<Float> getQuoteUnitRatioList() {
        return quoteUnitRatioList;
    }

    public void setQuoteUnitRatioList(List<Float> quoteUnitRatioList) {
        this.quoteUnitRatioList = quoteUnitRatioList;
    }
}
