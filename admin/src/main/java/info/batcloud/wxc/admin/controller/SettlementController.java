package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.helper.DateHelper;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.SettlementService;
import info.batcloud.wxc.core.service.SystemSettingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/api/settlement")
@RestController
@Permission(value = ManagerPermissions.SETTLEMENT_MANAGE)
public class SettlementController {

    @Inject
    private SettlementService settlementService;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private StoreUserRepository storeUserRepository;

    @PostMapping("/generate-store-user-daily-settlement-sheet-detail")
    public Object generateDailySettlementSheetDetail(@RequestParam(required = false) Long storeUserId, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam Date date) {
        settlementService.generateDailySettlementSheetDetail(storeUserId, date);
        return true;
    }

    @PostMapping("/check-settlement-sheet/{id}")
    public Object recheckSettlementSheet(@PathVariable Long id) {
        settlementService.checkSettlementSheet(id);
        return true;
    }

    @PostMapping("/recheck-settlement-sheet-detail/{id}")
    public Object recheckSettlementSheetDetail(@PathVariable Long id) {
        settlementService.recheckSettlementSheetDetail(id);
        return true;
    }

    @PutMapping("/re-generate-store-user-daily-settlement-sheet-detail/{id}")
    public Object generateDailySettlementSheetDetail(@PathVariable Long id) {
        settlementService.reGenerateDailySettlementSheetDetail(id);
        return true;
    }

    @GetMapping("/batch-re-generate-store-user-daily-settlement-sheet-detail")
    public Object batchGenerateDailySettlementSheetDetail(String date, Integer type) {
        System.out.println(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date time = sdf.parse(date);
            if (type == 0) {
                //一键核对
                settlementService.batchCheck(time);
            } else {
                //一键重检
                settlementService.batchReCheck(time);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    @PostMapping("/generate-week-settlement-sheet")
    public Object generateWeekSettlementSheet(@RequestParam(required = false) Long storeUserId, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam Date date) {
        int[] yw = DateHelper.yearAndWeek(date);
        settlementService.generateWeekSettlementSheet(storeUserId, yw[0], yw[1]);
        return true;
    }

}
