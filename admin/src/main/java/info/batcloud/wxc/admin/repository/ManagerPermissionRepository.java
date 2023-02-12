package info.batcloud.wxc.admin.repository;

import info.batcloud.wxc.admin.entity.ManagerPermission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ManagerPermissionRepository extends CrudRepository<ManagerPermission, Long> {

    int countByManagerIdAndPermission(long managerId, String permission);
    List<ManagerPermission> findByManagerId(long managerId);
}
