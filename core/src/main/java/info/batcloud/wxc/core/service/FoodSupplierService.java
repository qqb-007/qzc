package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.FoodSupplierDTO;
import info.batcloud.wxc.core.entity.FoodSupplier;
import info.batcloud.wxc.core.enums.FoodSupplierPriceIncreaseType;
import info.batcloud.wxc.core.enums.FoodSupplierStatus;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface FoodSupplierService extends UserDetailsService {

    FoodSupplierDTO findByPhone(String phone);

    FoodSupplierDTO findById(Long id);

    void modifyPassword(long id, String originPassword, String password);

    boolean checkPassword(FoodSupplier foodSupplier, String password);

    void saveFoodSupplier(FoodSupplier foodSupplier);

    void updateFoodSupplier(long id, FoodSupplierUpdateParam param);

    void lockFoodSupplier(long id);

    void unLockFoodSupplier(long id);

    void modifyPassword(long id, String password);

    void setPriceIncreaseType(long id, FoodSupplierPriceIncreaseType type);

    Paging<FoodSupplierDTO> search(SearchParam param);

    FoodSupplierDTO toDTO(FoodSupplier foodSupplier);

    void deleteById(long id);

    class FoodSupplierUpdateParam {
        private String name;
        private String phone;
        private String address;
        private FoodSupplierPriceIncreaseType priceIncreaseType;

        public FoodSupplierPriceIncreaseType getPriceIncreaseType() {
            return priceIncreaseType;
        }

        public void setPriceIncreaseType(FoodSupplierPriceIncreaseType priceIncreaseType) {
            this.priceIncreaseType = priceIncreaseType;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

    }

    class SearchParam extends PagingParam {
        private String name;
        private String phone;
        private Long storeUserId;
        private FoodSupplierStatus status;
        private FoodSupplierPriceIncreaseType priceIncreaseType;

        public FoodSupplierPriceIncreaseType getPriceIncreaseType() {
            return priceIncreaseType;
        }

        public void setPriceIncreaseType(FoodSupplierPriceIncreaseType priceIncreaseType) {
            this.priceIncreaseType = priceIncreaseType;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
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

        public FoodSupplierStatus getStatus() {
            return status;
        }

        public void setStatus(FoodSupplierStatus status) {
            this.status = status;
        }

    }
}
