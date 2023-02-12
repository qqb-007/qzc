package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.service.SettlementSheetDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RequestMapping("/api/settlement-sheet-detail")
@RestController
@Permission(value = ManagerPermissions.SETTLEMENT_SHEET_DETAIL_LIST)
public class SettlementSheetDetailController {

    @Inject
    private SettlementSheetDetailService settlementSheetDetailService;

    @GetMapping("/search")
    public Object search(SettlementSheetDetailService.SearchParam param) {
        return settlementSheetDetailService.search(param);
    }

}
