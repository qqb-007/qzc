package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.dto.SettlementSheetDTO;
import info.batcloud.wxc.core.service.SettlementSheetService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RequestMapping("/api/settlement-sheet")
@RestController
@Permission(value = ManagerPermissions.SETTLEMENT_MANAGE)
public class SettlementSheetController {

    @Inject
    private SettlementSheetService settlementSheetService;

    @GetMapping("/search")
    public Object search(SettlementSheetService.SearchParam param) {
        return settlementSheetService.search(param);
    }

    @GetMapping("/{id}")
    public Object info(@PathVariable long id) {
        SettlementSheetDTO dto = settlementSheetService.findById(id);
        return dto;
    }

    @PutMapping("/settle/{id}")
    public Object settle(@PathVariable long id) {
        return settlementSheetService.settleToWallet(id);
    }
}
