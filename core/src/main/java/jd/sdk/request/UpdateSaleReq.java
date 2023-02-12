package jd.sdk.request;

import jd.sdk.Request;
import jd.sdk.response.UpdateSaleRes;

/*门店商品上下架接口*/
public class UpdateSaleReq extends Request<UpdateSaleRes> {

    private String outStationId;
    private String userPin;
    private String outSkuId;
    private Boolean doSale;

    public String getOutStationId() {
        return outStationId;
    }

    public void setOutStationId(String outStationId) {
        this.outStationId = outStationId;
    }

    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getOutSkuId() {
        return outSkuId;
    }

    public void setOutSkuId(String outSkuId) {
        this.outSkuId = outSkuId;
    }

    public Boolean getDoSale() {
        return doSale;
    }

    public void setDoSale(Boolean doSale) {
        this.doSale = doSale;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getUrl() {
        return "https://openapi.jddj.com/djapi/stock/updateVendibility4SingleByOutsideSkuId";
    }

    @Override
    public Class<UpdateSaleRes> getResponseClass() {
        return UpdateSaleRes.class;
    }
}
