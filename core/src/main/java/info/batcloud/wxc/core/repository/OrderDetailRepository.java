package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {

    List<OrderDetail> findByOrderId(long orderId);
    List<OrderDetail> findByOrderIdIn(List<Long> orderIdList);
    List<OrderDetail> findByOrderIdInAndFoodSupplierId(List<Long> orderIdList, long foodSupplierId);

}
