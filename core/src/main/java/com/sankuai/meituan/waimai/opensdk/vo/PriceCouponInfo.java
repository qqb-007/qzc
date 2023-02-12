package com.sankuai.meituan.waimai.opensdk.vo;

public class PriceCouponInfo {
    private Integer full_price;
    private Integer reduce_price;
    private Integer stock;
    private Integer user_type;

    public Integer getFull_price() {
        return full_price;
    }

    public void setFull_price(Integer full_price) {
        this.full_price = full_price;
    }

    public Integer getReduce_price() {
        return reduce_price;
    }

    public void setReduce_price(Integer reduce_price) {
        this.reduce_price = reduce_price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }
}
