package info.batcloud.wxc.merchant.api.controller.supplier;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.FoodDTO;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.mapper.stat.OrderStatMapper;
import info.batcloud.wxc.core.mapper.stat.domain.FoodSupplierBizData;
import info.batcloud.wxc.core.mapper.stat.domain.StoreUserBizData;
import info.batcloud.wxc.core.service.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/food-supplier/page")
@PreAuthorize("hasRole('FOOD_SUPPLIER')")
public class SupplierPageController {

    @Inject
    private OrderStatMapper orderStatMapper;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private FoodSupplierService foodSupplierService;

    @Inject
    private FoodService foodService;

    @Inject
    private WalletService walletService;

    @Inject
    private StoreUserFoodService storeUserFoodService;

    @Inject
    private FoodCategoryService foodCategoryService;

    @Inject
    private SettlementSheetDetailService settlementSheetDetailService;

    @GetMapping("/index")
    public Object index() {
        Date today = DateUtils.truncate(new Date(), Calendar.DATE);
        Date tomorrow = DateUtils.addDays(today, 1);
        long foodSupplierId = SecurityHelper.loginFoodSupplierId();
        FoodSupplierBizData bizData = orderStatMapper.statFoodSupplierDayBizDataByFoodSupplierId(foodSupplierId, today, tomorrow);
        Map<String, Object> rs = new HashMap<>();
        rs.put("bizData", bizData == null ? new FoodSupplierBizData() : bizData);
        rs.put("startDate", DateFormatUtils.format(DateUtils.addDays(today, -7), "yyyy-MM-dd"));
        rs.put("endDate", DateFormatUtils.format(DateUtils.addDays(today, -1), "yyyy-MM-dd"));
        return BusinessResponse.ok(rs);
    }

    @GetMapping("/my")
    public Object my() {
        Map<String, Object> rs = new HashMap<>(1);
        rs.put("foodSupplier", foodSupplierService.findById(SecurityHelper.loginFoodSupplierId()));
        return BusinessResponse.ok(rs);
    }

    @GetMapping("/store-user-food-list")
    public Object quote() {
        Map<String, Object> rs = new HashMap<>(50);
        Long foodSupplierId = SecurityHelper.loginFoodSupplierId();
        rs.put("foodCategoryList", storeUserFoodService.aggregateCategoryOfFoodSupplier(foodSupplierId));
        return BusinessResponse.ok(rs);
    }

    @GetMapping("/store-food-application/apply")
    public Object storeFoodApplicationApply(@RequestParam long foodId) {

        Map<String, Object> map = new HashMap<>();
        FoodDTO food = foodService.findById(foodId);
        map.put("foodName", food.getName());
        map.put("foodPicture", food.getPicture());
        map.put("foodPrice", food.getPrice());
        map.put("foodUnit", food.getUnit());
        return BusinessResponse.ok(map);
    }

}
