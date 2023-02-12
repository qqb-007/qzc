package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.FoodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RequestMapping("/food")
@RestController
public class FoodController {

    @Inject
    private FoodService foodService;

    @GetMapping("/search")
    public Object search(FoodService.SearchParam param) {
        param.setExcludeStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(foodService.search(param));
    }

}
