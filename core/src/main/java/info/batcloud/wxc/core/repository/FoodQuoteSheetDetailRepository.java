package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.FoodQuoteSheetDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FoodQuoteSheetDetailRepository extends PagingAndSortingRepository<FoodQuoteSheetDetail, Long>, JpaSpecificationExecutor<FoodQuoteSheetDetail> {

    List<FoodQuoteSheetDetail> findByFoodQuoteSheetId(long foodQuoteSheetId);

}
