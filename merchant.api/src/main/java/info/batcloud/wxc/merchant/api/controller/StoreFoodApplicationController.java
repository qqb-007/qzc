package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.StoreFoodApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/store-food-application")
@PreAuthorize("hasRole('STORE_USER')")
public class StoreFoodApplicationController {

    @Inject
    private StoreFoodApplicationService storeFoodApplicationService;

    @PostMapping
    public Object apply(StoreFoodApplicationService.ApplyParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(storeFoodApplicationService.apply(param));
    }

}
