package info.batcloud.wxc.admin.repository;

import info.batcloud.wxc.admin.entity.ManagerRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ManagerRoleRepository extends CrudRepository<ManagerRole, Long> {

    List<ManagerRole> findByManagerId(long managerId);

    void deleteByManagerId(long managerId);
}
