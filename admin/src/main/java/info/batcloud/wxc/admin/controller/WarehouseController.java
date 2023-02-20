package info.batcloud.wxc.admin.controller;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.dto.WarehouseDTO;
import info.batcloud.wxc.core.entity.Warehouse;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import info.batcloud.wxc.core.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/warehouse")
@Permission(value = ManagerPermissions.WITHDRAW)
public class WarehouseController {
    @Inject
    WarehouseService warehouseService;

    @Inject
    StoreUserFoodService storeUserFoodService;

    //    @Inject
//    UserService userService;
//
    @GetMapping("/search")
    @ResponseBody
    public Paging<WarehouseDTO> search(WarehouseService.SearchParam param) {
        Paging<WarehouseDTO> paging = warehouseService.search(param);
        return paging;
    }

    @GetMapping("/getsufwList")
    @ResponseBody
    public Map<String,Object> getSufList(WarehouseService.SearchParam param) {
        //Paging<WarehouseDTO> paging = warehouseService.search(param);
        Map<String,Object> map = new HashMap<>();
        map.put("results",warehouseService.getskuWhList(param.getStoreUserId()));
        return map;
    }


//    @GetMapping("/getWhSufList")
//    @ResponseBody
//    public Map<String,Object> getWhSufList(WarehouseService.SearchParam param) {
//        //Paging<WarehouseDTO> paging = warehouseService.search(param);
//        Map<String,Object> map = new HashMap<>();
//        map.put("results",storeUserFoodService.getWhSufList(param.getStoreUserId()));
//        return map;
//    }

    //
    @PostMapping("")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public int save(WarehouseService.AddParam addParam) {
        warehouseService.create(addParam);
        return 1;
    }

    //
    @PutMapping("/{id}")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public int update(WarehouseService.UpdateParam storeUser, @PathVariable Long id) {
        storeUser.setId(id);
        warehouseService.update(storeUser);
        return 1;
    }
//
//    @PutMapping("/bizsuf/{sufId}")
//    public Object setBizManager(@PathVariable long sufId, @RequestParam long wid) {
//        warehouseService.bindStoreUserFoodSku(wid, sufId);
//        return true;
//    }

//    @PutMapping("/delsuf/{sufId}")
//    public Object delsuf(@PathVariable long sufId, @RequestParam long wid) {
//        warehouseService.deleteFoodSku(wid, sufId);
//        return true;
//    }
//
//    @PutMapping("/bizManager2/{storeUserId}")
//    public Object setBizManager2(@PathVariable long storeUserId, @RequestParam long employeeId) {
//        warehouseService.bindManager(storeUserId, employeeId);
//        return true;
//    }
//
//    @PutMapping("/lock/{locked}")
//    @ResponseBody
//    @Transactional
//    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
//    public int setStatus(@PathVariable boolean locked, @RequestParam String ids) {
//        for (String id : ids.split(",")) {
//            if (locked) {
//                this.warehouseService.lockWarehouse(Long.valueOf(id));
//            } else {
//                this.warehouseService.unlockWarehouse(Long.valueOf(id));
//            }
//        }
//        return 1;
//    }
//
    @DeleteMapping("/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_MANAGE)
    public Object delete(@PathVariable long id) {
        warehouseService.deleteWarehouse(id);
        return 1;
    }


}
