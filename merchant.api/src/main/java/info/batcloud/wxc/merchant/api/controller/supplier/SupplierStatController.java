package info.batcloud.wxc.merchant.api.controller.supplier;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.mapper.stat.OrderStatMapper;
import info.batcloud.wxc.core.mapper.stat.domain.FoodSupplierBizData;
import info.batcloud.wxc.core.mapper.stat.domain.SupplierFoodSaleInfo;
import info.batcloud.wxc.merchant.api.controller.supplier.form.FoodSupplierBizDataStatForm;
import info.batcloud.wxc.merchant.api.controller.supplier.form.SupplierFoodSalesStatForm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/food-supplier/stat")
@PreAuthorize("hasRole('FOOD_SUPPLIER')")
public class SupplierStatController {

    @Inject
    private OrderStatMapper orderStatMapper;

    @GetMapping("/food-supplier-biz-data")
    public Object statFoodSupplierBizData(FoodSupplierBizDataStatForm form) {
        Date now = DateUtils.truncate(new Date(), Calendar.DATE);
        Date startTime = null, endTime = now;
        switch (form.getDayType()) {
            case "today":
                startTime = DateUtils.truncate(now, Calendar.DATE);
                break;
            case "last1Days":
                startTime = DateUtils.truncate(DateUtils.addDays(now, -1), Calendar.DATE);
                endTime = DateUtils.addDays(startTime, 1);
                break;
            case "last30Days":
                startTime = DateUtils.addDays(now, -30);
                break;
            case "last7Days":
                startTime = DateUtils.addDays(now, -7);
                break;
            case "customDays":
                startTime = form.getStartTime();
                endTime = DateUtils.addDays(form.getEndTime(), 1);
            default:
                break;
        }
        FoodSupplierBizData data = orderStatMapper.statFoodSupplierDayBizDataByFoodSupplierId(SecurityHelper.loginFoodSupplierId(), startTime, endTime);
        return BusinessResponse.ok(data == null ? new FoodSupplierBizDataStatForm() : data);
    }

    @GetMapping("/food-sales")
    public Object statFoodSales(SupplierFoodSalesStatForm form) {
        Date now = DateUtils.truncate(new Date(), Calendar.DATE);
        Date startTime = null, endTime = now;
        switch (form.getDayType()) {
            case "today":
                startTime = DateUtils.truncate(now, Calendar.DATE);
                break;
            case "last1Days":
                startTime = DateUtils.truncate(DateUtils.addDays(now, -1), Calendar.DATE);
                endTime = DateUtils.addDays(startTime, 1);
                break;
            case "last7Days":
                startTime = DateUtils.addDays(now, -7);
                break;
            case "last30Days":
                startTime = DateUtils.addDays(now, -30);
                break;
            case "customDays":
                startTime = form.getStartTime();
                endTime = DateUtils.addDays(form.getEndTime(), 1);
            default:
                break;
        }
        List<SupplierFoodSaleInfo> list = orderStatMapper.statFoodSaleInfoOfFoodSupplier(SecurityHelper.loginFoodSupplierId(), startTime, endTime);
        return BusinessResponse.ok(list);
    }

}
