package info.batcloud.wxc.admin.service;

import info.batcloud.wxc.admin.domain.PermissionTreeNode;
import info.batcloud.wxc.admin.permission.ManagerPermissions;

import java.util.List;

public interface PermissionService {

    boolean checkPermission(long managerId, String permission);

    List<String> findManagerPermissions(long managerId);

    boolean checkAdminRole(long managerId);

    int clearRolePermission(long roleId);

    int clearManagerPermission(long managerId);

    List<PermissionTreeNode> permissionTree(List<String> permissions);

    boolean checkSettingPermission(long managerId, String setting);

    ManagerPermissions getSettingPermission(String setting);

}
