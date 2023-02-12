package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * 商品标品库商品类目
 */
public class PLSProductCategory {
    public long id;
    public String name;
    public int level;
    public String namePath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    @Override
    public String toString() {
        return "PLSProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", namePath='" + namePath + '\'' +
                '}';
    }
}
