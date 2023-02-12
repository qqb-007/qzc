package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.admin.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/role")
@Permission(ManagerPermissions.MANAGER_ROLE_MANAGE)
public class RoleController {

    @Inject
    private RoleService roleService;

    @GetMapping("/list")
    public Object list() {
        return roleService.findAll();
    }

    @PostMapping()
    public Object save(RoleService.RoleAddParam param) {
        roleService.save(param);
        return 1;
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable long id, RoleService.RoleUpdateParam param) {
        param.setId(id);
        roleService.update(param);
        return 1;
    }


    @DeleteMapping("/{id}")
    public Object delete(@PathVariable long id) {
        roleService.deleteById(id);
        return 1;
    }

    @GetMapping("/permission-tree/{id}")
    public Object permissionTree(@PathVariable long id) {
        return roleService.permissionTree(id);
    }

    @PutMapping("/permission/{id}")
    public Object setPermission(@PathVariable long id, SetPermissionForm form) {

        roleService.updatePermissions(id, form.getPermissionList());
        return 1;
    }

    public static class SetPermissionForm {
        private List<String> permissionList;

        public List<String> getPermissionList() {
            return permissionList;
        }

        public void setPermissionList(List<String> permissionList) {
            this.permissionList = permissionList;
        }
    }

}
