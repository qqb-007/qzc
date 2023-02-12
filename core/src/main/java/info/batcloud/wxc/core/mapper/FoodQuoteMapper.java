package info.batcloud.wxc.core.mapper;

import info.batcloud.wxc.core.enums.FoodQuoteSheetStatus;
import info.batcloud.wxc.core.mapper.domain.FoodPrice;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface FoodQuoteMapper {

    List<FoodPrice> findStoreUserNewestFoodQuotePrice(FoodQuotePriceParam param);

    class FoodQuotePriceParam {
        private Long storeUserId;
        private Long foodQuoteSheetId;
        private Date endTime;
        private FoodQuoteSheetStatus status;

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Long getFoodQuoteSheetId() {
            return foodQuoteSheetId;
        }

        public void setFoodQuoteSheetId(Long foodQuoteSheetId) {
            this.foodQuoteSheetId = foodQuoteSheetId;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public FoodQuoteSheetStatus getStatus() {
            return status;
        }

        public void setStatus(FoodQuoteSheetStatus status) {
            this.status = status;
        }
    }

}
