package info.batcloud.wxc.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.batcloud.wxc.core.enums.EnumTitle;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.enums.WalletValueType;

import java.util.Date;

public class WalletFlowDetailDTO {

    private Long id;

    private StoreUserDTO storeUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private WalletValueType valueType;

    private float beforeValue;

    private float value;

    private float afterValue;

    private WalletFlowDetailType type;

    private String context;

    private EnumTitle status;

    private Long relationId;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public EnumTitle getStatus() {
        return status;
    }

    public String getStatusTitle() {
        return status == null ? null : status.getTitle();
    }

    public void setStatus(EnumTitle status) {
        this.status = status;
    }

    public String getValueTypeTitle() {
        return getValueType() == null ? null : getValueType().getTitle();
    }

    public String getTypeTitle() {
        return getType() == null ? null : getType().getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreUserDTO getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUserDTO storeUser) {
        this.storeUser = storeUser;
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

    public float getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(float beforeValue) {
        this.beforeValue = beforeValue;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(float afterValue) {
        this.afterValue = afterValue;
    }

    public WalletFlowDetailType getType() {
        return type;
    }

    public void setType(WalletFlowDetailType type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
