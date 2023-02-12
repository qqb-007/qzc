package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.service.FoodQuoteSheetDetailService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/food-quote-sheet-detail")
@PreAuthorize("hasRole('STORE_USER')")
public class FoodQuoteSheetDetailController {
    @Inject
    private FoodQuoteSheetDetailService foodQuoteSheetDetailService;

    @GetMapping("/list/{foodQuoteSheetId}")
    public Object search(@PathVariable long foodQuoteSheetId) {
        return BusinessResponse.ok(foodQuoteSheetDetailService.findByFoodQuoteSheetId(foodQuoteSheetId));
    }

}
