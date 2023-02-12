package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.SettlementSheetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RequestMapping("/settlement-sheet")
@RestController
@PreAuthorize("hasRole('STORE_USER')")
public class SettlementSheetController {

    @Inject
    private SettlementSheetService settlementSheetService;

    @GetMapping("/search")
    public Object search(SettlementSheetService.SearchParam param) {
        param.setFetchDetails(true);
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(settlementSheetService.search(param));
    }


}
