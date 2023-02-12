package info.batcloud.wxc.core.mapper.domain;

public class FoodPrice {
    private Long foodId;
    private Float price;

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