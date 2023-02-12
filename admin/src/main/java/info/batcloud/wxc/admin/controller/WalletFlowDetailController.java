package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.enums.WalletFlowDetailType;
import info.batcloud.wxc.core.enums.WalletValueType;
import info.batcloud.wxc.core.service.WalletFlowDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/wallet-flow-detail")
@Permission(value = ManagerPermissions.WALLET)
public class WalletFlowDetailController {

    @Inject
    private WalletFlowDetailService walletFlowDetailService;

    @GetMapping("/search")
    public Object search(WalletFlowDetailService.SearchParam param) {
        return walletFlowDetailService.search(param);
    }

    //导出钱包进账明细
    @GetMapping("/export")
    public Object export(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date time = sdf.parse(date);
            WalletFlowDetailService.SearchParam searchParam = new WalletFlowDetailService.SearchParam();
            searchParam.setCreateTime(time);
            searchParam.setType(WalletFlowDetailType.MERGE_ORDER_SETTLE);
            searchParam.setValueType(WalletValueType.MONEY);
            walletFlowDetailService.export(searchParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
