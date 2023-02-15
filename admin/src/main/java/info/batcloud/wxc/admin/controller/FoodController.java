package info.batcloud.wxc.admin.controller;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.admin.controller.form.FoodSkuForm;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.domain.FoodSku;
import info.batcloud.wxc.core.dto.FoodSkuDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.service.FoodCategoryService;
import info.batcloud.wxc.core.service.FoodService;
import info.batcloud.wxc.core.service.FoodSkuService;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/food")
@RestController
public class FoodController {

    @Inject
    private FoodService foodService;

    @Inject
    private FoodCategoryService foodCategoryService;

    @Inject
    private FoodSkuService foodSkuService;

    @GetMapping("/search")
    public Object search(FoodService.SearchParam param) {
        return foodService.search(param);
    }

    @PutMapping("/meituan-tag/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object setMetiuanTag(@PathVariable long id, @RequestParam String meituanTagName, @RequestParam long meituanTagId) {
        foodService.setMeituanTag(id, meituanTagName, meituanTagId);
        return true;
    }

    @PutMapping("/addsy")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object changeIncrease(String name) {
        foodService.addShuiYin(name);
        return true;
    }

    @GetMapping("/sku/{id}")
    public Object skuList(@PathVariable long id) {
        return foodService.findById(id).getSkus();
    }

    @GetMapping("/detect-problematic-food")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object detectProblematicFoodList() {
        return foodService.detectProblematicFoodList();
    }

    @PostMapping("/sync/meituan/{poiCode}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object syncFromMeituan(@PathVariable String poiCode) {
        return foodService.syncFromMeituan(poiCode);
    }

    @PutMapping("/quotable/{id}/{flag}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object quotable(@PathVariable long id, @PathVariable boolean flag) {
        foodService.quotable(id, flag);
        return true;
    }

    @PutMapping("/promotional/{id}/{flag}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object promotional(@PathVariable long id, @PathVariable boolean flag) {
        foodService.promotional(id, flag);
        return true;
    }

    @PutMapping("/sort/{foodId}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object changeIdx(@PathVariable long foodId, @RequestParam int idx) {
        foodService.changeFoodIdx(foodId, idx);
        return true;
    }

    @PutMapping("/fork-mirror/{foodId}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object forkMirror(@PathVariable long foodId) {
        return foodService.forkFoodMirror(foodId);
    }

    @GetMapping("/category/list")
    public Object categoryList() {
        return foodService.findFoodCategoryList();
    }

    @GetMapping("/online-store/{storeId}")
    public Object onlineStoreFood(@PathVariable long storeId) {
        return foodService.onlineStoreFood(storeId);
    }

    @GetMapping("/detect-plat-store/{storeId}")
    public Object detectPlatStoreFood(@PathVariable long storeId) {
        return foodService.detectStoreFoodList(storeId);
    }

    @PutMapping("/code/publish-to-store/{storeId}")
    public Object publishCode(@PathVariable long storeId, FoodService.FoodCodePublishParam publishParam) {
        foodService.foodCodePublishToStore(storeId, publishParam);
        return foodService.foodCodePublishToStore(storeId, publishParam);
    }

    @GetMapping("/{id}")
    public Object info(@PathVariable long id) {
        return foodService.findById(id);
    }

    @PutMapping("/code/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object setCode(@PathVariable long id, @RequestParam String code) {
        foodService.setFoodCode(id, code);
        return true;
    }

    @PutMapping("/price/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object setPrice(@PathVariable long id, @RequestParam float price) {
        foodService.setFoodPrice(id, price);
        return true;
    }

    @PutMapping("/warning-price/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object setWarningPrice(@PathVariable long id, @RequestParam float warningPrice) {
        foodService.setFoodWarningPrice(id, warningPrice);
        return true;
    }

    @PutMapping("/per-increase/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object setPerIncrease(@PathVariable long id, @RequestParam float perIncrease) {
        foodService.setPerIncrease(id, perIncrease);
        return true;
    }

    @PutMapping("/quote-unit/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object set(@PathVariable long id, @RequestParam String unit) {
        foodService.setQuoteUnit(id, unit);
        return true;
    }

