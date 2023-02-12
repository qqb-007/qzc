package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/wallet")
@Permission(value = ManagerPermissions.WALLET)
public class WalletController {

    @Inject
    private WalletService walletService;

    @GetMapping("/search")
    public Object search(WalletService.SearchParam param) {
        return walletService.search(param);
    }

}
