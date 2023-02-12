package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.domain.FoodQuoteInfo;
import info.batcloud.wxc.core.domain.FoodQuoteSku;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.FoodQuoteSheetDTO;
import info.batcloud.wxc.core.enums.FoodQuoteSheetStatus;
import info.batcloud.wxc.core.enums.PublishStatus;

import java.util.List;

public interface FoodQuoteSheetService {

    long addFoodQuoteSheet(FoodQuoteSheetAddParam param);

    FoodQuoteSheetDTO findById(long id);

    void rejectFoodQuoteSheet(long id, String remark);

    Paging<FoodQuoteSheetDTO> search(SearchParam param);

    Result verifyFoodQuoteSheet(long id, VerifyParam param);

    void retrialFoodQuoteSheet(long id);

    Result publishToStore(long id);

    long countWaitVerifyNum();

    class VerifyParam {
        private List<FoodQuoteSheetDetailVerifyParam> detailList;

        public List<FoodQuoteSheetDetailVerifyParam> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<FoodQuoteSheetDetailVerifyParam> detailList) {
            this.detailList = detailList;
        }
    }

    class FoodQuoteSheetDetailVerifyParam {
        private long id;
        private float price;
        private float salePrice;
        private String foodName;
        private List<FoodQuoteSku> skuList;

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(float salePrice) {
            this.salePrice = salePrice;
        }

        public List<FoodQuoteSku> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<FoodQuoteSku> skuList) {
            this.skuList = skuList;
        }
    }

    class FoodSkuVerifyParam {
        private String skuId;
        private float price;

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }

    class SearchParam extends PagingParam {
        private Long storeUserId;
        private FoodQuoteSheetStatus status;
        private PublishStatus publishStatus;

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public FoodQuoteSheetStatus getStatus() {
            return status;
        }

        public void setStatus(FoodQuoteSheetStatus status) {
            this.status = status;
        }

        public PublishStatus getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(PublishStatus publishStatus) {
            this.publishStatus = publishStatus;
        }
    }

    class FoodQuoteSheetAddParam {
        private Long storeUserId;

        private List<FoodQuoteInfo> foodQuoteList;

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public List<FoodQuoteInfo> getFoodQuoteList() {
            return foodQuoteList;
        }

        public void setFoodQuoteList(List<FoodQuoteInfo> foodQuoteList) {
            this.foodQuoteList = foodQuoteList;
        }
    }

}
