package info.batcloud.wxc.core.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class FoodQuoteSheetDetail {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long foodQuoteSheetId;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Food food;

    @NotNull
    private Date createTime;

    @NotNull
    private Float price;

    @NotNull
    private String foodName;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    private String foodUnit;

    private Float salePrice;

    private String foodSkuJson;

    public String getFoodUnit() {
        return foodUnit;
    }

    public void setFoodUnit(String foodUnit) {
        this.foodUnit = foodUnit;
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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getFoodSkuJson() {
        return foodSkuJson;
    }

    public void setFoodSkuJson(String foodSkuJson) {
        this.foodSkuJson = foodSkuJson;
    }
}
