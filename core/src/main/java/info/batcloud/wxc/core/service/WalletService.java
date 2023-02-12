package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.domain.Result;
import info.batcloud.wxc.core.dto.WalletDTO;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.enums.WalletSort;

public interface WalletService {

    WalletChangeResult addMoney(long storeUserId, float money, WalletFlowDetailType flowDetailType, String... context);

    WalletDTO findByStoreUserId(long storeUserId);

    WalletConsumeResult consumeMoney(long userId, float money, WalletFlowDetailType flowDetailType, String... context);

    Paging<WalletDTO> search(SearchParam param);

    class WalletConsumeResult extends Result {

        private WalletChangeResult walletChangeResult;

        public WalletChangeResult getWalletChangeResult() {
            return walletChangeResult;
        }

        public void setWalletChangeResult(WalletChangeResult walletChangeResult) {
            this.walletChangeResult = walletChangeResult;
        }
    }

    class WalletChangeResult {
        private long walletDetailId;
        private WalletDTO wallet;

        public WalletDTO getWallet() {
            return wallet;
        }

        public void setWalletDTO(WalletDTO wallet) {
            this.wallet = wallet;
        }

        public long getWalletDetailId() {
            return walletDetailId;
        }

        public void setWalletDetailId(long walletDetailId) {
            this.walletDetailId = walletDetailId;
        }
    }

    class SearchParam extends PagingParam {

        private Long storeUserId;
        private String storeUserPhone;
        private String storeUserName;
        private WalletSort sort;

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public String getStoreUserPhone() {
            return storeUserPhone;
        }

        public void setStoreUserPhone(String storeUserPhone) {
            this.storeUserPhone = storeUserPhone;
        }

        public String getStoreUserName() {
            return storeUserName;
        }

        public void setStoreUserName(String storeUserName) {
            this.storeUserName = storeUserName;
        }

        public WalletSort getSort() {
            return sort;
        }

        public void setSort(WalletSort sort) {
            this.sort = sort;
        }
    }
}
