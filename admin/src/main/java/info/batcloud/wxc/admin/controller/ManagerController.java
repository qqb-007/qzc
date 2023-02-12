package info.batcloud.wxc.admin.controller;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.admin.entity.Manager;
import info.batcloud.wxc.admin.helper.SecurityHelper;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.admin.service.ManagerService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/manager")
@Permission(ManagerPermissions.MANAGER_MANAGE)
public class ManagerController {

    @Inject
    private ManagerService managerService;

    @PutMapping("/modify-password")
    public Object modifyPassword(ModifyPwdForm form) {

        managerService.modifyPassword(SecurityHelper.loginManagerId(), form.getOriginPassword(), form.getPassword());

        return true;
    }

    public static class ModifyPwdForm {
        private String password;
        private String originPassword;

        public String getOriginPassword() {
            return originPassword;
        }

        public void setOriginPassword(String originPassword) {
            this.originPassword = originPassword;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @GetMapping("/search")
    @ResponseBody
    public Paging<Manager> search(ManagerService.SearchParam param){
        Paging<Manager> paging = managerService.search(param);
        return paging;
    }

    @PostMapping("")
    @ResponseBody
    public int save(Manager manager) {
        managerService.saveManager(manager);
        return 1;
    }

    @PutMapping("/{id}")
    @ResponseBody
    public int update(Manager manager, @PathVariable Long id) {
        manager.setId(id);
        managerService.updateManager(manager);
        return 1;
    }

    @PutMapping("/lock/{locked}")
    @ResponseBody
    @Transactional
    public int setStatus(@PathVariable boolean locked, @RequestParam String ids) {
        for (String id : ids.split(",")) {
            if(locked) {
                this.managerService.lockManager(Long.valueOf(id));
            } else {
                this.managerService.unLockManager(Long.valueOf(id));
            }
        }
        return 1;
    }

    @PutMapping("/modifyManagerPwd/{managerId}")
    @ResponseBody
    @Transactional
    public int modifyManagerPwd(@PathVariable long managerId, @RequestParam String password, @RequestParam String rePassword) {
        if(!password.equals(rePassword)){
            return -1;//确认密码不正确
        }
        managerService.modifyPassword(managerId, password);
        return 1;
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable long id) {
        managerService.deleteById(id);
        return 1;
    }

}
