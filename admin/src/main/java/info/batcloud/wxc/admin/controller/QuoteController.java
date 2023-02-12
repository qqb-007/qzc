package info.batcloud.wxc.admin.controller;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.service.QuoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/quote")
public class QuoteController {

    @Inject
    private QuoteService quoteService;

    @PostMapping("/food")
    public Object quoteFood(@RequestParam String json) {
        QuoteService.FoodQuoteParam param = JSON.parseObject(json, QuoteService.FoodQuoteParam.class);
        quoteService.storeUserQuoteFood(param);
        return BusinessResponse.ok(true);
    }

}
