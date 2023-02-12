package info.batcloud.wxc.core.settings;

import info.batcloud.wxc.core.settings.annotation.Single;

import java.io.Serializable;

@Single
public class SmsSetting implements Serializable {

    private String signName;

    private String registerTemplateCode;

    private String findPwdTemplateCode;

    private String phoneLoginTemplateCode;

    private String phoneBindTemplateCode;

    private String withdrawTemplateCode;

    private int expireSeconds;

    private int cooldown;

    public String getWithdrawTemplateCode() {
        return withdrawTemplateCode;
    }

    public void setWithdrawTemplateCode(String withdrawTemplateCode) {
        this.withdrawTemplateCode = withdrawTemplateCode;
    }

    public String getPhoneBindTemplateCode() {
        return phoneBindTemplateCode;
    }

    public void setPhoneBindTemplateCode(String phoneBindTemplateCode) {
        this.phoneBindTemplateCode = phoneBindTemplateCode;
    }

    public String getPhoneLoginTemplateCode() {
        return phoneLoginTemplateCode;
    }

    public void setPhoneLoginTemplateCode(String phoneLoginTemplateCode) {
        this.phoneLoginTemplateCode = phoneLoginTemplateCode;
    }

    public String getFindPwdTemplateCode() {
        return findPwdTemplateCode;
    }

    public void setFindPwdTemplateCode(String findPwdTemplateCode) {
        this.findPwdTemplateCode = findPwdTemplateCode;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getRegisterTemplateCode() {
        return registerTemplateCode;
    }

    public void setRegisterTemplateCode(String registerTemplateCode) {
        this.registerTemplateCode = registerTemplateCode;
    }
}
