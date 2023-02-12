package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.constants.MessageKeyConstants;
import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.StoreUserWithdrawService;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.settings.WithdrawSetting;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/withdraw")
@PreAuthorize("hasRole('STORE_USER')")
public class WithdrawController {

    @Inject
    private StoreUserWithdrawService userWithdrawService;

    @Inject
    private SystemSettingService systemSettingService;

    @PostMapping
    public Object withdraw(StoreUserWithdrawService.StoreUserWithdrawParams params) {
        WithdrawSetting setting = systemSettingService.findActiveSetting(WithdrawSetting.class);
        if (!setting.isOpenWithdraw()) {
            throw new BizException(MessageKeyConstants.WITHDRAW_IS_CLOSED);
        }
        userWithdrawService.withdraw(SecurityHelper.loginStoreUserId(), params);
        return BusinessResponse.ok(true);
    }

}
