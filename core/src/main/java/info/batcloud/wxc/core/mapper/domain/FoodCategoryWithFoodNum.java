package info.batcloud.wxc.core.mapper.domain;

import java.io.Serializable;

public class FoodCategoryWithFoodNum implements Serializable {

    private String name;

    private int foodNum;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }
}
