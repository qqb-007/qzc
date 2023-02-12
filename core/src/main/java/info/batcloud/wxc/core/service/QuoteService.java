package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.domain.FoodQuoteInfo;
import info.batcloud.wxc.core.dto.FoodQuoteDTO;
import info.batcloud.wxc.core.mapper.FoodQuoteMapper;

import java.util.List;

public interface QuoteService {

    Paging<FoodQuoteDTO> searchFoodForQuote(FoodForQuoteSearchParam param);

    void storeUserQuoteFood(FoodQuoteParam param);

    class FoodQuoteParam {
        List<FoodQuoteInfo> foodQuoteInfoList;
        private long storeUserId;

        public long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public List<FoodQuoteInfo> getFoodQuoteInfoList() {
            return foodQuoteInfoList;
        }

        public void setFoodQuoteInfoList(List<FoodQuoteInfo> foodQuoteInfoList) {
            this.foodQuoteInfoList = foodQuoteInfoList;
        }
    }

    class FoodForQuoteSearchParam extends PagingParam {
        private Long storeUserId;
        private String foodName;
        private String categoryName;
        private List<Long> foodIdList;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<Long> getFoodIdList() {
            return foodIdList;
        }

        public void setFoodIdList(List<Long> foodIdList) {
            this.foodIdList = foodIdList;
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
    }

}
