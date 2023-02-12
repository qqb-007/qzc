package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.Store;
import info.batcloud.wxc.core.enums.Plat;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StoreRepository extends PagingAndSortingRepository<Store, Long>, JpaSpecificationExecutor<Store> {

    Store findByCodeAndPlat(String code, Plat plat);

    List<Store> findByStoreUserId(long id);

    Store findByStoreUserIdAndPlat(long id, Plat plat);

    List<Store> findByStoreUserIdAndIsOnlineTrue(long id);

    List<Store> findByStoreUserIdAndOpeningTrueAndPlatIn(long id, List<Plat> platList);

    List<Store> findByStoreUserIdAndOpeningTrue(long id);

    List<Store> findByStoreUserIdAndOpeningTrueAndPlat(long id, Plat plat);

    List<Store> findByOpeningTrueAndPlat(Plat plat);

    List<Store> findByPlat(Plat plat);

    List<Store> findByIsOnlineTrue();
}
