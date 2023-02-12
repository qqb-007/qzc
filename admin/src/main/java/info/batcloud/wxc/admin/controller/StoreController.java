package info.batcloud.wxc.admin.controller;

import com.sankuai.meituan.waimai.opensdk.util.StringUtil;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.enums.UuAccountType;
import info.batcloud.wxc.core.service.StoreService;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RequestMapping("/api/store")
@RestController
public class StoreController {

    @Inject
    private StoreService storeService;

    @Inject
    private StoreUserFoodService storeUserFoodService;

    @GetMapping("/search")
    public Object search(StoreService.SearchParam param) {
        return storeService.search(param);
    }

    @PostMapping("/sync/meituan")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object syncFromMeituan(@RequestParam String poiCodes) {
        return storeService.syncFromMeituan(poiCodes);
    }

    @PostMapping("/sync/meituan-pic")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object updateMeiTuanPic(@RequestParam String poiCodes, @RequestParam String imgUrl) {
        System.out.println(imgUrl);
        if (StringUtil.isNotBlank(imgUrl)) {
            storeService.updateStorePic(poiCodes, imgUrl);
        }
        return null;
    }

    @PutMapping("/checkFoodTip/{id}/{opening}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object checkFoodTip(@PathVariable long id) {
        storeUserFoodService.updateTips(id);
        return true;
    }

    @PutMapping("/checkAllFoodTip/{id}/{opening}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object checkAllFoodTip(@PathVariable long id) {
        storeUserFoodService.batchUpdateTips(Plat.MEITUAN);
        return true;
    }

    @PostMapping("/sync/clbm")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object syncFromClbm(@RequestParam String poiCodes) {
        return storeService.syncFromClbm(poiCodes);
    }

    @PostMapping("/sync/ele")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object syncFromEle(@RequestParam(required = false) String shopId,
                              @RequestParam(required = false) Integer sysStatus) {
        if (shopId != null) {
            storeService.syncFromEle(shopId);
        } else {
            storeService.syncListFromEle(sysStatus);
        }
        return true;
    }

    @PostMapping("/sync/wante")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object syncFromWante(@RequestParam(required = false) String shopId) {
        storeService.syncFromWante(shopId);
        return true;
    }

    @PostMapping("/sync/jddj")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object syncFromJddj(@RequestParam(required = false) String shopId) {
        storeService.syncFromJddj(shopId, true);
        return true;
    }

    @PostMapping("/sync/jddjz")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object syncFromJddjz(@RequestParam(required = false) String shopId) {
        storeService.syncFromJddj(shopId, false);
        return true;
    }

    @PutMapping("/secend-delivery-type/{id}")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public int updateSecendDeliveryType(@RequestParam DeliveryType deliveryType, @PathVariable Long id) {
        storeService.setStoreSecendDeliveryType(id, deliveryType);
        return 1;
    }

    @PutMapping("/delivery-type/{id}")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public int updateDeliveryType(@RequestParam DeliveryType deliveryType, @PathVariable Long id) {
        storeService.setStoreDeliveryType(id, deliveryType);
        return 1;
    }

    @PutMapping("/uu-type/{id}")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public int updateUuType(@RequestParam UuAccountType uuAcountType, @PathVariable Long id) {
        //System.out.println(uuAcountType.getTitle() + id);
        storeService.setStoreUuAccountType(id, uuAcountType);
        return 1;
    }

    @PutMapping("/{id}/delivery-area")
    @ResponseBody
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public int updateDeliveryArea(@PathVariable Long id) {
        storeService.updateDeliveryAreaById(id);
        return 1;
    }

    @PutMapping("/toggle/uuMix/{id}")
    public Object toggleUuMix(@PathVariable long id) {
        return storeService.toggleUuMix(id);
    }

    @PostMapping("/update/area")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object refundAgree(long id, String area) {
        return storeService.updateDeliveryArea(id, area);
    }

    @PostMapping("/update/uuTime")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object updateUuTime(long id, Float time) {
        storeService.updateUuTime(id, time);
        return true;
    }

    @PutMapping("/store-user/{id}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object bindStoreUser(@PathVariable long id, @RequestParam long storeUserId) {
        storeService.bindStoreUser(id, storeUserId);
        return true;
    }

    @PutMapping("/price-increase/{id}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object setPriceIncrease(@PathVariable long id, @RequestParam float priceIncrease) {
        storeService.setPriceIncrease(id, priceIncrease);
        return true;
    }

    @PutMapping("/open/{id}/{opening}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object open(@PathVariable long id, @PathVariable Boolean opening) {
        if (opening) {
            storeService.openStore(id);
        } else {
            storeService.closeStore(id);
        }
        return true;
    }

    @PutMapping("/openLine/{id}/{opening}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object openLine(@PathVariable long id, @PathVariable Boolean opening) {
        if (opening) {
            storeService.openLineStore(id);
        } else {
            storeService.closeLineStore(id);
        }
        return true;
    }

    @PutMapping("/openShow/{id}/{opening}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object openShow(@PathVariable long id, @PathVariable Boolean opening) {
        if (opening) {
            storeService.openShow(id);
        } else {
            storeService.closeShow(id);
        }
        return true;
    }

    @PutMapping("/deliverySelf/{id}/{opening}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object deliverySelf(@PathVariable long id, @PathVariable Boolean opening) {
        if (opening) {
            storeService.openDeliverySelf(id);
        } else {
            storeService.closeDeliverySelf(id);
        }
        return true;
    }


    @PutMapping("/checkFood/{id}/{opening}")
    @Permission(value = ManagerPermissions.STORE_MANAGE)
    public Object checkFood(@PathVariable long id) {
        storeUserFoodService.checkStoreUserFood(id);
        return true;
    }

}