    @PutMapping("/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object update(@PathVariable long id, FoodService.UpdateParam param) {
        foodService.updateFood(id, param);
        return true;
    }

    @PutMapping("/actual/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object actual(@PathVariable long id, @RequestParam long actualFoodId) {
        foodService.bindActualFood(id, actualFoodId);
        return true;
    }

    @DeleteMapping("/actual/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object relieveActual(@PathVariable long id) {
        foodService.relieveActualFood(id);
        return true;
    }

    @DeleteMapping("/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object delete(@PathVariable long id) {
        foodService.deleteById(id);
        return true;
    }

    @PostMapping("/pro")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchSave(@RequestParam String json) {
        FoodService.PropertyParam param = JSON.parseObject(json, FoodService.PropertyParam.class);
        foodService.setProperties(param);
        return true;
    }

    @GetMapping("/export")
    public Object export(FoodService.SearchParam param, HttpServletResponse response) throws IOException {
        File file = foodService.export(param);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + System.currentTimeMillis() + ".xls");
        try (InputStream is = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            file.delete();
        }
        return null;
    }

    @PostMapping("")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object save(FoodService.CreateParam param) {
        return foodService.createFood(param);
    }

//    @PutMapping("/propertie/{id}")
//    @Permission(value = ManagerPermissions.FOOD_MANAGE)
//    public Object setPropertie(@PathVariable long id, FoodSkuForm form) {
//        List<FoodSku> foodSkus = new ArrayList<>();
//        for (int i = 0; i < form.getPriceRatioList().size(); i++) {
//            FoodSku sku = new FoodSku();
//            sku.setSpec(form.getSpecList().get(i));
//            sku.setSkuId(form.getSkuIdList().get(i));
//            sku.setPriceRatio(form.getPriceRatioList().get(i));
//            sku.setQuoteUnitRatio(form.getQuoteUnitRatioList().get(i));
//            sku.setWeight(form.getWeightList().get(i));
//            sku.setIgnore(form.getIgnoreList().get(i));
//            foodSkus.add(sku);
//        }
//        foodService.setFoodSku(id, foodSkus);
//        return foodSkus;
//    }

    @PutMapping("/sku/{id}")
    @Permission(value = ManagerPermissions.FOOD_MANAGE)
    public Object setSku(@PathVariable long id, FoodSkuForm form) {
        //List<FoodSkuDTO> foodSkus = new ArrayList<>();
        for (int i = 0; i < form.getNameList().size(); i++) {
            if(form.getIdList().get(i) == null){
                //新增
                FoodSkuService.CreateParam param = new FoodSkuService.CreateParam();
                param.setFoodId(id);
                param.setUpc(form.getUpcList().get(i));
                param.setName(form.getNameList().get(i));
                param.setWeight(form.getWeightList().get(i));
                param.setSpec(form.getSpecList().get(i));
                param.setInputTax(form.getInputTaxList().get(i));
                param.setOutputTax(form.getOutputTaxList().get(i));
                param.setMinOrderCount(form.getMinOrderCountList().get(i));
                param.setBoxNum(form.getBoxNumList().get(i));
                param.setBoxPrice(form.getBoxPriceList().get(i));
                foodSkuService.createFoodSku(param);
            }else {
                FoodSkuService.UpdateParam updateParam = new FoodSkuService.UpdateParam();
                updateParam.setId(form.getIdList().get(i));
                updateParam.setUpc(form.getUpcList().get(i));
                updateParam.setName(form.getNameList().get(i));
                updateParam.setWeight(form.getWeightList().get(i));
                updateParam.setSpec(form.getSpecList().get(i));
                updateParam.setInputTax(form.getInputTaxList().get(i));
                updateParam.setOutputTax(form.getOutputTaxList().get(i));
                updateParam.setMinOrderCount(form.getMinOrderCountList().get(i));
                updateParam.setBoxNum(form.getBoxNumList().get(i));
                updateParam.setBoxPrice(form.getBoxPriceList().get(i));
                foodSkuService.updateFoodSku(updateParam);
            }
        }
        return foodService.findById(id).getSkus();
    }
}
