package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.service.StoreUserWithdrawService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;

@RestController
@RequestMapping("/api/withdraw")
@Permission(value = ManagerPermissions.WITHDRAW)
public class WithdrawController {

    @Inject
    private StoreUserWithdrawService userWithdrawService;

    @GetMapping("/search")
    public Object search(StoreUserWithdrawService.SearchParam param) {
        return userWithdrawService.search(param);
    }

    @GetMapping("/batchCheck")
    public Object batchCheck(StoreUserWithdrawService.SearchParam param) {
        userWithdrawService.BatchCheck(param);
        return true;
    }

    @GetMapping("/export")
    public Object export(StoreUserWithdrawService.SearchParam param) {
        try {
            userWithdrawService.export(param);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @PutMapping("/verify")
    public Object verify(StoreUserWithdrawService.VerifyParam param) {
        userWithdrawService.verify(param);
        return 1;
    }

    @PostMapping("/fund-transfer/{id}")
    public Object fundTransfer(@PathVariable long id) {
        return userWithdrawService.withdrawFundTransferById(id);
    }

    @PostMapping("/manual-fund-transfer")
    public Object manualFundTransfer(StoreUserWithdrawService.ManualFundTransferParam param, BindingResult bindingResult) {
        if (StringUtils.isBlank(param.getAlipayOrderId())) {
            param.setAlipayOrderId("0000000000");
        }
        if (bindingResult.hasErrors()) {
            throw new BizException("参数错误");
        }
        return userWithdrawService.withdrawManualFundTransfer(param);
    }

    @GetMapping("/Translate")
    public Object batchTransfer() {
        userWithdrawService.BatchTransfer();
        return true;
    }

    @GetMapping("/withdraw")
    public Object withdraw(Long storeUserId) {
        //userWithdrawService.BatchTransfer();
        userWithdrawService.withdraw(storeUserId);
        return true;
    }


}
