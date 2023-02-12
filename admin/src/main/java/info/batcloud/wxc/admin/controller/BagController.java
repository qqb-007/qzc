package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.service.BagService;
import info.batcloud.wxc.core.service.StoreUserWithdrawService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bag")
@Permission(value = ManagerPermissions.WITHDRAW)
public class BagController {

    @Inject
    private BagService bagService;

    @GetMapping("/export")
    public Object export(BagService.SearchParam param) {
        BagService.ExportParam exportParam = new BagService.ExportParam();
        exportParam.setEndTime(param.getCreateEndDate());
        exportParam.setStartTime(param.getCreateStartDate());
        return bagService.export(exportParam);
    }

    @GetMapping("/download")
    public Object download() {
        Map<String, Object> rs = new HashMap<>();
        rs.put("file", bagService.download());
        return rs;
    }

    @GetMapping("/search")
    public Object search(BagService.SearchParam param) {
        return bagService.search(param);
    }

    @PutMapping("/verify")
    public Object verify(StoreUserWithdrawService.VerifyParam param) {
        //userWithdrawService.verify(param);
        if (param.isSuccess()) {
            bagService.checkApply(param.getId(), param.getRemark());
        } else {
            bagService.rejectApply(param.getId(), param.getRemark());
        }
        return 1;
    }

    @PostMapping("/manual-fund-transfer")
    public Object manualFundTransfer(BagService.AddWuLiuParam param) {
        bagService.addWuLiu(param.getId(), param.getWuliuType() + ": " + param.getWuliu(), param.getRemark());
        return 1;
    }

    @PostMapping("/update")
    public Object update(BagService.UpdataParam param) {
        bagService.update(param.getId(), param.getNum());
        return 1;
    }

}
