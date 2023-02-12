package info.batcloud.wxc.core.dto;

public class WalletDTO {

    private Long id;

    private StoreUserDTO storeUser;

    //现金余额
    private float money;

    //已提现
    private float withdrawMoney;

    //返利的现金
    private float returnedMoney;

    /**
     * 已消费的金额，用于购买商品，比如夺宝的商品等
     */
    private float consumedMoney;

    /**
     * 总共获取的金额
     */
    private float obtainedMoney;

    public float getObtainedMoney() {
        return obtainedMoney;
    }

    public void setObtainedMoney(float obtainedMoney) {
        this.obtainedMoney = obtainedMoney;
    }

    public Long getId() {
        return id;
    }

    public StoreUserDTO getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUserDTO storeUser) {
        this.storeUser = storeUser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(float withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public float getReturnedMoney() {
        return returnedMoney;
    }

    public void setReturnedMoney(float returnedMoney) {
        this.returnedMoney = returnedMoney;
    }

    public float getConsumedMoney() {
        return consumedMoney;
    }

    public void setConsumedMoney(float consumedMoney) {
        this.consumedMoney = consumedMoney;
    }
}
