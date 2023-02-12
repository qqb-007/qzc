package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.OrderNotification;
import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.Plat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderNotificationRepository extends PagingAndSortingRepository<OrderNotification, Long>, JpaSpecificationExecutor<OrderNotification> {

    OrderNotification findByPlatAndPlatOrderId(Plat plat, String platOrderId);

    List<OrderNotification> findByTypeAndSuccess(OrderNotificationType type, Boolean success);

}
