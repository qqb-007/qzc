package info.batcloud.wxc.core.entity;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.enums.UuAccountType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Store {

    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String code;

    @NotNull
    private String picUrl;

    @NotNull
    private String phone;

    @NotNull
    private Boolean isOnline;

    @Enumerated(EnumType.STRING)
    private Plat plat;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private StoreUser storeUser;

    /**
     * 加价百分比
     */
    @NotNull
    private Float priceIncrease;

    @NotNull
    private Boolean opening;

    private String eleCategoryMapJson;
    private Long closeTime;
    private Boolean lineOpen;
    private Boolean showButton;
    private Boolean deliverySelf;
    private Boolean jd;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    private DeliveryType secondDeliveryType;

    @Enumerated(EnumType.STRING)
    private UuAccountType uuAccountType;

    private Float changeUuTime;

    private Boolean uuMix;

    public Boolean getDeliverySelf() {
        return deliverySelf;
    }

    public void setDeliverySelf(Boolean deliverySelf) {
        this.deliverySelf = deliverySelf;
    }

    public Boolean getShowButton() {
        return showButton;
    }

    public void setShowButton(Boolean showButton) {
        this.showButton = showButton;
    }

    public DeliveryType getSecondDeliveryType() {
        return secondDeliveryType;
    }

    public void setSecondDeliveryType(DeliveryType secondDeliveryType) {
        this.secondDeliveryType = secondDeliveryType;
    }

    public Long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    public Boolean getLineOpen() {
        return lineOpen == null ? false : lineOpen;
    }

    public void setLineOpen(Boolean lineOpen) {
        this.lineOpen = lineOpen;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public UuAccountType getUuAcountType() {
        return uuAccountType;
    }

    public void setUuAcountType(UuAccountType uuAcountType) {
        this.uuAccountType = uuAcountType;
    }

    public Map<String, Long> getEleCategoryMap() {
        return JSON.parseObject(StringUtils.isBlank(eleCategoryMapJson) ? "{}" : eleCategoryMapJson, HashMap.class);
    }

    public void setEleCategoryMap(Map<String, Long> eleCategoryMap) {
        this.eleCategoryMapJson = JSON.toJSONString(eleCategoryMap);
    }

    public String getEleCategoryMapJson() {
        return eleCategoryMapJson;
    }

    public void setEleCategoryMapJson(String eleCategoryMapJson) {
        this.eleCategoryMapJson = eleCategoryMapJson;
    }

    public Boolean getOpening() {
        return opening;
    }

    public void setOpening(Boolean opening) {
        this.opening = opening;
    }

    public Float getPriceIncrease() {
        return priceIncrease;
    }

    public void setPriceIncrease(Float priceIncrease) {
        this.priceIncrease = priceIncrease;
    }

    public StoreUser getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUser storeUser) {
        this.storeUser = storeUser;
    }

    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getJd() {
        return jd;
    }

    public void setJd(Boolean jd) {
        this.jd = jd;
    }

    public Float getChangeUuTime() {
        return changeUuTime;
    }

    public void setChangeUuTime(Float changeUuTime) {
        this.changeUuTime = changeUuTime;
    }

    public Boolean getUuMix() {
        return uuMix;
    }

    public void setUuMix(Boolean uuMix) {
        this.uuMix = uuMix;
    }
}
