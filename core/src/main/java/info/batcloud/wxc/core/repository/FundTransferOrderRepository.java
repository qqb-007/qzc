package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.FundTransferOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FundTransferOrderRepository extends PagingAndSortingRepository<FundTransferOrder, Long>, JpaSpecificationExecutor<FundTransferOrder> {
}
