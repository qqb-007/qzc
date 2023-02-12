package info.batcloud.wxc.core.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 钱包?
 * */
@Entity
public class Wallet {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private StoreUser storeUser;

    private Date createTime;

    private Date updateTime;

    //现金余额
    private float money;

    /**
     * 已消费的金额，用于购买商品，比如夺宝的商品等
     */
    private float consumedMoney;

    /**
     * 提现的金额
     */
    private float withdrawMoney;

    //总共获取的money
    private float obtainedMoney;

    @Version
    private int version;//版本

    public float getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(float withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public float getObtainedMoney() {
        return obtainedMoney;
    }

    public void setObtainedMoney(float obtainedMoney) {
        this.obtainedMoney = obtainedMoney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreUser getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUser storeUser) {
        this.storeUser = storeUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getConsumedMoney() {
        return consumedMoney;
    }

    public void setConsumedMoney(float consumedMoney) {
        this.consumedMoney = consumedMoney;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
