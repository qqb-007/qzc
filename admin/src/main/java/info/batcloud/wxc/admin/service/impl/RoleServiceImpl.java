package info.batcloud.wxc.admin.service.impl;

import info.batcloud.wxc.admin.domain.PermissionTreeNode;
import info.batcloud.wxc.admin.entity.Role;
import info.batcloud.wxc.admin.entity.RolePermission;
import info.batcloud.wxc.admin.repository.RolePermissionRepository;
import info.batcloud.wxc.admin.repository.RoleRepository;
import info.batcloud.wxc.admin.service.PermissionService;
import info.batcloud.wxc.admin.service.RoleService;
import info.batcloud.wxc.core.constants.CacheNameConstants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = CacheNameConstants.MANAGER_PERMISSION)
public class RoleServiceImpl implements RoleService {

    @Inject
    private RoleRepository roleRepository;

    @Inject
    private PermissionService permissionService;

    @Inject
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(role -> roles.add(role));
        return roles;
    }

    @Override
    public void save(RoleAddParam param) {
        Role role = new Role();
        role.setName(param.getName());
        role.setValid(param.getValid());
        role.setUpdateTime(new Date());
        roleRepository.save(role);
    }

    @Override
    @CacheEvict(key = "'ROLE_PERMISSIONS_' + #param.getId()")
    public void update(RoleUpdateParam param) {
        Role role = roleRepository.findOne(param.getId());
        role.setName(param.getName());
        role.setValid(param.getValid());
        role.setUpdateTime(new Date());
        roleRepository.save(role);
    }

    @Override
    @CacheEvict(key = "'ROLE_PERMISSIONS_' + #id")
    public void deleteById(long id) {
        roleRepository.delete(id);
    }

    @Override
    public List<PermissionTreeNode> permissionTree(long roleId) {
        return permissionService.permissionTree(rolePermissionRepository.findByRoleId(roleId).stream().map(rp -> rp.getPermission()).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    @CacheEvict(key = "'ROLE_PERMISSIONS_' + #roleId")
    public void updatePermissions(long roleId, List<String> permissions) {
        rolePermissionRepository.deleteByRoleId(roleId);
        for (String permission : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permission);
            rolePermission.setRoleId(roleId);
            rolePermissionRepository.save(rolePermission);
        }
    }

    @Override
    @Cacheable(key = "'ROLE_PERMISSIONS_' + #roleId")
    public List<String> findRolePermission(long roleId) {
        return rolePermissionRepository.findByRoleId(roleId).stream().map(rp -> rp.getPermission()).collect(Collectors.toList());
    }
}
