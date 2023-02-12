package info.batcloud.wxc.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class FoodQuoteReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long foodId;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Region city;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Region province;
    @NotNull
    private String foodPic;
    @NotNull
    private String foodName;
    @NotNull
    private float maxQuotePrice;
    @NotNull
    private float minQuotePrice;
    @NotNull
    private float avgQuotePrice;
    @NotNull
    private float foodPrice;
    @NotNull
    private int totalQuoteNum;
    @NotNull
    private int greatQuoteNum;
    @NotNull
    private int lessQuoteNum;

    private Float refPrice; // 参考价

    private Float warningPrice; // 警戒价

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

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

    public Region getProvince() {
        return province;
    }

    public void setProvince(Region province) {
        this.province = province;
    }

    public Region getCity() {
        return city;
    }

    public void setCity(Region city) {
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    @Override
    public int hashCode() {
        return this.getFoodId().intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FoodQuoteReport)) {
            return false;
        }
        return this.foodId.equals(((FoodQuoteReport) obj).foodId);
    }
}
