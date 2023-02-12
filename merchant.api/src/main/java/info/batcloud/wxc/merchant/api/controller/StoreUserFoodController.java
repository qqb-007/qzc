package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.StoreUserFoodDTO;
import info.batcloud.wxc.core.entity.Food;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.repository.FoodRepository;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

@RestController
@RequestMapping("/store-user-food")
@PreAuthorize("isAuthenticated()")
public class StoreUserFoodController {

    @Inject
    private StoreUserFoodService storeUserFoodService;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @Inject
    private FoodRepository foodRepository;

    private static final Logger logger = LoggerFactory.getLogger(StoreUserFoodController.class);

    @PutMapping("/sold-out/{foodId}")
    public Object soldOut(@PathVariable long foodId) {
        logger.info("接收到商家下架商品请求 " + SecurityHelper.loginStoreUserId() + "  " + foodId);
        return BusinessResponse.ok(storeUserFoodService.soldOut(SecurityHelper.loginStoreUserId(), foodId));
    }

    @PutMapping("/sale/{foodId}")
    public Object sale(@PathVariable long foodId) {
        logger.info("接收到商家上架商品请求 " + SecurityHelper.loginStoreUserId() + " " + foodId);
        StoreUserFoodDTO foodDTO = storeUserFoodService.findByStoreUserIdAndFoodId(SecurityHelper.loginStoreUserId(), foodId);
        if (foodDTO.getFood().getQuotable()) {
            return BusinessResponse.ok(storeUserFoodService.sale(SecurityHelper.loginStoreUserId(), foodId));
        } else {
            throw new BizException("活动商品请联系客服上架");
        }

    }

    @PostMapping("")
    public Object add(StoreUserFoodService.SaveParam param) {
        param.setFoodSupplierId(SecurityHelper.loginFoodSupplierId());
        Food food = foodRepository.findOne(param.getFoodId());
        if(food!= null && food.getQuotable()){
            return BusinessResponse.ok(storeUserFoodService.saveStoreUserFood(SecurityHelper.loginStoreUserId(), param));
        }else {
            throw new BizException("活动商品请联系客服添加");
        }

    }

    @GetMapping("/search")
    public Object search(StoreUserFoodService.SearchParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(storeUserFoodService.search(param));
    }

    @GetMapping("/count")
    public Object search(String categoryName) {
//        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(storeUserFoodService.countCategoryStoreUserFood(SecurityHelper.loginStoreUserId(), categoryName));
    }

    @PutMapping("/alter-quote-price/{id}")
    @PreAuthorize("hasRole('STORE_USER')")
    public Object changeAlterQuotePrice(@PathVariable long id, @RequestParam float alterQuotePrice) {
        logger.info("接收到商家改价请求 " + SecurityHelper.loginStoreUserId() + " " + id + "  " + alterQuotePrice);
        return BusinessResponse.ok(storeUserFoodService.changeAlterQuotePrice(SecurityHelper.loginStoreUserId(), id, alterQuotePrice));
    }

    @PutMapping("/supplier-alter-quote-price/{id}")
    @PreAuthorize("hasRole('FOOD_SUPPLIER')")
    public Object changeSupplierAlterQuotePrice(@PathVariable long id, @RequestBody SupplierQuotePriceParam param) {
        return BusinessResponse.ok(storeUserFoodService.changeSupplierAlterQuotePrice(SecurityHelper.loginFoodSupplierId(), id, param.getAlterQuotePrice()));
    }

    @PutMapping("/special-sku/{id}")
    public Object setSpecialSku(@PathVariable long id, @RequestParam(required = false) List<String> specialSkuIdList,
                                @RequestParam(required = false) String eleSkuId) {
        StoreUserFood suf = storeUserFoodRepository.findOne(id);
        Assert.isTrue(suf.getStoreUser().getId().equals(SecurityHelper.loginStoreUserId()), "");
        storeUserFoodService.setSpecialSkuList(id, specialSkuIdList == null ? new ArrayList<>(0) : specialSkuIdList);
        storeUserFoodService.setEleSkuId(id, eleSkuId);
        return BusinessResponse.ok(true);
    }

    @GetMapping("/sku/{id}")
    public Object skuInfo(@PathVariable long id) {
        StoreUserFoodDTO suf = storeUserFoodService.findByStoreUserIdAndId(SecurityHelper.loginStoreUserId(), id);
        Map<String, Object> map = new HashMap<>(3);
        map.put("specialSkuIdList", StringUtils.isNotBlank(suf.getSpecialSkuIdList()) ? Arrays.asList(suf.getSpecialSkuIdList().split(",")) : Collections.EMPTY_LIST);
        map.put("eleSkuId", suf.getEleSkuId());
        map.put("foodSkuList", suf.getFood().getSkuList());
        map.put("foodId", suf.getFood().getId());
        map.put("foodName", suf.getFood().getName());
        return BusinessResponse.ok(map);
    }

    @GetMapping("/saleTime/{id}")
    public Object saleInfo(@PathVariable long id) {
        return BusinessResponse.ok(storeUserFoodService.getSaleTime(id));
    }

    @PutMapping("/sale-time-set/{id}")
    @PreAuthorize("hasRole('STORE_USER')")
    public Object setSaleTime(@PathVariable long id, @RequestParam String saleTime) {
        logger.info("接收到商家改售卖时间请求 " + SecurityHelper.loginStoreUserId() + " " + id + "  " + saleTime);
        storeUserFoodService.setSaleTime(id, saleTime);
        return BusinessResponse.ok("");
    }

    public static class SupplierQuotePriceParam {
        private float alterQuotePrice;

        public float getAlterQuotePrice() {
            return alterQuotePrice;
        }

        public void setAlterQuotePrice(float alterQuotePrice) {
            this.alterQuotePrice = alterQuotePrice;
        }
    }
}
