package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.FoodSku;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FoodSkuRepository extends PagingAndSortingRepository<FoodSku, Long>, JpaSpecificationExecutor<FoodSku> {

    List<FoodSku> findByFoodId(long foodId);

    int countByFoodIdAndName(long foodId, String name);

    int countByUpc(String upc);

    FoodSku findByUpc(String upc);

}
