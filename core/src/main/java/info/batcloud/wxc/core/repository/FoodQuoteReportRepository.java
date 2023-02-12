package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.FoodQuoteReport;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FoodQuoteReportRepository extends PagingAndSortingRepository<FoodQuoteReport, Long>, JpaSpecificationExecutor<FoodQuoteReport> {

    List<FoodQuoteReport> findByFoodIdIn(List<Long> foodIdList);
}
