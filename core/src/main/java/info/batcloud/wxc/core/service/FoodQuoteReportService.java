package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.FoodQuoteReportDTO;
import info.batcloud.wxc.core.entity.FoodQuoteReport;

public interface FoodQuoteReportService {

    void generateByAllFood();

    void generateByFoodId(long foodId);

    void setRefPrice(long id, float refPrice);
    void setWarningPrice(long id, float warningPrice);

    Paging<FoodQuoteReportDTO> search(SearchParam param);

    class SearchParam extends PagingParam {
        private Long foodId;
        private String foodName;
        private Long provinceId;
        private Long cityId;

        public Long getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Long provinceId) {
            this.provinceId = provinceId;
        }

        public Long getCityId() {
            return cityId;
        }

        public void setCityId(Long cityId) {
            this.cityId = cityId;
        }

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }
    }

}
