package info.batcloud.wxc.core.dto;

public class FoodSkuDTO {
    private Long id;

    private FoodDTO foodDTO;
    //upc
    private String upc;

    private String name;

    private Integer weight;

    private String spec;

    private Float inputTax;

    private Float outputTax;

    private Integer minOrderCount;

    private Integer boxNum;

    private Float boxPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodDTO getFoodDTO() {
        return foodDTO;
    }

    public void setFoodDTO(FoodDTO foodDTO) {
        this.foodDTO = foodDTO;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Float getInputTax() {
        return inputTax;
    }

    public void setInputTax(Float inputTax) {
        this.inputTax = inputTax;
    }

    public Float getOutputTax() {
        return outputTax;
    }

    public void setOutputTax(Float outputTax) {
        this.outputTax = outputTax;
    }

    public Integer getMinOrderCount() {
        return minOrderCount;
    }

    public void setMinOrderCount(Integer minOrderCount) {
        this.minOrderCount = minOrderCount;
    }

    public Integer getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Integer boxNum) {
        this.boxNum = boxNum;
    }

    public Float getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(Float boxPrice) {
        this.boxPrice = boxPrice;
    }
}
