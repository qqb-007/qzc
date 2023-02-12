package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.Bag;
import info.batcloud.wxc.core.enums.BagStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BagRepository extends PagingAndSortingRepository<Bag, Long>, JpaSpecificationExecutor<Bag> {
    List<Bag> findByStoreUserIdAndStatus(Long storeUserId, BagStatus status);
}
