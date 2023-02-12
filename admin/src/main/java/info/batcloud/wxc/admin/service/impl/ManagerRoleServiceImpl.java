package info.batcloud.wxc.admin.service.impl;

import info.batcloud.wxc.admin.entity.ManagerRole;
import info.batcloud.wxc.admin.entity.Role;
import info.batcloud.wxc.admin.repository.ManagerRoleRepository;
import info.batcloud.wxc.admin.repository.RoleRepository;
import info.batcloud.wxc.admin.service.ManagerRoleService;
import info.batcloud.wxc.core.constants.CacheNameConstants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = CacheNameConstants.MANAGER_PERMISSION)
public class ManagerRoleServiceImpl implements ManagerRoleService {

    @Inject
    private ManagerRoleRepository managerRoleRepository;

    @Inject
    private RoleRepository roleRepository;

    @Override
    @Cacheable(key = "'MANAGER_ROLES_' + #managerId")
    public List<Role> findManagerRoles(long managerId) {
        List<ManagerRole> managerRoles = managerRoleRepository.findByManagerId(managerId);
        List<Role> roles = new ArrayList<>();
        if(managerRoles.size() == 0) {
            return roles;
        }
        List<Long> ids = managerRoles.stream().map(mr -> mr.getRoleId()).collect(Collectors.toList());
        return roleRepository.findByIdInAndValid(ids, true);
    }

    @Override
    @Transactional
    @CacheEvict(key = "'MANAGER_ROLES_' + #managerId")
    public void setManagerRoles(long managerId, List<Long> roleIds) {
        managerRoleRepository.deleteByManagerId(managerId);
        if(roleIds == null) {
            return;
        }
        for (Long roleId : roleIds) {
            ManagerRole mr = new ManagerRole();
            mr.setRoleId(roleId);
            mr.setManagerId(managerId);
            managerRoleRepository.save(mr);
        }
    }

    @Override
    public List<RoleInfo> listManagerRoles(long managerId) {
        List<ManagerRole> managerRoles = managerRoleRepository.findByManagerId(managerId);
        List<Long> ids = managerRoles.stream().map(mr -> mr.getRoleId()).collect(Collectors.toList());
        return toRoleInfoList(roleRepository.findByValid(true), ids);
    }

    private List<RoleInfo> toRoleInfoList(List<Role> roles, List<Long> existsRoleIds) {
        return roles.stream().map(r -> {
            RoleInfo ri = new RoleInfo();
            ri.setName(r.getName());
            ri.setId(r.getId());
            ri.setChecked(existsRoleIds.contains(r.getId()));
            return ri;
        }).collect(Collectors.toList());
    }
}
