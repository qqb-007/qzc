package info.batcloud.wxc.admin.repository;

import info.batcloud.wxc.admin.entity.Manager;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ManagerRepository extends PagingAndSortingRepository<Manager, Long>, JpaSpecificationExecutor<Manager> {

    Manager findByUsernameAndDeleted(String username, boolean deleted);

}
