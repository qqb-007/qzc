package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.WarehouseDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public interface WarehouseService {

    void create(AddParam addParam);

    void update(UpdateParam updateParam);


    void bindStoreUserFoodSku(long id, long storeUserFoodSkuId);


    void deleteFoodSku(long id, long storeUserFoodSkuId);


    void deleteWarehouse(Long id);

    Paging<WarehouseDTO> search(SearchParam param);

//    List<WarehouseDTO> getsufWhList(Long sufId);

    List<WarehouseDTO> getskuWhList(Long skuId);


    class SearchParam extends PagingParam {
        private String name;
        private Long storeUserId;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createStartTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createEndTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date createStartDate;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date createEndDate;


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

        public Date getCreateStartTime() {
            return createStartTime;
        }

        public void setCreateStartTime(Date createStartTime) {
            this.createStartTime = createStartTime;
        }

        public Date getCreateEndTime() {
            return createEndTime;
        }

        public void setCreateEndTime(Date createEndTime) {
            this.createEndTime = createEndTime;
        }

        public Date getCreateStartDate() {
            return createStartDate;
        }

        public void setCreateStartDate(Date createStartDate) {
            this.createStartDate = createStartDate;
        }

        public Date getCreateEndDate() {
            return createEndDate;
        }

        public void setCreateEndDate(Date createEndDate) {
            this.createEndDate = createEndDate;
        }
    }


    class UpdateParam {
        private Long id;
        private String name;
        private String address;

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
    }


    class AddParam {
        private Long storeUserId;
        private String name;
        private String address;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
