package info.batcloud.wxc.core.settings;

import java.io.Serializable;

public class AlipaySetting implements Serializable {

    //付款方姓名
    private String payerShowName;

    public String getPayerShowName() {
        return payerShowName;
    }

    public void setPayerShowName(String payerShowName) {
        this.payerShowName = payerShowName;
    }
}
