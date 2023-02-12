package info.batcloud.wxc.admin.controller;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.dto.FoodSupplierDTO;
import info.batcloud.wxc.core.entity.FoodSupplier;
import info.batcloud.wxc.core.enums.FoodSupplierPriceIncreaseType;
import info.batcloud.wxc.core.service.FoodSupplierService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/food-supplier")
@Permission(ManagerPermissions.FOOD_SUPPLIER_MANAGE)
public class FoodSupplierController {

    @Inject
    private FoodSupplierService foodSupplierService;

    @GetMapping("/search")
    @ResponseBody
    public Paging<FoodSupplierDTO> search(FoodSupplierService.SearchParam param) {
        Paging<FoodSupplierDTO> paging = foodSupplierService.search(param);
        return paging;
    }

    @GetMapping("/list-by-store-user/{storeUserId}")
    public Object findByStoreUserId(@PathVariable long storeUserId) {
        FoodSupplierService.SearchParam sp = new FoodSupplierService.SearchParam();
        sp.setStoreUserId(storeUserId);
        return foodSupplierService.search(sp).getResults();
    }

    @PutMapping("/{id}/price-increase-type")
    public Object setPriceIncreaseType(@PathVariable long id, FoodSupplierPriceIncreaseType priceIncreaseType) {
        foodSupplierService.setPriceIncreaseType(id, priceIncreaseType);
        return true;
    }

    @PostMapping("")
    @ResponseBody
    @Permission(value = ManagerPermissions.FOOD_SUPPLIER_MANAGE)
    public int save(FoodSupplier foodSupplier) {
        foodSupplierService.saveFoodSupplier(foodSupplier);
        return 1;
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Permission(value = ManagerPermissions.FOOD_SUPPLIER_MANAGE)
    public int update(FoodSupplierService.FoodSupplierUpdateParam foodSupplier, @PathVariable Long id) {
        foodSupplierService.updateFoodSupplier(id, foodSupplier);
        return 1;
    }

    @PutMapping("/lock/{locked}")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @Permission(value = ManagerPermissions.FOOD_SUPPLIER_MANAGE)
    public int setStatus(@PathVariable boolean locked, @RequestParam String ids) {
        for (String id : ids.split(",")) {
            if (locked) {
                this.foodSupplierService.lockFoodSupplier(Long.valueOf(id));
            } else {
                this.foodSupplierService.unLockFoodSupplier(Long.valueOf(id));
            }
        }
        return 1;
    }

    @PutMapping("/modifyPwd/{foodSupplierId}")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @Permission(value = ManagerPermissions.FOOD_SUPPLIER_MANAGE)
    public int modifyFoodSupplierPwd(@PathVariable long foodSupplierId, @RequestParam String password, @RequestParam String rePassword) {
        //确认密码不正确
        if (!password.equals(rePassword)) {
            return -1;
        }
        foodSupplierService.modifyPassword(foodSupplierId, password);
        return 1;
    }

    @DeleteMapping("/{id}")
    @Permission(value = ManagerPermissions.FOOD_SUPPLIER_MANAGE)
    public Object delete(@PathVariable long id) {
        foodSupplierService.deleteById(id);
        return 1;
    }

}
