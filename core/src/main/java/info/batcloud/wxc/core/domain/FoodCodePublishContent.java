package info.batcloud.wxc.core.domain;

import info.batcloud.wxc.core.enums.Plat;

public class FoodCodePublishContent {

    private Long storeId;
    private String storeName;
    private Plat plat;
    private Boolean success;
    private String result;

    public String getPlatTitle() {
        return plat == null ? null : plat.getTitle();
    }

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
