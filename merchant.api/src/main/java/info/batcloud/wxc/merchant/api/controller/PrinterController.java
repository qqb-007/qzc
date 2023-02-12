package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.PrinterService;
import info.batcloud.wxc.core.service.XinYeYunService;
import info.batcloud.wxc.core.service.YilainyunPrintService;
import info.batcloud.wxc.core.service.ZhongWuPrintService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RequestMapping("/printer")
@RestController
@PreAuthorize("hasRole('STORE_USER')")
public class PrinterController {

    @Inject
    private PrinterService printerService;

    @Inject
    private YilainyunPrintService yilainyunPrintService;

    @Inject
    private ZhongWuPrintService zhongWuPrintService;

    @Inject
    private XinYeYunService xinYeYunService;

    @PostMapping("")
    public Object bindPrinter(@RequestParam String sn, @RequestParam String key) {
        return BusinessResponse.ok(printerService.addPrinter(SecurityHelper.loginStoreUserId(), sn, key));
    }

    @PostMapping("/test/{sn}")
    public Object testPrinter(@PathVariable String sn) {
        if (sn.equals("test")) {
            return BusinessResponse.ok(yilainyunPrintService.testPrinter(SecurityHelper.loginStoreUserId()));
        } else if (sn.equals("zw")) {
            return BusinessResponse.ok(zhongWuPrintService.testPrinter(SecurityHelper.loginStoreUserId()));
        } else if (sn.equals("xx")) {
            return BusinessResponse.ok(xinYeYunService.testPrinter(SecurityHelper.loginStoreUserId()));
        } else {
            return BusinessResponse.ok(printerService.testPrinter(sn));
        }


    }

    @PostMapping("bindYlPrinter")
    public Object bindYlPrinter(@RequestParam String machineCode, @RequestParam String msign) {
        return BusinessResponse.ok(yilainyunPrintService.addPrinter(SecurityHelper.loginStoreUserId(), machineCode, msign));
    }

    @PostMapping("bindZwPrinter")
    public Object bindZwPrinter(@RequestParam String deviceid, @RequestParam String screct) {
        return BusinessResponse.ok(zhongWuPrintService.addPrinter(SecurityHelper.loginStoreUserId(), deviceid, screct));
    }

    @PostMapping("bindXxPrinter")
    public Object bindXxPrinter(@RequestParam String sn) {
        return BusinessResponse.ok(xinYeYunService.addPrinter(SecurityHelper.loginStoreUserId(), sn));
    }
}
