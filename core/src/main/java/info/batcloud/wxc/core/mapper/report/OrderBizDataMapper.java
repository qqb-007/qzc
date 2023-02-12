package info.batcloud.wxc.core.mapper.report;

import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.mapper.report.domain.OrderBizData;
import info.batcloud.wxc.core.mapper.report.domain.StoreUserOrderBizData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderBizDataMapper {

    List<OrderBizData> findOrderBizDataByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    List<OrderBizData> findOrderBizDataByDeliveryDateRange(@Param("startDate") Long startDate, @Param("endDate") Long endDate);
    List<StoreUserOrderBizData> findStoreUserOrderBizDataByDateRange(@Param("param") StoreUserOrderBizDataParam param);

    class StoreUserOrderBizDataParam {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endDate;
        private Plat plat;
        private Long storeUserId;
        private String storeUserName;
        private String dimension;

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public String getStoreUserName() {
            return storeUserName;
        }

        public void setStoreUserName(String storeUserName) {
            this.storeUserName = storeUserName;
        }

    }
}
