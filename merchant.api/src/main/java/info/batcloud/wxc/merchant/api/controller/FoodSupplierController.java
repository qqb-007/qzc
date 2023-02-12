package info.batcloud.wxc.merchant.api.controller;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.FoodSupplierDTO;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.FoodSupplierService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author lvling
 * Date: 2020/2/4
 * Time: 14:39
 */
@RestController
@PreAuthorize("hasRole('STORE_USER')")
@RequestMapping("/food-supplier")
public class FoodSupplierController {

    @Inject
    private FoodSupplierService foodSupplierService;

    @GetMapping("/list-of-current")
    public Object findCurrentStoreUserSuppliers() {
        Long storeUserId = SecurityHelper.loginStoreUserId();
        FoodSupplierService.SearchParam sp = new FoodSupplierService.SearchParam();
        sp.setStoreUserId(storeUserId);
        sp.setPageSize(1000);
        Paging<FoodSupplierDTO> paging = foodSupplierService.search(sp);
        return BusinessResponse.ok(paging.getResults());
    }

}
