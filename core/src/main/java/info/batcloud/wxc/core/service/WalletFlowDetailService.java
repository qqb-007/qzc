package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.WalletFlowDetailDTO;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.enums.WalletValueType;

import java.io.IOException;
import java.util.Date;

public interface WalletFlowDetailService {

    Paging<WalletFlowDetailDTO> search(SearchParam param);

    String export(SearchParam param) throws IOException;

    class SearchParam extends PagingParam {
        private Long storeUserId;
        private WalletValueType valueType;
        private WalletFlowDetailType type;
        private Date createTime;

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public WalletValueType getValueType() {
            return valueType;
        }

        public void setValueType(WalletValueType valueType) {
            this.valueType = valueType;
        }

        public WalletFlowDetailType getType() {
            return type;
        }

        public void setType(WalletFlowDetailType type) {
            this.type = type;
        }
    }

}
