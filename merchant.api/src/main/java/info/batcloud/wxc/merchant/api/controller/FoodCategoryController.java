package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.service.FoodCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author lvling
 * Date: 2020/3/12
 * Time: 22:16
 */
@RestController
@RequestMapping("/food-category")
public class FoodCategoryController {

    @Inject
    private FoodCategoryService foodCategoryService;

    @GetMapping("/list-with-food-num")
    public Object listWithFoodNum() {
        return BusinessResponse.ok(foodCategoryService.findList());
    }

    @GetMapping("/list-by-parent-name/{parentName}")
    public Object listByParentName(@PathVariable String parentName) {
        return BusinessResponse.ok(foodCategoryService.findByParentName(parentName));
    }
}
