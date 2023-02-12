package wante.sdk.up.request;

import com.alibaba.fastjson.JSONObject;
import wante.sdk.up.AbstractRequest;
import wante.sdk.up.response.StoreDeliveryUpdateRes;

import java.util.List;

public class StoreDeliveryUpdateReq extends AbstractRequest<StoreDeliveryUpdateRes> {
    private Integer storeId;
    private List<UpdateInfo> updateInfo;

    public List<UpdateInfo> getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(List<UpdateInfo> updateInfo) {
        this.updateInfo = updateInfo;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getURL() {
        return "http://store.mall.wangxiaocai.cn/api/store/distribution/accept/store_distribution/" + this.storeId + "/update";
    }

    @Override
    public Boolean isJson() {
        return true;
    }

    @Override
    public String getJson() {
        return JSONObject.toJSONString(this.updateInfo);
    }

    @Override
    public String getSecret() {
        return "Basic c3RvcmU6TDlJQm9KZDFKUnU1YlR5bw==";
    }

    @Override
    public Class<StoreDeliveryUpdateRes> getResponseType() {
        return StoreDeliveryUpdateRes.class;
    }

    public static class UpdateInfo {
        private String address;
        private Double longitude;
        private Double latitude;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
    }
}
