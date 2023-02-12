package info.batcloud.wxc.admin.controller.vo;

import java.util.List;

public class FoodQuoteSheetDetailVerifyVo {

    private Long id;
    private String foodName;
    private String foodPicture;
    private Long foodId;
    private String foodUnit;
    private Float salePrice;
    private Float price;
    private List<FoodQuoteSkuVo> foodSkuList;

    public Float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Float salePrice) {
        this.salePrice = salePrice;
    }

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
    }

    public List<FoodQuoteSkuVo> getFoodSkuList() {
        return foodSkuList;
    }

    public void setFoodSkuList(List<FoodQuoteSkuVo> foodSkuList) {
        this.foodSkuList = foodSkuList;
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
