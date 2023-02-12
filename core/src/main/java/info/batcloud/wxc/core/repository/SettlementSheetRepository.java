package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.SettlementSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface SettlementSheetRepository extends PagingAndSortingRepository<SettlementSheet, Long>, JpaSpecificationExecutor<SettlementSheet> {

    SettlementSheet findTopByStoreUserIdOrderByIdDesc(long storeUserId);

    SettlementSheet findByStoreUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndSettledFalse(long storeUserId, Date startDate, Date endDate);

    @Override
    @EntityGraph(value = "StoreUser.Graph", type = EntityGraph.EntityGraphType.FETCH)
    Page<SettlementSheet> findAll(Specification<SettlementSheet> spec, Pageable pageable);

}
