package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.SettlementSheetDetailDTO;
import info.batcloud.wxc.core.enums.SettlementSheetDetailStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public interface SettlementSheetDetailService {

    Paging<SettlementSheetDetailDTO> search(SearchParam param);

    class SearchParam extends PagingParam {
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date date;
        private SettlementSheetDetailStatus status;
        private Long storeUserId;
        private String storeUserName;
        private Integer year;
        private Integer week;

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getWeek() {
            return week;
        }

        public void setWeek(Integer week) {
            this.week = week;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public SettlementSheetDetailStatus getStatus() {
            return status;
        }

        public void setStatus(SettlementSheetDetailStatus status) {
            this.status = status;
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
