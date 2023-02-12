package info.batcloud.wxc.core.entity;

import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.SettleType;
import info.batcloud.wxc.core.enums.StoreUserStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@NamedEntityGraph(name = "Region.Graph", attributeNodes = {@NamedAttributeNode("city"), @NamedAttributeNode("province"), @NamedAttributeNode("district")})
public class StoreUser {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Region province;

    @NotNull
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Region city;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Region district;

    @NotNull
    private String name;

    private String code;

    @NotNull
    private String phone;

    @NotNull
    private String address;

    @NotNull
    private String password;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Employee bizManager;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Employee bizManager2;

    //加价百分比
    @NotNull
    private Float priceIncrease;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StoreUserStatus status;

    @NotNull
    private Date createTime;

    /**
     * 最后结账日期
     */
    private Date lastSettlementDate;

    private String bankName;

    private String bankAccount;

    private String bankAccountName;

    private Boolean opened;

    //飞蛾打印机sn
    private String feiePrinterSn;

    //芯烨云打印机sn
    private String xyySn;

    //易联云账号
    private String machineCode;

    //易联云token
    private String accessToken;

    //易联云账号 refresh_token
    private String refreshToken;

    private String devideid;

    private String devicesecret;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SettleType settleType;

    /**
     * 自动接单
     */
    private Boolean autoReceiptOrder;

    private Boolean meituanOpened;

    private Boolean eleOpened;
    private Boolean wanteOpened;
    private Boolean jddjOpened;
    private Boolean clbmOpened;

    private Integer deductPoint;

    private Integer seCycle;

    private String lng;

    private String lat;

    private String psContactName;

    private String psContactPhone;

    private String bizStartTime;

    private String bizEndTime;

    private Integer mtpsCategory;

    private Integer mtpsSecondCategory;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    private String mtpsShopId;
    private String sfpsShopId;

    /**
     * 起送价
     */
    private Float minPrice;

    /**
     * 配送费
     */
    private Float shippingFee;

    public Integer getSeCycle() {
        return seCycle;
    }

    public void setSeCycle(Integer seCycle) {
        this.seCycle = seCycle;
    }

    public String getXyySn() {
        return xyySn;
    }

    public void setXyySn(String xyySn) {
        this.xyySn = xyySn;
    }

    public String getSfpsShopId() {
        return sfpsShopId;
    }

    public void setSfpsShopId(String sfpsShopId) {
        this.sfpsShopId = sfpsShopId;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Float shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getMtpsShopId() {
        return mtpsShopId;
    }

    public void setMtpsShopId(String mtpsShopId) {
        this.mtpsShopId = mtpsShopId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPsContactName() {
        return psContactName;
    }

    public void setPsContactName(String psContactName) {
        this.psContactName = psContactName;
    }

    public String getPsContactPhone() {
        return psContactPhone;
    }

    public void setPsContactPhone(String psContactPhone) {
        this.psContactPhone = psContactPhone;
    }

    public String getBizStartTime() {
        return bizStartTime;
    }

    public void setBizStartTime(String bizStartTime) {
        this.bizStartTime = bizStartTime;
    }

    public String getBizEndTime() {
        return bizEndTime;
    }

    public void setBizEndTime(String bizEndTime) {
        this.bizEndTime = bizEndTime;
    }

    public Integer getMtpsCategory() {
        return mtpsCategory;
    }

    public void setMtpsCategory(Integer mtpsCategory) {
        this.mtpsCategory = mtpsCategory;
    }

    public Integer getMtpsSecondCategory() {
        return mtpsSecondCategory;
    }

    public void setMtpsSecondCategory(Integer mtpsSecondCategory) {
        this.mtpsSecondCategory = mtpsSecondCategory;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Region getProvince() {
        return province;
    }

    public void setProvince(Region province) {
        this.province = province;
    }

    public Region getCity() {
        return city;
    }

    public void setCity(Region city) {
        this.city = city;
    }

    public Region getDistrict() {
        return district;
    }

    public void setDistrict(Region district) {
        this.district = district;
    }

    public Integer getDeductPoint() {
        return deductPoint;
    }

    public void setDeductPoint(Integer deductPoint) {
        this.deductPoint = deductPoint;
    }

    public Boolean getMeituanOpened() {
        return meituanOpened == null ? false : meituanOpened;
    }

    public void setMeituanOpened(Boolean meituanOpened) {
        this.meituanOpened = meituanOpened;
    }

    public Boolean getEleOpened() {
        return eleOpened == null ? false : eleOpened;
    }

    public void setEleOpened(Boolean eleOpened) {
        this.eleOpened = eleOpened;
    }

    public Boolean getWanteOpened() {
        return wanteOpened == null ? false : wanteOpened;
    }

    public void setWanteOpened(Boolean wanteOpened) {
        this.wanteOpened = wanteOpened;
    }

    public Boolean getJddjOpened() {
        return jddjOpened == null ? false : jddjOpened;
    }

    public void setJddjOpened(Boolean jddjOpened) {
        this.jddjOpened = jddjOpened;
    }

    public Boolean getClbmOpened() {
        return clbmOpened == null ? false : clbmOpened;
    }

    public void setClbmOpened(Boolean clbmOpened) {
        this.clbmOpened = clbmOpened;
    }

    public Boolean getAutoReceiptOrder() {
        return autoReceiptOrder;
    }

    public void setAutoReceiptOrder(Boolean autoReceiptOrder) {
        this.autoReceiptOrder = autoReceiptOrder;
    }

    public SettleType getSettleType() {
        return settleType;
    }

    public void setSettleType(SettleType settleType) {
        this.settleType = settleType;
    }

    public Employee getBizManager() {
        return bizManager;
    }

    public void setBizManager(Employee bizManager) {
        this.bizManager = bizManager;
    }

    public String getFeiePrinterSn() {
        return feiePrinterSn;
    }

    public void setFeiePrinterSn(String feiePrinterSn) {
        this.feiePrinterSn = feiePrinterSn;
    }

    public Boolean getOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getLastSettlementDate() {
        return lastSettlementDate;
    }

    public void setLastSettlementDate(Date lastSettlementDate) {
        this.lastSettlementDate = lastSettlementDate;
    }

    public Float getPriceIncrease() {
        return priceIncrease;
    }

    public void setPriceIncrease(Float priceIncrease) {
        this.priceIncrease = priceIncrease;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StoreUserStatus getStatus() {
        return status;
    }

    public void setStatus(StoreUserStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Employee getBizManager2() {
        return bizManager2;
    }

    public void setBizManager2(Employee bizManager2) {
        this.bizManager2 = bizManager2;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getDevideid() {
        return devideid;
    }

    public void setDevideid(String devideid) {
        this.devideid = devideid;
    }

    public String getDevicesecret() {
        return devicesecret;
    }

    public void setDevicesecret(String devicesecret) {
        this.devicesecret = devicesecret;
    }
}
