package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.enums.FoodQuoteSheetStatus;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.mapper.FoodQuoteMapper;
import info.batcloud.wxc.core.service.QuoteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/quote")
@PreAuthorize("hasRole('STORE_USER')")
public class QuoteController {

    @Inject
    private QuoteService quoteService;

    @GetMapping("/food/search")
    public Object quoteFoodList(QuoteService.FoodForQuoteSearchParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
//        param.setPageSize(1000);
        return BusinessResponse.ok(quoteService.searchFoodForQuote(param));
    }

    @PostMapping("/food")
    public Object quoteFood(@RequestParam String json) {
        QuoteService.FoodQuoteParam param = JSON.parseObject(json, QuoteService.FoodQuoteParam.class);
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        quoteService.storeUserQuoteFood(param);
        return BusinessResponse.ok(true);
    }

}
