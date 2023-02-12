package info.batcloud.wxc.core.service;

import com.ctospace.archit.common.pagination.Paging;
import com.ctospace.archit.common.pagination.PagingParam;
import info.batcloud.wxc.core.dto.OrderDetailDTO;
import info.batcloud.wxc.core.entity.OrderDetail;
import info.batcloud.wxc.core.enums.OrderStatus;
import info.batcloud.wxc.core.enums.OrderStatusType;
import info.batcloud.wxc.core.enums.Plat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public interface OrderDetailService {

    Paging<OrderDetailDTO> search(SearchParam param);

    void bindFood(long id, FoodBindParam param);

    OrderDetailDTO toDetailDTO(OrderDetail orderDetail);
    OrderDetailDTO toDetailDTO(OrderDetail orderDetail, boolean fetchOrder);

    class FoodBindParam {
        private Long foodId;
        private String skuId;

        public Long getFoodId() {
            return foodId;
        }

        public void setFoodId(Long foodId) {
            this.foodId = foodId;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }
    }

    class SearchParam extends PagingParam {
        private String platOrderId;
        private Plat plat;
        private OrderStatus status;
        private Integer daySeq;
        private Long storeId;
        private Long storeUserId;
        private Long orderId;
        private List<Long> orderIdList;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date date;
        private OrderStatusType statusType;
        private Long foodSupplierId;
        private boolean pageable = true;
        private boolean fetchOrder;

        public boolean isFetchOrder() {
            return fetchOrder;
        }

        public void setFetchOrder(boolean fetchOrder) {
            this.fetchOrder = fetchOrder;
        }

        public boolean isPageable() {
            return pageable;
        }

        public void setPageable(boolean pageable) {
            this.pageable = pageable;
        }

        public List<Long> getOrderIdList() {
            return orderIdList;
        }

        public void setOrderIdList(List<Long> orderIdList) {
            this.orderIdList = orderIdList;
        }

        public Long getFoodSupplierId() {
            return foodSupplierId;
        }

        public void setFoodSupplierId(Long foodSupplierId) {
            this.foodSupplierId = foodSupplierId;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public OrderStatusType getStatusType() {
            return statusType;
        }

        public void setStatusType(OrderStatusType statusType) {
            this.statusType = statusType;
        }

        public Long getStoreUserId() {
            return storeUserId;
        }

        public void setStoreUserId(Long storeUserId) {
            this.storeUserId = storeUserId;
        }

        public Long getStoreId() {
            return storeId;
        }

        public void setStoreId(Long storeId) {
            this.storeId = storeId;
        }

        public String getPlatOrderId() {
            return platOrderId;
        }

        public void setPlatOrderId(String platOrderId) {
            this.platOrderId = platOrderId;
        }

        public Plat getPlat() {
            return plat;
        }

        public void setPlat(Plat plat) {
            this.plat = plat;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public void setStatus(OrderStatus status) {
            this.status = status;
        }

        public Integer getDaySeq() {
            return daySeq;
        }

        public void setDaySeq(Integer daySeq) {
            this.daySeq = daySeq;
        }
    }

}
