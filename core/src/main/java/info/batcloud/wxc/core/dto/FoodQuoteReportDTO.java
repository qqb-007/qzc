package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FoodQuoteReportDTO {

    private long id;
    private Long foodId;
    private RegionDTO city;
    private RegionDTO province;
    private String foodPic;
    private String foodName;
    private float maxQuotePrice;
    private float minQuotePrice;
    private float avgQuotePrice;
    private float foodPrice;
    private int totalQuoteNum;
    private int greatQuoteNum;
    private int lessQuoteNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private Float refPrice; // 参考价

    private Float warningPrice; // 警戒价

    public Float getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(Float refPrice) {
        this.refPrice = refPrice;
    }

    public Float getWarningPrice() {
        return warningPrice;
    }

    public void setWarningPrice(Float warningPrice) {
        this.warningPrice = warningPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public RegionDTO getCity() {
        return city;
    }

    public void setCity(RegionDTO city) {
        this.city = city;
    }

    public RegionDTO getProvince() {
        return province;
    }

    public void setProvince(RegionDTO province) {
        this.province = province;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
