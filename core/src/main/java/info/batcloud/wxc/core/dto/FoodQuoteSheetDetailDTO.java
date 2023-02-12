package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.domain.FoodQuoteSku;

import java.util.Date;
import java.util.List;

public class FoodQuoteSheetDetailDTO {

    private Long id;

    private Long foodQuoteSheetId;

    private FoodDTO food;

    private List<FoodQuoteSku> foodQuoteSkuList;

    private String foodPicture;

    private String foodUnit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Boolean published;

    private String errMsg;

    private Float price;

    private Float salePrice;

    private String foodName;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    public List<FoodQuoteSku> getFoodQuoteSkuList() {
        return foodQuoteSkuList;
    }

    public void setFoodQuoteSkuList(List<FoodQuoteSku> foodQuoteSkuList) {
        this.foodQuoteSkuList = foodQuoteSkuList;
    }

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodQuoteSheetId() {
        return foodQuoteSheetId;
    }

    public void setFoodQuoteSheetId(Long foodQuoteSheetId) {
        this.foodQuoteSheetId = foodQuoteSheetId;
    }

    public FoodDTO getFood() {
        return food;
    }

    public void setFood(FoodDTO food) {
        this.food = food;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
