package info.batcloud.wxc.admin.repository;

import info.batcloud.wxc.admin.entity.RolePermission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RolePermissionRepository extends CrudRepository<RolePermission, Long> {

    int countByRoleIdInAndPermission(List<Long> roleIds, String permission);

    int deleteByRoleId(long roleId);

    List<RolePermission> findByRoleIdIn(List<Long> roleIdList);

    List<RolePermission> findByRoleId(long roleId);
}
