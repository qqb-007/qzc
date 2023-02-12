package info.batcloud.wxc.admin.repository;

import info.batcloud.wxc.admin.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findByValid(boolean valid);
    List<Role> findByIdInAndValid(List<Long> ids, boolean valid);

}
