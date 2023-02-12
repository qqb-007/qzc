package info.batcloud.wxc.admin.controller;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.admin.helper.SecurityHelper;
import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.entity.FoodCategory;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.service.FoodCategoryService;
import info.batcloud.wxc.core.service.StoreService;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import org.apache.commons.lang3.StringUtils;
import org.python.modules.itertools.count;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/store-user-food")
public class StoreUserFoodController {

    @Inject
    @Lazy
    private StoreUserFoodService storeUserFoodService;

    @Inject
    private FoodCategoryService foodCategoryService;

    @PutMapping("/sold-out/{storeUserFoodId}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object soldOut(@PathVariable long storeUserFoodId, @RequestParam(required = false) Plat plat) {

        if (plat == null) {
            return storeUserFoodService.soldOutById(storeUserFoodId);
        } else {
            return storeUserFoodService.soldOutPlatById(storeUserFoodId, plat);
        }
    }

    @PutMapping("/{storeUserFoodId}/food-supplier")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object soldOut(@PathVariable long storeUserFoodId, @RequestParam long foodSupplierId) {
        this.storeUserFoodService.setFoodSupplier(storeUserFoodId, foodSupplierId);
        return true;
    }

    @GetMapping("/{id}")
    public Object info(@PathVariable long id) {
        return storeUserFoodService.findById(id);
    }

