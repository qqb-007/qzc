package com.sankuai.meituan.waimai.opensdk.vo;

/**
 * Created by zhangzhidong on 15/10/28.
 */
public class skuStockParam {
    private String sku_id;
    private String stock;

    public String getSku_id() {
        return sku_id;
    }

    public skuStockParam setSku_id(String sku_id) {
        this.sku_id = sku_id;
        return this;
    }

    public String getStock() {
        return stock;
    }

    public skuStockParam setStock(String stock) {
        this.stock = stock;
        return this;
    }

    @Override
    public String toString() {
        return "skuStockParam [" +
                "sku_id='" + sku_id + '\'' +
                ", stock='" + stock + '\'' +
                ']';
    }
}
