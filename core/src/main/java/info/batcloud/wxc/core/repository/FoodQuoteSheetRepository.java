package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.FoodQuoteSheet;
import info.batcloud.wxc.core.enums.FoodQuoteSheetStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FoodQuoteSheetRepository extends PagingAndSortingRepository<FoodQuoteSheet, Long>, JpaSpecificationExecutor<FoodQuoteSheet> {

    long countByStatus(FoodQuoteSheetStatus status);

}
