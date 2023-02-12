package info.batcloud.wxc.core.service;

import info.batcloud.wxc.core.enums.Plat;

import java.util.Date;
import java.util.List;

public interface YilainyunPrintService {
    boolean delPrinter(long storeUserId);

    boolean addPrinter(long storeUserId, String machineCode, String msign);

    boolean testPrinter(long storeUserId);

    boolean print(long storeUserId, PrinterService.OrderInfo orderInfo);

    boolean getToken(long storeUserId);

}
