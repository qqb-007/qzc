package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.admin.service.ManagerRoleService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/manager-role")
@Permission(ManagerPermissions.MANAGER_ROLE_MANAGE)
public class ManagerRoleController {

    @Inject
    private ManagerRoleService managerRoleService;

    @GetMapping("/{managerId}")
    public Object list(@PathVariable long managerId) {
        return managerRoleService.listManagerRoles(managerId);
    }

    @PutMapping("/{managerId}")
    public Object setRoles(@PathVariable long managerId, @RequestParam(required = false) List<Long> roleIds) {
        managerRoleService.setManagerRoles(managerId, roleIds);
        return 1;
    }

}
