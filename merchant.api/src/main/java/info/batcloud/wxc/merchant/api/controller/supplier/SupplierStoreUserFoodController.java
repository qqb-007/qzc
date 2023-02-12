package info.batcloud.wxc.merchant.api.controller.supplier;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.entity.StoreUserFood;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.repository.StoreUserFoodRepository;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.StoreUserFoodService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/food-supplier/store-user-food")
@PreAuthorize("hasRole('FOOD_SUPPLIER')")
public class SupplierStoreUserFoodController {

    @Inject
    private StoreUserFoodService storeUserFoodService;

    @Inject
    private StoreUserFoodRepository storeUserFoodRepository;

    @GetMapping("/search")
    public Object search(StoreUserFoodService.SearchParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        param.setFoodSupplierId(SecurityHelper.loginFoodSupplierId());
        return BusinessResponse.ok(storeUserFoodService.search(param));
    }

    @PutMapping("/sold-out/{foodId}")
    public Object soldOut(@PathVariable long foodId) {
        return BusinessResponse.ok(storeUserFoodService.soldOut(SecurityHelper.loginStoreUserId(), foodId));
    }

    @PutMapping("/sale/{foodId}")
    public Object sale(@PathVariable long foodId) {
        return BusinessResponse.ok(storeUserFoodService.sale(SecurityHelper.loginStoreUserId(), foodId));
    }

    @PutMapping("/supplier-alter-quote-price/{id}")
    @PreAuthorize("hasRole('FOOD_SUPPLIER')")
    public Object changeSupplierAlterQuotePrice(@PathVariable long id, @RequestBody SupplierQuotePriceParam param) {
        return BusinessResponse.ok(storeUserFoodService.changeSupplierAlterQuotePrice(SecurityHelper.loginFoodSupplierId(), id, param.getAlterQuotePrice()));
    }

    @PostMapping
    @PreAuthorize("hasRole('FOOD_SUPPLIER')")
    public Object supplierAdd(@RequestBody StoreUserFoodService.SaveParam param) {
        return BusinessResponse.ok(storeUserFoodService.saveSupplierStoreUserFood(SecurityHelper.loginFoodSupplierId(), param));
    }

    @PutMapping("/special-sku/{id}")
    public Object setSpecialSku(@PathVariable long id, @RequestBody SetSpecialSkuParam param) {
        StoreUserFood suf = storeUserFoodRepository.findOne(id);
        Assert.isTrue(suf.getStoreUser().getId().equals(SecurityHelper.loginStoreUserId()), "");
        storeUserFoodService.setSpecialSkuList(id, param.getSpecialSkuIdList() == null ? new ArrayList<>(0) : param.getSpecialSkuIdList());
        storeUserFoodService.setEleSkuId(id, param.getEleSkuId());
        return BusinessResponse.ok(true);
    }

    public static class SetSpecialSkuParam {
        private List<String> specialSkuIdList;
        private String eleSkuId;

        public List<String> getSpecialSkuIdList() {
            return specialSkuIdList;
        }

        public void setSpecialSkuIdList(List<String> specialSkuIdList) {
            this.specialSkuIdList = specialSkuIdList;
        }

        public String getEleSkuId() {
            return eleSkuId;
        }

        public void setEleSkuId(String eleSkuId) {
            this.eleSkuId = eleSkuId;
        }
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
