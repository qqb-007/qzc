package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.StoreUserFoodSku;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StoreUserFoodSkuRepository extends PagingAndSortingRepository<StoreUserFoodSku, Long>, JpaSpecificationExecutor<StoreUserFoodSku> {

    List<StoreUserFoodSku> findByStoreUserFoodId(long sufId);

    StoreUserFoodSku findByStoreUserIdAndUpc(long stoerUserId, String upc);

    List<StoreUserFoodSku> findByFoodSkuId(long foodSkuId);

    StoreUserFoodSku findByStoreUserFoodIdAndFoodSkuId(long storeUserId, long foodSkuId);

    void deleteByStoreUserFoodId(long sfuId);
}
