package info.batcloud.wxc.admin.service.impl;

import info.batcloud.wxc.admin.domain.PermissionTreeNode;
import info.batcloud.wxc.admin.entity.Manager;
import info.batcloud.wxc.admin.entity.Role;
import info.batcloud.wxc.admin.entity.RolePermission;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.PermissionItem;
import info.batcloud.wxc.admin.repository.ManagerPermissionRepository;
import info.batcloud.wxc.admin.repository.ManagerRepository;
import info.batcloud.wxc.admin.repository.RolePermissionRepository;
import info.batcloud.wxc.admin.service.ManagerRoleService;
import info.batcloud.wxc.admin.service.PermissionService;
import info.batcloud.wxc.admin.service.RoleService;
import info.batcloud.wxc.core.constants.CacheNameConstants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = CacheNameConstants.MANAGER_PERMISSION)
public class PermissionServiceImpl implements PermissionService {

    @Inject
    private RolePermissionRepository rolePermissionRepository;

    @Inject
    private ManagerRoleService managerRoleService;

    @Inject
    private ManagerPermissionRepository managerPermissionRepository;

    @Inject
    private ManagerRepository managerRepository;

    @Inject
    private PermissionService permissionService;

    @Inject
    private RoleService roleService;

    private List<PermissionItem> ROOT = new ArrayList<>();

    private Map<String, ManagerPermissions> SETTING_PERMISSION_MAP = new HashMap<>();

    @PostConstruct
    public void init() {
        this.initRoot();
    }

    private void initRoot() {
        Map<String, PermissionItem> itemMap = new HashMap();
        Map<String, List<PermissionItem>> childrenMap = new HashMap();
        for (ManagerPermissions permissionItem : ManagerPermissions.values()) {
            itemMap.put(permissionItem.getId(), permissionItem);
            if(permissionItem.settingTypes() != null) {
                for (Class aClass : permissionItem.settingTypes()) {
                    SETTING_PERMISSION_MAP.put(aClass.getSimpleName().replace(".class", ""), permissionItem);
                }
            }
            if(childrenMap.containsKey(permissionItem.getId())){
                permissionItem.getChildren().addAll(childrenMap.remove(permissionItem.getId()));
            }
            if(permissionItem.getParentId() == null){
                this.ROOT.add(permissionItem);
                continue;
            }
            if(itemMap.containsKey(permissionItem.getParentId())){
                itemMap.get(permissionItem.getParentId()).addChild(permissionItem);
            } else {
                List<PermissionItem> children = childrenMap.get(permissionItem.getParentId());
                if(children == null){
                    children = new ArrayList();
                    childrenMap.put(permissionItem.getParentId(), children);
                }
                children.add(permissionItem);
            }
        }
    }

    @Override
    public boolean checkPermission(long managerId, String permission) {
        List<Role> roleList = managerRoleService.findManagerRoles(managerId);
        if(roleList.size() > 0) {
            for (Role role : roleList) {
                List<String> rolePermissions = roleService.findRolePermission(role.getId());
                if(rolePermissions.contains(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> findManagerPermissions(long managerId) {
        List<String> permissionList = new ArrayList<>();
//        List<ManagerPermission> managerPermissions = managerPermissionRepository.findByManagerId(managerId);
//        permissionList.addAll(managerPermissions.stream().map(mp -> mp.getPermission()).collect(Collectors.toList()));
        List<Role> roleList = managerRoleService.findManagerRoles(managerId);
        if(roleList.size() > 0) {
            List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleIdIn(roleList.stream().map(r -> r.getId())
                    .collect(Collectors.toList()));
            permissionList.addAll(rolePermissions.stream().map(rp -> rp.getPermission()).collect(Collectors.toList()));
        }
        return permissionList;
    }

    @Override
    public boolean checkAdminRole(long managerId) {
        Manager manager = managerRepository.findOne(managerId);
        return manager.isAdmin();
    }

    @Override
    @CacheEvict(key = CacheNameConstants.MANAGER_PERMISSION, allEntries = true)
    public int clearRolePermission(long roleId) {
        return rolePermissionRepository.deleteByRoleId(roleId);
    }

    @Override
    @CacheEvict(key = "'MANAGER_PERMISSIONS_' + #managerId", allEntries = true)
    public int clearManagerPermission(long managerId) {
        return 0;
    }

    @Override
    public List<PermissionTreeNode> permissionTree(List<String> permissions) {
        List<PermissionTreeNode> treeNodes = new ArrayList();
        ROOT.forEach((item) -> treeNodes.add(walkPermissionItem(permissions, item, null)));
        return treeNodes;
    }

    @Override
    public boolean checkSettingPermission(long managerId, String setting) {
        PermissionItem permissionItem = this.SETTING_PERMISSION_MAP.get(setting);
        return checkPermission(managerId, permissionItem.getId());
    }

    @Override
    public ManagerPermissions getSettingPermission(String setting) {
        return this.SETTING_PERMISSION_MAP.get(setting);
    }

    private PermissionTreeNode walkPermissionItem(List<String> permissions,
                                                  PermissionItem item, PermissionTreeNode parentTreeNode) {
        PermissionTreeNode treeNode = new PermissionTreeNode(item.getId(), item.getName(), permissions.contains(item.getId()));
        if (parentTreeNode != null) {
            parentTreeNode.getChildren().add(treeNode);
        }
        if (item.getChildren() != null && item.getChildren().size() > 0) {
            treeNode.setChildren(new ArrayList());
            item.getChildren().forEach((child) -> walkPermissionItem(permissions, child, treeNode));
        }
        return treeNode;
    }
}
