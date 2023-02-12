package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.Order;
import info.batcloud.wxc.core.enums.*;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    int countByPlatAndPlatOrderId(Plat plat, String platOrderId);

    List<Order> findByStatus(OrderStatus status);

    Order findByDdPeisongId(String ddOrderId);

    int countByStoreIdInAndRefundStatusAndStatusNotIn(List<Long> storeIds, OrderRefundStatus refundStatus, List<OrderStatus> status);

    List<Order> findByStatusAndCreateTimeBetween(OrderStatus orderStatus, Date start, Date end);

    List<Order> findByStatusAndUpdateTimeBetweenAndPlatInAndPreparationIsNull(OrderStatus orderStatus, Date start, Date end, List<Plat> plats);

    List<Order> findByStatusAndUpdateTimeBetweenAndDeliveryTypeAndDeliveryStatus(OrderStatus orderStatus, Date start, Date end, DeliveryType deliveryType, DeliveryStatus deliveryStatus);

    List<Order> findByPlatAndDeliveryStatusAndExpectedDeliveryTimeBetweenAndStatusIn(Plat plat, DeliveryStatus deliveryStatus, long minExpectedDeliveryTime, long maxExpectedDeliveryTime, OrderStatus... status);

    List<Order> findByStatusAndDeliveryTypeAndDeliveryStatus(OrderStatus status, DeliveryType deliveryType, DeliveryStatus deliveryStatus);

    Order findByPlatAndPlatOrderId(Plat plat, String platOrderId);

    Order findBySfPeisongId(String sfOrderId);

    Order findBySsPeisongId(String ssOrderId);

    Order findByUuPeisongId(String uuPeisongId);

    List<Order> findByStatusAndDeliveryStatusInAndDeliveryType(OrderStatus status, List<DeliveryStatus> statuses, DeliveryType deliveryTypes);

    List<Order> findByStatusAndDeliveryStatusInAndDeliveryTypeIn(OrderStatus status, List<DeliveryStatus> statuses, List<DeliveryType> deliveryTypes);

    List<Order> findByIdIn(List<Long> idList);

    List<Order> findBySettlementSheetDetailId(long settlementSheetDetailId);

    List<Order> findTop20ByStatusNotOrderByIdDesc(OrderStatus status);

    @Modifying
    @Query(nativeQuery = true, value = "update wxc_order set status=?2 where id=?1")
    int updateStatusById(long id, String status);

    @Modifying
    @Query(nativeQuery = true, value = "update wxc_order set uu_peisong_id=?2 where id=?1")
    int updateUuIdById(long id, String UuPeisongId);

    @Modifying
    @Query(nativeQuery = true, value = "update wxc_order set ss_peisong_id=?2 where id=?1")
    int updateSsIdById(long id, String SsPeisongId);

    @Modifying
    @Query(nativeQuery = true, value = "update wxc_order set mt_peisong_id=?2, ps_delivery_id=?3 where id=?1")
    int updateHkIdById(long id, String MtPeisongId, String psDeliveryId);

//    @Modifying
//    @Query(nativeQuery = true, value = "update wxc_order set uu_peisong_id=?2 where id=?1")
//    int updateUuIdById(long id, String UuPeisongId);

//    @Modifying
//    @Query(nativeQuery = true, value = "update wxc_order set delivery_status=?2, delivery_type=?3 where id=?1")
//    int updateDeliveryById(long id, String deliveryStatus, String deliveryType);

    List<Order> findByCreateTimeBetweenAndStatusAndStoreIdInAndBizStatus(Date startTime, Date endTime, OrderStatus status, List<Long> storeIdList, OrderBizStatus bizStatus);

    List<Order> findByCreateTimeBetweenAndStatusAndStoreIdInAndBizStatusAndCansunGreaterThan(Date startTime, Date endTime, OrderStatus status, List<Long> storeIdList, OrderBizStatus bizStatus, Float cansun);

    List<Order> findByCreateTimeBetweenAndStoreIdIn(Date startTime, Date endTime, List<Long> storeIdList);

    List<Order> findByCreateTimeBetweenAndStatusIn(Date startTime, Date endTime, List<OrderStatus> statusList);

    List<Order> findByCreateTimeBetweenAndStatusNotIn(Date startTime, Date endTime, List<OrderStatus> statusList);

    List<Order> findByDeliveryStatusAndExpectedDeliveryTimeBetweenAndStatusIn(DeliveryStatus deliveryStatus, long minExpectedDeliveryTime, long maxExpectedDeliveryTime, OrderStatus... status);

    List<Order> findByPlatNotAndDeliveryStatusAndExpectedDeliveryTimeBetweenAndUpdateTimeLessThanAndStatusIn(Plat plat, DeliveryStatus deliveryStatus, long minExpectedDeliveryTime, long maxExpectedDeliveryTime, Date startTime, OrderStatus... status);

    int countByCreateTimeBetweenAndStoreIdInAndStatusIn(Date startTime, Date endTime, List<Long> storeIdList, List<OrderStatus> statusList);

    int countByCreateTimeBetweenAndSettlementSheetDetailIsNullAndStoreIdInAndBizStatusNotIn(Date startTime, Date endTime, List<Long> storeIdList, List<OrderBizStatus> bizStatus);
}
