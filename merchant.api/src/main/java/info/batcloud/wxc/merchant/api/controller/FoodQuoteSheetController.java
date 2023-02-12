package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.FoodQuoteSheetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/food-quote-sheet")
@PreAuthorize("hasRole('STORE_USER')")
public class FoodQuoteSheetController {
    @Inject
    private FoodQuoteSheetService foodQuoteSheetService;

    @GetMapping("/search")
    public Object search(FoodQuoteSheetService.SearchParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(foodQuoteSheetService.search(param));
    }

}
