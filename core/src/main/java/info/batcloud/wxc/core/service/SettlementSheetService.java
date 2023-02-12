package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.SettlementSheetDTO;
import info.batcloud.wxc.core.enums.SettlementSheetStatus;

public interface SettlementSheetService {

    Paging<SettlementSheetDTO> search(SearchParam searchParam);

    Result settleToWallet(long settlementSheetId);

    SettlementSheetDTO findById(long id);

    class SearchParam extends PagingParam {
        private Long storeUserId;
        private String storeUserName;
        private Boolean settled;
        private boolean fetchDetails;
        private SettlementSheetStatus status;

        public SettlementSheetStatus getStatus() {
            return status;
        }

        public void setStatus(SettlementSheetStatus status) {
            this.status = status;
        }

        public boolean isFetchDetails() {
            return fetchDetails;
        }

        public void setFetchDetails(boolean fetchDetails) {
            this.fetchDetails = fetchDetails;
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

        public Boolean getSettled() {
            return settled;
        }

        public void setSettled(Boolean settled) {
            this.settled = settled;
        }
    }

}
