package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.StoreUserDTO;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.SettleType;
import info.batcloud.wxc.core.enums.StoreUserStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface StoreUserService extends UserDetailsService {

    void createDDShop(Long storeUserId);

    void createSSShop(Long storeUserId);

    void createUUShop(Long storeUserId);

    void deleteSms(Long storeUserId);

    void getNewYlyToken();

    StoreUserDTO findByPhone(String username);

    StoreUserDTO findById(Long id);

    void modifyPassword(long id, String originPassword, String password);

    boolean checkPassword(StoreUser storeUser, String password);

    void saveStoreUser(StoreUser storeUser);

    void updateStoreUser(long id, StoreUserUpdateParam param);

    void setStoreUserDeliveryType(long id, DeliveryType type);

    void lockStoreUser(long id);

    void unLockStoreUser(long id);

    void modifyPassword(long id, String password);

    Paging<StoreUserDTO> search(SearchParam param);

    StoreUserDTO toDTO(StoreUser storeUser);

    boolean toggleAutoReceiptOrderById(long id);

    boolean isAutoReceiptOrderById(long id);

    void deleteById(long id);

    void setStoreUserBizManager(long storeUserId, long employeeId);

    void setStoreUserBizManager2(long storeUserId, long employeeId);

    void updateStoreUserBank(long storeUserId, String bankName, String bankAccount, String bankAccountName);

    void changeBizManager(long originBizManagerId, long currentBizManagerId);

    class StoreUserUpdateParam {
        private Integer seCycle;
        private String name;
        private String phone;
        private String address;
        private float priceIncrease;
        private boolean opened;
        private SettleType settleType;
        private boolean meituanOpened;
        private boolean eleOpened;
        private boolean wanteOpened;
        private boolean jddjOpened;
        private boolean clbmOpened;
        private int deductPoint;
        private long provinceId;
        private long cityId;
        private long districtId;
        private String mtpsShopId;
        private String sfpsShopId;

        /**
         * 起送价
         */
        private Float minPrice;

        public Integer getSeCycle() {
            return seCycle;
        }

        public void setSeCycle(Integer seCycle) {
            this.seCycle = seCycle;
        }

        /**
         * 配送费
         */
        private Float shippingFee;

        private String lng;

        private String lat;

        private String psContactName;

        private String psContactPhone;

        private String bizStartTime;

        private String bizEndTime;

        private Integer mtpsCategory;

        private Integer mtpsSecondCategory;

        public boolean isClbmOpened() {
            return clbmOpened;
        }

        public void setClbmOpened(boolean clbmOpened) {
            this.clbmOpened = clbmOpened;
        }

        public String getSfpsShopId() {
            return sfpsShopId;
        }

        public void setSfpsShopId(String sfpsShopId) {
            this.sfpsShopId = sfpsShopId;
        }

        public boolean isJddjOpened() {
            return jddjOpened;
        }

        public void setJddjOpened(boolean jddjOpened) {
            this.jddjOpened = jddjOpened;
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

        public long getDistrictId() {
            return districtId;
        }

        public void setDistrictId(long districtId) {
            this.districtId = districtId;
        }

        public long getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(long provinceId) {
            this.provinceId = provinceId;
        }

        public long getCityId() {
            return cityId;
        }

        public void setCityId(long cityId) {
            this.cityId = cityId;
        }

        public int getDeductPoint() {
            return deductPoint;
        }

        public void setDeductPoint(int deductPoint) {
            this.deductPoint = deductPoint;
        }

        public boolean isMeituanOpened() {
            return meituanOpened;
        }

        public void setMeituanOpened(boolean meituanOpened) {
            this.meituanOpened = meituanOpened;
        }

        public boolean isEleOpened() {
            return eleOpened;
        }

        public void setEleOpened(boolean eleOpened) {
            this.eleOpened = eleOpened;
        }

        public boolean isWanteOpened() {
            return wanteOpened;
        }

        public void setWanteOpened(boolean wanteOpened) {
            this.wanteOpened = wanteOpened;
        }

        public SettleType getSettleType() {
            return settleType;
        }

        public void setSettleType(SettleType settleType) {
            this.settleType = settleType;
        }

        public boolean isOpened() {
            return opened;
        }

        public void setOpened(boolean opened) {
            this.opened = opened;
        }

        public float getPriceIncrease() {
            return priceIncrease;
        }

        public void setPriceIncrease(float priceIncrease) {
            this.priceIncrease = priceIncrease;
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
    }

    class SearchParam extends PagingParam {
        private String name;
        private String phone;
        private String code;
        private StoreUserStatus status;
        private Boolean autoReceiptOrder;
        private Boolean opened;
        private Long cityId;
        private Long provinceId;
        private Long districtId;

        private Integer seCycle;

        private SettleType settleType;

        private float priceIncrease;

        private Boolean meituanOpened;
        private Boolean eleOpened;
        private Boolean wanteOpened;
        private Boolean jddjOpened;
        private Boolean clbmOpened;

        public Integer getSeCycle() {
            return seCycle;
        }

        public void setSeCycle(Integer seCycle) {
            this.seCycle = seCycle;
        }

        public Boolean getClbmOpened() {
            return clbmOpened;
        }

        public void setClbmOpened(Boolean clbmOpened) {
            this.clbmOpened = clbmOpened;
        }

        public Boolean getJddjOpened() {
            return jddjOpened;
        }

        public void setJddjOpened(Boolean jddjOpened) {
            this.jddjOpened = jddjOpened;
        }

        public Boolean getWanteOpened() {
            return wanteOpened;
        }

        public void setWanteOpened(Boolean wanteOpened) {
            this.wanteOpened = wanteOpened;
        }

        public Long getCityId() {
            return cityId;
        }

        public void setCityId(Long cityId) {
            this.cityId = cityId;
        }

        public Long getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Long provinceId) {
            this.provinceId = provinceId;
        }

        public Long getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Long districtId) {
            this.districtId = districtId;
        }

        public Boolean getMeituanOpened() {
            return meituanOpened;
        }

        public void setMeituanOpened(Boolean meituanOpened) {
            this.meituanOpened = meituanOpened;
        }

        public Boolean getEleOpened() {
            return eleOpened;
        }

        public void setEleOpened(Boolean eleOpened) {
            this.eleOpened = eleOpened;
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

        public Boolean getOpened() {
            return opened;
        }

        public void setOpened(Boolean opened) {
            this.opened = opened;
        }

        public float getPriceIncrease() {
            return priceIncrease;
        }

        public void setPriceIncrease(float priceIncrease) {
            this.priceIncrease = priceIncrease;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public StoreUserStatus getStatus() {
            return status;
        }

        public void setStatus(StoreUserStatus status) {
            this.status = status;
        }
    }
}
