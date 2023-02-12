package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class skuPriceParam {
    private String sku_id;
    private String price;

    public String getSku_id() {
        return sku_id;
    }

    public skuPriceParam setSku_id(String sku_id) {
        this.sku_id = sku_id;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public skuPriceParam setPrice(String price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "skuPriceParam [" +
                "sku_id='" + sku_id + '\'' +
                ", price='" + price + '\'' +
                ']';
    }
}
