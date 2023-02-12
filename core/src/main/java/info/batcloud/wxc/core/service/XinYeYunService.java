package info.batcloud.wxc.core.service;

public interface XinYeYunService {
    boolean delPrinter(long storeUserId);

    boolean addPrinter(long storeUserId, String syySn);

    boolean testPrinter(long  storeUserId);

    boolean print(long storeUserId, PrinterService.OrderInfo orderInfo);

}
