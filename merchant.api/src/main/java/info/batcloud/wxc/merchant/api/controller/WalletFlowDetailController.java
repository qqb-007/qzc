package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.WalletFlowDetailService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/wallet-flow-detail")
@PreAuthorize("hasRole('STORE_USER')")
public class WalletFlowDetailController {

    @Inject
    private WalletFlowDetailService walletFlowDetailService;

    @GetMapping("/search")
    public Object search(WalletFlowDetailService.SearchParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(walletFlowDetailService.search(param));
    }

}
