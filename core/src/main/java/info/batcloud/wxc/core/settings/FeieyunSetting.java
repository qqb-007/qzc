package info.batcloud.wxc.core.settings;

import info.batcloud.wxc.core.settings.annotation.Single;

import java.io.Serializable;

@Single
public class FeieyunSetting implements Serializable {

    private String user;
    private String ukey;
    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }
}
