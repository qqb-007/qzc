package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.StoreFoodApplicationDTO;
import info.batcloud.wxc.core.enums.StoreFoodApplicationStatus;

public interface StoreFoodApplicationService {


    StoreFoodApplicationDTO apply(ApplyParam param);

    Paging<StoreFoodApplicationDTO> search(SearchParam param);

    void setStatusById(long id, StoreFoodApplicationStatus status);

    class SearchParam extends PagingParam {
        private StoreFoodApplicationStatus status;
        private String storeUserName;
        private Long storeUserId;

        public StoreFoodApplicationStatus getStatus() {
            return status;
        }

        public void setStatus(StoreFoodApplicationStatus status) {
            this.status = status;
        }

        public String getStoreUserName() {
            return storeUserName;
        }

        public void setStoreUserName(String storeUserName) {
            this.storeUserName = storeUserName;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }
    }

    class ApplyParam {
        private Long storeUserId;
        private Long foodSupplierId;

        private String foodName;

        private String unit;

        private Float price;

        private String foodPicture;

        public Long getFoodSupplierId() {
            return foodSupplierId;
        }

        public void setFoodSupplierId(Long foodSupplierId) {
            this.foodSupplierId = foodSupplierId;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public String getFoodPicture() {
            return foodPicture;
        }

        public void setFoodPicture(String foodPicture) {
            this.foodPicture = foodPicture;
        }
    }

}
