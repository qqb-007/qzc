package info.batcloud.wxc.core.alipay.domain;

import com.alibaba.fastjson.annotation.JSONField;

public class FundTransToAccountTransferContent {

    @JSONField(name = "out_biz_no")
    private String outBizNo;
    @JSONField(name = "payee_type")
    private String payeeType;
    @JSONField(name = "payee_account")
    private String payeeAccount;
    private float amount;
    @JSONField(name = "payer_show_name")
    private String payerShowName;
    @JSONField(name = "payee_real_name")
    private String payeeRealName;
    private String remark;

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getPayeeType() {
        return payeeType;
    }

    public void setPayeeType(String payeeType) {
        this.payeeType = payeeType;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPayerShowName() {
        return payerShowName;
    }

    public void setPayerShowName(String payerShowName) {
        this.payerShowName = payerShowName;
    }

    public String getPayeeRealName() {
        return payeeRealName;
    }

    public void setPayeeRealName(String payeeRealName) {
        this.payeeRealName = payeeRealName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
