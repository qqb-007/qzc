package info.batcloud.wxc.core.mapper.domain;

import info.batcloud.wxc.core.entity.FoodCategory;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class FirstFoodCategory implements Serializable {
    private Long id;
    private Integer idx;
    private String name;
    private Integer level;
    private Long parentId;
    private List<FoodCategory> foodCategories;
    private Long meituanTagId;
    private Long jingdongTagId;
    private String jingdongTagName;

    public Long getJingdongTagId() {
        return jingdongTagId;
    }

    public void setJingdongTagId(Long jingdongTagId) {
        this.jingdongTagId = jingdongTagId;
    }

    public String getJingdongTagName() {
        return jingdongTagName;
    }

    public void setJingdongTagName(String jingdongTagName) {
        this.jingdongTagName = jingdongTagName;
    }

    private String meituanTagName;

    public Long getMeituanTagId() {
        return meituanTagId;
    }

    public void setMeituanTagId(Long meituanTagId) {
        this.meituanTagId = meituanTagId;
    }

    public String getMeituanTagName() {
        return meituanTagName;
    }

    public void setMeituanTagName(String meituanTagName) {
        this.meituanTagName = meituanTagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<FoodCategory> getFoodCategories() {
        return foodCategories;
    }

    public void setFoodCategories(List<FoodCategory> foodCategories) {
        this.foodCategories = foodCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstFoodCategory that = (FirstFoodCategory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(idx, that.idx) &&
                Objects.equals(name, that.name) &&
                Objects.equals(level, that.level) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(foodCategories, that.foodCategories) &&
                Objects.equals(meituanTagId, that.meituanTagId) &&
                Objects.equals(meituanTagName, that.meituanTagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idx, name, level, parentId, foodCategories, meituanTagId, meituanTagName);
    }

    @Override
    public String toString() {
        return "FirstFoodCategory{" +
                "id=" + id +
                ", idx=" + idx +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", parentId=" + parentId +
                ", foodCategories=" + foodCategories +
                ", meituanTagId=" + meituanTagId +
                ", meituanTagName='" + meituanTagName + '\'' +
                '}';
    }
}