    @GetMapping("/export")
    public Object export(StoreUserFoodService.SearchParam param, HttpServletResponse response) throws IOException {
        File file = storeUserFoodService.export(param);
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + System.currentTimeMillis() + ".xls");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        try (
                InputStream is = new FileInputStream(file);
                OutputStream os = response.getOutputStream()
        ) {
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

    @GetMapping("/search")
    public Object search(StoreUserFoodService.SearchParam param) {
        return storeUserFoodService.search(param);
    }

    @PutMapping("/reset-publish")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object resetPublish(@RequestParam List<Long> storeUserIdList, @RequestParam(required = false) List<Long> foodIdList, @RequestParam(required = true) List<Plat> platList) {
        for (Long storeUserId : storeUserIdList) {
            if (foodIdList == null || foodIdList.size() == 0) {
                storeUserFoodService.resetStoreUserPublish(storeUserId, platList);
            } else {
                storeUserFoodService.resetStoreUserFoodPublish(storeUserId, foodIdList, platList);
            }
        }
        return true;
    }

    @PutMapping("/batch-active")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchActive(@RequestParam(required = false) List<Long> storeUserIdList, @RequestParam Boolean allStoreUser, @RequestParam List<Long> foodIdList, @RequestParam(required = true) List<Plat> platList, @RequestParam Integer activeType, Float priceIncrease) {
        if ((storeUserIdList == null || storeUserIdList.size() == 0) && !allStoreUser) {
            return false;
        }
        if (foodIdList == null || foodIdList.size() == 0) {
            return false;
        }
        if (activeType != 1 && activeType != 2 && activeType != 3) {
            return false;
        }
        if (activeType == 2 && priceIncrease == null) {
            return false;
        }
        if (activeType == 3) {
            //发布指定满减活动
            //storeUserFoodService.batchDiscount(foodIdList, storeUserIdList, platList);
        } else {
            storeUserFoodService.batchActive(foodIdList, allStoreUser, storeUserIdList, platList, activeType, priceIncrease);
        }

        return true;
    }


    @PutMapping("/couponDiscount-active")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object fullDiscount(@RequestParam(required = false) List<Long> storeUserIdList, @RequestParam Boolean allStoreUser, @RequestParam(required = false) List<Long> foodIdList, @RequestParam(required = true) List<Plat> platList, @RequestParam Integer activeType, String name, Float fullPrice, Float reducePrice) {
        if ((storeUserIdList == null || storeUserIdList.size() == 0) && !allStoreUser) {
            return false;
        }
        if (foodIdList == null || foodIdList.size() == 0) {
            return false;
        }
        if (activeType != 1 && activeType != 2 && activeType != 3) {
            return false;
        }
        if (StringUtils.isBlank(name) || fullPrice == null || reducePrice == null) {
            return false;
        }
        if (activeType == 1) {
            //部分满减
            storeUserFoodService.batchDiscount(foodIdList, storeUserIdList, platList, name, fullPrice, reducePrice);
        } else if (activeType == 2) {
            //商品券活动
            storeUserFoodService.batchCouponDiscount(foodIdList, storeUserIdList, allStoreUser, platList, name, fullPrice, reducePrice);
        } else if (activeType == 3) {
            storeUserFoodService.batchXm(foodIdList, storeUserIdList, platList, name, fullPrice.doubleValue(), reducePrice.intValue());
        }

        return true;
    }

    @PutMapping("/batch-soldOutByPlat")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchSoldOutByIdAndPlat(@RequestParam(required = false) List<Long> foodIdList, @RequestParam(required = true) List<Plat> platList) {

        if (foodIdList == null || foodIdList.size() == 0) {
            return false;
        }
        storeUserFoodService.batchSoldOutByFoodId(foodIdList.get(0), platList);
        return true;
    }

    @PutMapping("/reset-status")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object resetStatus(@RequestParam List<Long> storeUserIdList, @RequestParam(required = true) List<Plat> platList) {
        for (Long storeUserId : storeUserIdList) {
            storeUserFoodService.batchResetStatus(storeUserId, platList);
        }
        return true;
    }

    @PutMapping("/verify/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object verify(@PathVariable long id) {

        return storeUserFoodService.verifyQuoteById(id);
    }

    @PutMapping("/unlock/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object unlock(@PathVariable long id) {

        return storeUserFoodService.unlockQuotePrice(id);
    }

    @PutMapping("/category/{id}")
    public Object setCategory(@PathVariable long id, @RequestParam(required = false) Long categoryId) {
        if (categoryId == null) {
            storeUserFoodService.clearCategory(id);
        } else {
            storeUserFoodService.setCategory(id, categoryId);
        }
        return true;
    }

    @PutMapping("/quote-price/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object quotePrice(@PathVariable long id, @RequestParam float quotePrice) {
        return storeUserFoodService.changeQuotePrice(id, quotePrice);
    }

    @PutMapping("/supplier-alter-quote-price/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object supplierQuotePrice(@PathVariable long id, @RequestParam float supplierAlterQuotePrice) {
        return storeUserFoodService.changeSupplierAlterQuotePrice(id, supplierAlterQuotePrice);
    }

    @PutMapping("/supplier-increase/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object supplierIncrease(@PathVariable long id, @RequestParam float supplierIncrease) {
        return storeUserFoodService.changeSupplierIncrease(id, supplierIncrease);
    }

    @PutMapping("/price-increase/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object priceIncrease(@PathVariable long id, @RequestParam float priceIncrease) {
        return storeUserFoodService.changePriceIncrease(id, priceIncrease);
    }

    @PutMapping("/sold-out/food/{foodId}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchSoldOut(@PathVariable long foodId) {
        storeUserFoodService.soldOutByFoodId(foodId);
        return true;
    }

    @PutMapping("/special-sku/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object setSpecialSku(@PathVariable long id, @RequestParam(required = false) List<String> specialSkuIdList,@RequestParam(required = false) List<String> skuId,@RequestParam(required = false) List<Integer> stock, @RequestParam(required = false) String eleSkuId) {
        storeUserFoodService.setSpecialSkuList(id, specialSkuIdList == null ? new ArrayList<>(0) : specialSkuIdList);
        storeUserFoodService.updateStock(id,skuId,stock);
        storeUserFoodService.setEleSkuId(id, eleSkuId);
        return true;
    }

//    @PostMapping("/{storeUserId}")
//    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
//    public Object save(@PathVariable long storeUserId, @RequestParam String json) {
//        List<StoreUserFoodService.SaveParam> paramList = JSON.parseObject(json, new TypeReference<List<StoreUserFoodService.SaveParam>>() {
//        });
//        storeUserFoodService.batchSaveStoreUserFood(storeUserId, paramList);
//        return true;
//    }

    @PostMapping()
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchSave(@RequestParam String json) {
        StoreUserFoodService.BatchSaveParam param = JSON.parseObject(json, StoreUserFoodService.BatchSaveParam.class);
        storeUserFoodService.batchSaveStoreUserFood(param);
        return true;
    }

    @PostMapping("/lock")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchLock(@RequestParam String json) {
        StoreUserFoodService.BatchLockParam param = JSON.parseObject(json, StoreUserFoodService.BatchLockParam.class);
        storeUserFoodService.batchLockQuotePrice(param);
        return true;
    }

    @PostMapping("/saleTime")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object saleTime(@RequestParam String json) {
        StoreUserFoodService.BatchSaleTimeParam param = JSON.parseObject(json, StoreUserFoodService.BatchSaleTimeParam.class);
        storeUserFoodService.batchSetSaleTime(param);
        return true;
    }

    @PostMapping("/priceChange")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchChangePrice(@RequestParam String json) {
        StoreUserFoodService.BatchChangePriceParam param = JSON.parseObject(json, StoreUserFoodService.BatchChangePriceParam.class);
        storeUserFoodService.batchChangePrice(param);
        return true;
    }

    @PostMapping("/priceChangeIncrease")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchChangePriceIncrease(@RequestParam String json) {
        StoreUserFoodService.BatchChangePriceParam param = JSON.parseObject(json, StoreUserFoodService.BatchChangePriceParam.class);
        storeUserFoodService.batchChangePriceIncrease(param);
        return true;
    }

    @PostMapping("/batchCheckPlatFood")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchCheckPlatFood(@RequestParam String json) {
        StoreUserFoodService.BatchCheckPlatFoodParam param = JSON.parseObject(json, StoreUserFoodService.BatchCheckPlatFoodParam.class);
        storeUserFoodService.batchCheckPlatFood(param);
        return true;
    }

    @PostMapping("/unlock")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchUnLock(@RequestParam String json) {
        StoreUserFoodService.BatchLockParam param = JSON.parseObject(json, StoreUserFoodService.BatchLockParam.class);
        storeUserFoodService.batchUnlockQuotePrice(param);
        return true;
    }

    @PostMapping("/publish/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object publish(@PathVariable long id, @RequestParam boolean checkExists, @RequestParam List<Plat> platList) {
        return storeUserFoodService.publish(id, checkExists, platList);
    }

    @PostMapping("/publishActive/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object publishActive(@PathVariable long id, @RequestParam boolean checkExists, @RequestParam List<Plat> platList, @RequestParam Integer activeType, Float priceIncrease) {
        return storeUserFoodService.publishActive(id, checkExists, platList, activeType, priceIncrease);
    }

    @PutMapping("/increase/change")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object changeIncrease(StoreUserFoodService.IncreaseChangeParam form) {
        if (form.getStoreUserIdList() == null || form.getStoreUserIdList().size() == 0) {
            return false;
        }
        storeUserFoodService.changePriceIncrease(form);
        return true;
    }

    @PutMapping("/increase/quotechange")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object changeIncreasequote(StoreUserFoodService.QuoteChangeParam form) {
        if (form.getStoreUserIdList() == null || form.getStoreUserIdList().size() == 0) {
            return false;
        }
        storeUserFoodService.batchChangeQuotePrice(form);
        return true;
    }

    @PutMapping("/increase/changeByPrice")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object changeIncreaseByPrice(StoreUserFoodService.IncreateChangeByPrice form) {
        //System.out.println(form);
        storeUserFoodService.batchChangePrice(form.getStart(), form.getEnd(), form.getChange());
        return true;
    }

    @PutMapping("/increase/changeElePrice")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object changeElePrice(StoreUserFoodService.IncreateChangeByPrice form) {
        //System.out.println(form);
        storeUserFoodService.updataElePrice((int) (form.getChange()));
        return true;
    }

    @PutMapping("/soldCategory")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object soldCategory(StoreUserFoodService.BatchSoldCategoryParam form) {
        if (form.getStoreUserIdList() == null || form.getStoreUserIdList().size() == 0) {
            return false;
        }
        storeUserFoodService.batchSoldOutCategory(form);
        return true;
    }

    @PutMapping("/soldOut/Jddj")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object soldOudJddj(StoreUserFoodService.soldOutJddjParam form) {
        storeUserFoodService.batchSoldOutJddj(form.getStoreUserIdList());
        return true;
    }

    @PutMapping("/align-store-user-food")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object alignStoreUserFood(StoreUserFoodService.AlignStoreUserFoodParam form) {
        storeUserFoodService.alignStoreUserFood(form);
        return true;
    }

    @PutMapping("/batch/reset-quote-status")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchResetQuoteStatus(StoreUserFoodService.BatchResetQuoteStatusParam param) {
        storeUserFoodService.batchResetQuoteStatus(param);
        return true;
    }


    @DeleteMapping("/{id}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object delete(@PathVariable long id) {
        storeUserFoodService.deleteById(id);
        return true;
    }

    @PutMapping("/batch/del")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD_BATCH_DELETE)
    public Object batchDelete(StoreUserFoodService.BatchDeleteParam param) {
        if (param.getFoodId() == null && StringUtils.isBlank(param.getFoodCode())) {
            throw new BizException("请输入商品ID或者商品CODE进行批量删除");
        }
        storeUserFoodService.batchDelete(param);
        return true;
    }

