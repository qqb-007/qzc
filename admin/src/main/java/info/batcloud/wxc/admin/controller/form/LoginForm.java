package info.batcloud.wxc.admin.controller.form;

/**
 * Created by Administrator on 2017-06-05.
 */
public class LoginForm {

    private String account;
    private String password;

    private int holdIn;

    public int getHoldIn() {
        return holdIn;
    }

    public void setHoldIn(int holdIn) {
        this.holdIn = holdIn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
