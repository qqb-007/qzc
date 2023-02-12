package info.batcloud.wxc.core.mapper.report.domain;

public class FoodQuoteReportData {

    private Long cityId;
    private Long provinceId;
    private Long foodId;
    private String foodPic;
    private String foodName;
    private float maxQuotePrice;
    private float minQuotePrice;
    private float avgQuotePrice;
    private float foodPrice;
    private int totalQuoteNum;
    private int greatQuoteNum;
    private int lessQuoteNum;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodPic() {
        return foodPic;
    }

    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public float getMaxQuotePrice() {
        return maxQuotePrice;
    }

    public void setMaxQuotePrice(float maxQuotePrice) {
        this.maxQuotePrice = maxQuotePrice;
    }

    public float getMinQuotePrice() {
        return minQuotePrice;
    }

    public void setMinQuotePrice(float minQuotePrice) {
        this.minQuotePrice = minQuotePrice;
    }

    public float getAvgQuotePrice() {
        return avgQuotePrice;
    }

    public void setAvgQuotePrice(float avgQuotePrice) {
        this.avgQuotePrice = avgQuotePrice;
    }

    public float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(float foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getTotalQuoteNum() {
        return totalQuoteNum;
    }

    public void setTotalQuoteNum(int totalQuoteNum) {
        this.totalQuoteNum = totalQuoteNum;
    }

    public int getGreatQuoteNum() {
        return greatQuoteNum;
    }

    public void setGreatQuoteNum(int greatQuoteNum) {
        this.greatQuoteNum = greatQuoteNum;
    }

    public int getLessQuoteNum() {
        return lessQuoteNum;
    }

    public void setLessQuoteNum(int lessQuoteNum) {
        this.lessQuoteNum = lessQuoteNum;
    }
}
