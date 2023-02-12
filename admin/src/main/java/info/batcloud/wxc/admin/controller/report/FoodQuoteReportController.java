package info.batcloud.wxc.admin.controller.report;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.service.FoodQuoteReportService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/food-quote-report")
public class FoodQuoteReportController {

    @Inject
    private FoodQuoteReportService foodQuoteReportService;

    @GetMapping("/search")
    public Object search(FoodQuoteReportService.SearchParam param) {
        return foodQuoteReportService.search(param);
    }

    @PutMapping("/generate-all")
    public Object generateAll() {
        foodQuoteReportService.generateByAllFood();
        return true;
    }

    @PutMapping("/generate/{foodId}")
    public Object generateFood(@PathVariable long foodId) {
        foodQuoteReportService.generateByFoodId(foodId);
        return true;
    }


    @PutMapping("/ref-price/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object setPrice(@PathVariable long id, @RequestParam float refPrice) {
        foodQuoteReportService.setRefPrice(id, refPrice);
        return true;
    }

    @PutMapping("/warning-price/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object setWarningPrice(@PathVariable long id, @RequestParam float warningPrice) {
        foodQuoteReportService.setWarningPrice(id, warningPrice);
        return true;
    }

}
