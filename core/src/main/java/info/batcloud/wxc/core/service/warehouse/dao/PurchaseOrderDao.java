package info.batcloud.wxc.core.service.warehouse.dao;

import info.batcloud.wxc.core.entity.PreShopProcurement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @ClassName: PurchaseOrderDao
 * @Description:
 * @Author V
 * @Date 14/2/2023
 * @Version 1.0
 */
@Mapper
public interface PurchaseOrderDao extends PagingAndSortingRepository<PreShopProcurement,Long>, JpaSpecificationExecutor<PreShopProcurement> {
}