    @PutMapping("/proofread/sold-out")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object proofreadSoldOutStoreUserFood(@RequestParam long storeUserId) {
        storeUserFoodService.proofreadSoldOutStoreUserFood(storeUserId);
        return true;
    }

    @PutMapping("/batch-publish")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object batchPublish(StoreUserFoodService.BatchPublishParam param) {
        if (param.getPlat() == null) {
            return "请选择要发布的平台后重新发布";
        } else {
            storeUserFoodService.batchPublish(param);
            return 1;
        }

    }

    @PutMapping("/batch-verify")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD_BATCH_VERIFY)
    public Object batchVerify(StoreUserFoodService.BatchVerifyParam param) {
        storeUserFoodService.batchVerify(param);
        return 1;
    }

    @PostMapping("/detect/change")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object detectChange(@RequestParam(required = false) List<Long> storeUserIdList) {
        if (storeUserIdList == null) {
            storeUserFoodService.detectAllChange();
        } else {
            storeUserFoodService.detectChangeByStoreUserIdList(storeUserIdList);
        }
        return 1;
    }

    @PostMapping("/copy")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object detectSalePriceChange(Long sourceStoreUserId, @RequestParam Long targetStoreUserId) {
        return storeUserFoodService.copyStoreUserFood(sourceStoreUserId, targetStoreUserId);
    }

    @PostMapping("/food-category/publish/{storeUserId}")
    @Permission(value = ManagerPermissions.STORE_USER_FOOD)
    public Object categoryPublish(@PathVariable Long storeUserId, @RequestParam Plat plat) {
        return storeUserFoodService.publishFoodCategory(storeUserId, plat);
    }

    @PostMapping("/setMinCount")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object addFee(long id, Integer count) {
        storeUserFoodService.setMinOrderCount(id, count);
        return true;
    }
}
