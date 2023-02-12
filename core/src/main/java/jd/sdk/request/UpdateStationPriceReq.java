package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.UpdateStationPriceRes;

public class UpdateStationPriceReq extends Request<UpdateStationPriceRes> {
    private String stationNo;//到家门店编码
    private String outSkuId;//商品编码
    private Long price;
    private String serviceNo;//uuid

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/venderprice/updateStationPriceBySingle";
    }

    @Override
    public Class<UpdateStationPriceRes> getResponseClass() {
        return UpdateStationPriceRes.class;
    }
}
