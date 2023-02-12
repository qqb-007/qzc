package info.batcloud.wxc.admin.controller;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.dto.StoreUserDTO;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.service.PrinterService;
import info.batcloud.wxc.core.service.StoreUserService;
import info.batcloud.wxc.core.service.XinYeYunService;
import info.batcloud.wxc.core.service.YilainyunPrintService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/store-user")
@Permission(ManagerPermissions.STORE_USER_MANAGE)
public class StoreUserController {

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private PrinterService printerService;

    @Inject
    private XinYeYunService xinYeYunService;

    @Inject
    private YilainyunPrintService yilainyunPrintService;

    @GetMapping("/search")
    @ResponseBody
    public Paging<StoreUserDTO> search(StoreUserService.SearchParam param) {
        Paging<StoreUserDTO> paging = storeUserService.search(param);
        return paging;
    }

    @PostMapping("")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public int save(StoreUser storeUser) {
        storeUserService.saveStoreUser(storeUser);
        return 1;
    }

    @PutMapping("/toggle/auto-receipt-order/{id}")
    public Object toggleAutoReceiptOrder(@PathVariable long id) {
        return storeUserService.toggleAutoReceiptOrderById(id);
    }

    @PutMapping("/toggle/createDD/{id}")
    public Boolean createDD(@PathVariable long id) {
        storeUserService.createDDShop(id);
        return Boolean.TRUE;
    }

    @PutMapping("/toggle/delFeie/{id}")
    public Object delFeie(@PathVariable long id) {
        return printerService.delPrinter(id);
    }

    @PutMapping("/toggle/delXyy/{id}")
    public Object delXyy(@PathVariable long id) {
        return xinYeYunService.delPrinter(id);
    }

    @PutMapping("/toggle/getYlyToken")
    public Object getYlyToken() {
        storeUserService.getNewYlyToken();
        return true;
    }

    @PutMapping("/toggle/delYiLian/{id}")
    public Object delYiLian(@PathVariable long id) {
        return yilainyunPrintService.delPrinter(id);
    }

    @PutMapping("/toggle/delSms/{id}")
    public Object delSms(@PathVariable long id) {
        storeUserService.deleteSms(id);
        return null;
    }

    @PutMapping("/toggle/createUU/{id}")
    public Object createUU(@PathVariable long id) {
        storeUserService.createUUShop(id);
        return null;
    }

    @PutMapping("/toggle/createSS/{id}")
    public Boolean createSS(@PathVariable long id) {
        storeUserService.createSSShop(id);
        return Boolean.TRUE;
    }

    @PutMapping("/bizManager/{storeUserId}")
    public Object setBizManager(@PathVariable long storeUserId, @RequestParam long employeeId) {
        storeUserService.setStoreUserBizManager(storeUserId, employeeId);
        return true;
    }

    @PutMapping("/bizManager2/{storeUserId}")
    public Object setBizManager2(@PathVariable long storeUserId, @RequestParam long employeeId) {
        storeUserService.setStoreUserBizManager2(storeUserId, employeeId);
        return true;
    }

    @PostMapping("/bank/{storeUserId}")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public int setBank(@PathVariable long storeUserId, @RequestParam String bankName,
                       @RequestParam String bankAccount, @RequestParam String bankAccountName) {
        storeUserService.updateStoreUserBank(storeUserId, bankName, bankAccount, bankAccountName);
        return 1;
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public int update(StoreUserService.StoreUserUpdateParam storeUser, @PathVariable Long id) {
        storeUserService.updateStoreUser(id, storeUser);
        return 1;
    }

    @PutMapping("/delivery-type/{id}")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public int updateDeliveryType(@RequestParam DeliveryType deliveryType, @PathVariable Long id) {
        storeUserService.setStoreUserDeliveryType(id, deliveryType);
        return 1;
    }

    @PutMapping("/lock/{locked}")
    @ResponseBody
    @Transactional
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public int setStatus(@PathVariable boolean locked, @RequestParam String ids) {
        for (String id : ids.split(",")) {
            if (locked) {
                this.storeUserService.lockStoreUser(Long.valueOf(id));
            } else {
                this.storeUserService.unLockStoreUser(Long.valueOf(id));
            }
        }
        return 1;
    }

    @PutMapping("/modifyPwd/{storeUserId}")
    @ResponseBody
    @Transactional
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public int modifyStoreUserPwd(@PathVariable long storeUserId, @RequestParam String password, @RequestParam String rePassword) {
        if (!password.equals(rePassword)) {
            return -1;//确认密码不正确
        }
        storeUserService.modifyPassword(storeUserId, password);
        return 1;
    }

    @DeleteMapping("/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public Object delete(@PathVariable long id) {
        storeUserService.deleteById(id);
        return 1;
    }

}
