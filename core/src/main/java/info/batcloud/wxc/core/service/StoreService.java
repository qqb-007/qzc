package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.StoreDTO;
import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.entity.Store;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.enums.UuAccountType;

import java.util.List;

public interface StoreService {

    boolean closeDeliverySelf(long id);

    boolean openDeliverySelf(long id);

    boolean openShow(long id);

    boolean closeShow(long id);

    boolean openLineStore(long id);

    boolean closeLineStore(long id);

    boolean toggleUuMix(long id);

    void updateUuTime(long id, float time);

    void updateStorePic(String id, String imageUrl);

    void updateOpenStatus(String code, Plat plat, Boolean open);

    boolean updateDeliveryArea(long id, String area);

    void publishCategoryToAll(FoodCategory foodCategory);

    void updateCategoryToAll(FoodCategory foodCategory, String originName);

    void deleteCategoryToAll(FoodCategory foodCategory);

    boolean syncFromMeituan(String poiCodes);

    boolean syncFromClbm(String poiCodes);

    boolean syncListFromEle(Integer sysStatus);

    boolean syncFromEle(String shopId);

    boolean syncFromWante(String shopId);

    boolean syncFromJddj(String shopId, Boolean jd);

    void bindStoreUser(long id, long storeUserId);

    void setPriceIncrease(long id, float priceIncrease);

    void setStoreDeliveryType(long id, DeliveryType type);

    void setStoreSecendDeliveryType(long id, DeliveryType type);

    void setStoreUuAccountType(long id, UuAccountType type);

    boolean updateDeliveryAreaById(long id);

    Paging<StoreDTO> search(SearchParam param);

    StoreDTO toDTO(Store store);

    StoreDTO toDTO(Store store, boolean fetchStoreUser);

    void openStore(long id);

    void closeStore(long id);

    String findPrinterSnByStoreCode(String code, Plat plat);

    class SearchParam extends PagingParam {
        private String phone;
        private String name;
        private Plat plat;
        private Long storeUserId;
        private Boolean opening;
        private DeliveryType deliveryType;
        private Boolean lineOpen;
        private Boolean showButton;
        private Boolean deliverySelf;
        private Integer closeDayNum;

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

        public Boolean getLineOpen() {
            return lineOpen;
        }

        public void setLineOpen(Boolean lineOpen) {
            this.lineOpen = lineOpen;
        }

        public Integer getCloseDayNum() {
            return closeDayNum;
        }

        public void setCloseDayNum(Integer closeDayNum) {
            this.closeDayNum = closeDayNum;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }
    }
}
