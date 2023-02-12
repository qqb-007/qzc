package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.enums.StoreFoodApplicationStatus;
import info.batcloud.wxc.core.service.StoreFoodApplicationService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RequestMapping("/api/store-food-application")
@RestController
@Permission(value = ManagerPermissions.STORE_FOOD_APPLICATION_MANAGE)
public class StoreFoodApplicationController {

    @Inject
    private StoreFoodApplicationService storeFoodApplicationService;

    @GetMapping("/search")
    public Object search(StoreFoodApplicationService.SearchParam searchParam) {
        return storeFoodApplicationService.search(searchParam);
    }

    @PutMapping("/status/{id}")
    public Object setStatus(@PathVariable long id, StoreFoodApplicationStatus status) {
        storeFoodApplicationService.setStatusById(id, status);
        return true;
    }

}
