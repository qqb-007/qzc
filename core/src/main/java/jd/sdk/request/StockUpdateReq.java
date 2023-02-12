package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.StockUpdateRes;

/*
 *根据商家商品编码和商家门店编码更新门店现货库存接口
 *  */
public class StockUpdateReq extends Request<StockUpdateRes> {
    private String stationNo;//商家门店编码，不是到家门店编码
    private Long skuId;//商家商品编码，
    private Integer currentQty;//现货库存

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(Integer currentQty) {
        this.currentQty = currentQty;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/stock/update";
    }

    @Override
    public Class<StockUpdateRes> getResponseClass() {
        return StockUpdateRes.class;
    }
}
