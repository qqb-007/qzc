package info.batcloud.wxc.core.service;

public interface ZhongWuPrintService {
    boolean print(long storeUserId, PrinterService.OrderInfo orderInfo);
    boolean addPrinter(long storeUserId, String deviceid, String devicesecret);
    boolean testPrinter(long storeUserId);
}
