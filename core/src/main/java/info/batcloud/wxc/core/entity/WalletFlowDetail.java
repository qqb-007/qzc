package info.batcloud.wxc.core.entity;


import info.batcloud.wxc.core.context.StaticContext;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.enums.WalletValueType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WalletFlowDetail {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private StoreUser storeUser;

    private Date createTime;

    @Enumerated(EnumType.STRING)
    private WalletValueType valueType;

    private float beforeValue;

    private float value;

    private float afterValue;

    @Enumerated(EnumType.STRING)
    private WalletFlowDetailType type;

    private String context;

    public StoreUser getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUser storeUser) {
        this.storeUser = storeUser;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public WalletFlowDetailType getType() {
        return type;
    }

    public void setType(WalletFlowDetailType type) {
        this.type = type;
    }

    public String getDescription() {
        return StaticContext.messageSource.getMessage("WalletFlowDetail." + type.name(), context == null ? null : context.split(","), null);
    }

    public float getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(float beforeValue) {
        this.beforeValue = beforeValue;
    }

    public float getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(float afterValue) {
        this.afterValue = afterValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public WalletValueType getValueType() {
        return valueType;
    }

    public void setValueType(WalletValueType valueType) {
        this.valueType = valueType;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
