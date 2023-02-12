package info.batcloud.wxc.merchant.api.controller.supplier;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.StoreFoodApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/food-supplier/store-food-application")
@PreAuthorize("hasRole('FOOD_SUPPLIER')")
public class FoodSupplierStoreFoodApplicationController {

    @Inject
    private StoreFoodApplicationService storeFoodApplicationService;

    @PostMapping
    public Object apply(@RequestBody StoreFoodApplicationService.ApplyParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        param.setFoodSupplierId(SecurityHelper.loginFoodSupplierId());
        return BusinessResponse.ok(storeFoodApplicationService.apply(param));
    }

}
