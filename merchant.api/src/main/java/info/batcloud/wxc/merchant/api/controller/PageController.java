package info.batcloud.wxc.merchant.api.controller;

import com.ctospace.archit.common.pagination.Paging;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.FoodDTO;
import info.batcloud.wxc.core.dto.SettlementSheetDetailDTO;
import info.batcloud.wxc.core.dto.StoreUserWithdrawDTO;
import info.batcloud.wxc.core.helper.DateHelper;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.mapper.stat.OrderStatMapper;
import info.batcloud.wxc.core.mapper.stat.domain.StoreUserBizData;
import info.batcloud.wxc.core.service.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/page")
@PreAuthorize("isAuthenticated()")
public class PageController {

    @Inject
    private OrderStatMapper orderStatMapper;

    @Inject
    private StoreUserService storeUserService;

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
        long storeUserId = SecurityHelper.loginStoreUserId();
        StoreUserBizData bizData = orderStatMapper.statStoreUserDayBizDataByStoreUserId(storeUserId, today, tomorrow);
        Map<String, Object> rs = new HashMap<>();
        rs.put("bizData", bizData == null ? new StoreUserBizData() : bizData);
        rs.put("wallet", walletService.findByStoreUserId(SecurityHelper.loginStoreUserId()));
        rs.put("startDate", DateFormatUtils.format(DateUtils.addDays(today, -7), "yyyy-MM-dd"));
        rs.put("endDate", DateFormatUtils.format(DateUtils.addDays(today, -1), "yyyy-MM-dd"));
        return BusinessResponse.ok(rs);
    }

    @GetMapping("/my")
    public Object my() {
        Map<String, Object> rs = new HashMap<>();
        rs.put("storeUser", storeUserService.findById(SecurityHelper.loginStoreUserId()));
        return BusinessResponse.ok(rs);
    }

    @GetMapping("/store-user-food-list")
    public Object storeUserFoodList() {
        Map<String, Object> rs = new HashMap<>(50);
        Long foodSupplierId = SecurityHelper.loginFoodSupplierId();
        if (foodSupplierId != null) {
            rs.put("foodCategoryList", storeUserFoodService.aggregateCategoryOfFoodSupplier(foodSupplierId));
        } else {
            rs.put("foodCategoryList", foodCategoryService.findSortedList().stream().map(o -> o.getName())
                    .collect(Collectors.toList()));
        }
        return BusinessResponse.ok(rs);
    }

    @GetMapping("/supplier-food-list")
    public Object supplierFoodList(@RequestParam long foodSupplierId) {
        Map<String, Object> rs = new HashMap<>(50);
        rs.put("foodCategoryList", storeUserFoodService.aggregateCategoryOfFoodSupplier(foodSupplierId));
        return BusinessResponse.ok(rs);
    }

    @GetMapping("/withdraw")
    public Object withdraw() {
        Map<String, Object> rs = new HashMap<>(2);
        rs.put("storeUser", storeUserService.findById(SecurityHelper.loginStoreUserId()));
        rs.put("wallet", walletService.findByStoreUserId(SecurityHelper.loginStoreUserId()));
        return BusinessResponse.ok(rs);
    }

    @Inject
    private StoreUserWithdrawService userWithdrawService;

    @GetMapping("/withdraw/{id}")
    public Object data(@PathVariable long id) {

        Map<String, Object> map = new HashMap<>();
        StoreUserWithdrawDTO userWithdraw = userWithdrawService.findById(id);
        map.put("withdraw", userWithdraw);
        return BusinessResponse.ok(map);
    }

    @GetMapping("/withdraw/wallet-flow-detail/{walletFlowDetailId}")
    public Object withdraw(@PathVariable long walletFlowDetailId) {

        Map<String, Object> map = new HashMap<>();
        StoreUserWithdrawDTO userWithdraw = userWithdrawService.findByWalletFlowDetailId(walletFlowDetailId);
        map.put("withdraw", userWithdraw);
        return BusinessResponse.ok(map);
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

    @GetMapping("/settlement-sheet-list")
    public Object settlementSheetList() {

        Map<String, Object> map = new HashMap<>();
        SettlementSheetDetailService.SearchParam param = new SettlementSheetDetailService.SearchParam();
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        int[] yw = DateHelper.yearAndWeek(new Date());
        param.setWeek(yw[1]);
        param.setYear(yw[0]);
        param.setPageSize(100);
        Paging<SettlementSheetDetailDTO> paging = settlementSheetDetailService.search(param);
        map.put("weekSettlementSheetDetailList", paging.getResults());
        return BusinessResponse.ok(map);
    }

}
