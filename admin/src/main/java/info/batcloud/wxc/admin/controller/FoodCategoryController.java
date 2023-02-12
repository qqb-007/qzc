package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.service.FoodCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/food-category")
public class FoodCategoryController {

    @Inject
    private FoodCategoryService foodCategoryService;

    @GetMapping("/list")
    public Object list() {
        return foodCategoryService.findSortedList();
    }

    @GetMapping("/firstcategorylist")
    public Object firstCategoryList() {
        return foodCategoryService.findFirstCategoryList();
    }

    @GetMapping("/childcategorylist")
    public Object childCategoryList(@RequestParam String name) {
        List<FoodCategory> byParentName = foodCategoryService.findByParentName(name);
        return byParentName;
    }


    @PostMapping("/init")
    @Permission(value = ManagerPermissions.FOOD_CATEGORY_MANAGE)
    public Object init() {
        foodCategoryService.initFromFood();
        return true;
    }

    @PutMapping("/saveTag/{id}")
    public Object saveMeiTuanTag(@PathVariable long id, @RequestParam String mtname, @RequestParam Long mttagId, @RequestParam String jdname, @RequestParam Long jdtagId) {
        foodCategoryService.setTag(id, mtname, mttagId, jdname, jdtagId);
        return true;
    }

    @PutMapping("/{id}")
    public Object save(@PathVariable long id, @RequestParam String name, @RequestParam Integer idx) {
        foodCategoryService.modifyName(id, name, idx);
        return true;
    }

    @PutMapping("/createFirst")
    public Object createFirst(@RequestParam String name) {
        foodCategoryService.saveFoodCategory(name, null);
        return true;
    }

    @PutMapping("/createScend")
    public Object createScend(@RequestParam String name, @RequestParam String parentName) {
        foodCategoryService.saveFoodCategory(name, parentName);
        return true;
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable long id) {
        foodCategoryService.deleteCategory(id);
        return true;
    }

    @PutMapping("/sort")
    @Permission(value = ManagerPermissions.FOOD_CATEGORY_MANAGE)
    public Object sort(@RequestParam List<String> categoryNameList) {
        foodCategoryService.sort(categoryNameList);
        return true;
    }
}
