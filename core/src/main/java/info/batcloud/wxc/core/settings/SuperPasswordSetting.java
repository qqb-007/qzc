package info.batcloud.wxc.core.settings;

import info.batcloud.wxc.core.settings.annotation.Single;

import java.io.Serializable;

@Single
public class SuperPasswordSetting implements Serializable {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
