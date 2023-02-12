package info.batcloud.wxc.merchant.api.domain;

import info.batcloud.wxc.core.domain.Result;

public class LoginResult extends Result {

    private Long storeUserId;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(Long storeUserId) {
        this.storeUserId = storeUserId;
    }
}
