package info.batcloud.wxc.core.dto;

import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.enums.UuAccountType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class StoreDTO {

    private Long id;

    private String name;

    private String address;

    private String code;

    private String picUrl;

    private String phone;

    private Boolean isOnline;

    private Plat plat;

    private StoreUserDTO storeUser;

    private Float priceIncrease;

    private Boolean opening;

    private DeliveryType deliveryType;

    private DeliveryType secondDeliveryType;

    private UuAccountType uuAccountType;

    private Long closeTime;
    private Boolean lineOpen;

    private Boolean showButton;

    private Boolean deliverySelf;

    private Boolean jd;

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

    public UuAccountType getUuAcountType() {
        return uuAccountType;
    }

    public void setUuAcountType(UuAccountType uuAcountType) {
        this.uuAccountType = uuAcountType;
    }

    public Boolean getJd() {
        return jd;
    }

    public void setJd(Boolean jd) {
        this.jd = jd;
    }

    public Long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    public Boolean getLineOpen() {
        return lineOpen;
    }

    public void setLineOpen(Boolean lineOpen) {
        this.lineOpen = lineOpen;
    }

    public String getUuAccountTypeTitle() {
        return uuAccountType == null ? UuAccountType.TOTAL.getTitle() : uuAccountType.getTitle();
    }

    public String getDeliveryTypeTitle() {
        return deliveryType == null ? null : deliveryType.getTitle();
    }

    public String getSecondDeliveryTypeTitle() {
        return secondDeliveryType == null ? null : secondDeliveryType.getTitle();
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
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

    public StoreUserDTO getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(StoreUserDTO storeUser) {
        this.storeUser = storeUser;
    }

    public String getPlatTitle() {
        return plat == null ? null : plat.getTitle();
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
