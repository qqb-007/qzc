package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.BagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/bag-application")
@PreAuthorize("hasRole('STORE_USER')")
public class BagController {

    @Inject
    private BagService bagService;

    @PostMapping
    public Object apply(BagService.ApplyParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(bagService.applyBag(param));
    }

    @GetMapping("/search")
    public Object search(BagService.SearchParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(bagService.search(param));
    }
}
