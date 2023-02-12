package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface WarehouseRepository extends PagingAndSortingRepository<Warehouse, Long>, JpaSpecificationExecutor<Warehouse> {
    List<Warehouse> findByStoreUserId(long storeUserId);

    int countByStoreUserIdAndName(long storeUserId, String name);

    Warehouse findByStoreUserIdAndName(long storeUserId, String name);
}
