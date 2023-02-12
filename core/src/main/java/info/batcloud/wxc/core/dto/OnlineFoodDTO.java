package info.batcloud.wxc.core.dto;

public class OnlineFoodDTO {

//    foodPicture: f.picture,
//    foodId: f.id,
//    foodName: f.name,
//    foodSkuList: f.skuList,
//    price: f.price,
//    salePrice: f.salePrice || 0,
//    foodUnit: f.quoteUnit,
//    sale: f.sale || false,
//    foodPerIncrease: f.perIncrease

    private String foodPicture;
    private Long foodId;
    private String foodName;
    private Float price;
    private Float salePrice;
    private String foodUnit;
    private Boolean sale;
    private Float foodPerIncrease;
    private Float quotePrice;

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

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

    public Boolean getSale() {
        return sale;
    }

    public void setSale(Boolean sale) {
        this.sale = sale;
    }

    public Float getFoodPerIncrease() {
        return foodPerIncrease;
    }

    public void setFoodPerIncrease(Float foodPerIncrease) {
        this.foodPerIncrease = foodPerIncrease;
    }

    public Float getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(Float quotePrice) {
        this.quotePrice = quotePrice;
    }
}
