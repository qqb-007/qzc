package info.batcloud.wxc.core.repository;

import info.batcloud.wxc.core.entity.StoreFoodApplication;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StoreFoodApplicationRepository extends PagingAndSortingRepository<StoreFoodApplication, Long>, JpaSpecificationExecutor<StoreFoodApplication> {
}
