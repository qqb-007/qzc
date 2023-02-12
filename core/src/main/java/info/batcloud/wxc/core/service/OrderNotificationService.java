package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.OrderNotificationDTO;
import info.batcloud.wxc.core.entity.OrderNotification;
import info.batcloud.wxc.core.enums.OrderNotificationSort;
import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.Plat;

public interface OrderNotificationService {

    void saveOrderNotification(Plat plat, OrderNotificationType type, String platOrderId, boolean success, String errMsg, Object data);

    Paging<OrderNotificationDTO> search(SearchParam param);

    void setSuccess(long id);

    class SearchParam extends PagingParam {
        private Plat plat;
        private String platOrderId;
        private Boolean success;
        private OrderNotificationType type;
        private OrderNotificationSort sort;

        public OrderNotificationSort getSort() {
            return sort;
        }

        public void setSort(OrderNotificationSort sort) {
            this.sort = sort;
        }

        public OrderNotificationType getType() {
            return type;
        }

        public void setType(OrderNotificationType type) {
            this.type = type;
        }

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }

        public String getPlatOrderId() {
            return platOrderId;
        }

        public void setPlatOrderId(String platOrderId) {
            this.platOrderId = platOrderId;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }
    }
}
