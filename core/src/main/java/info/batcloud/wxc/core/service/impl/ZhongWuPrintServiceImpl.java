package info.batcloud.wxc.core.service.impl;

import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.helper.ZhongWuHelper;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.PrinterService;
import info.batcloud.wxc.core.service.ZhongWuPrintService;
import info.batcloud.wxc.core.printers.zhongwu.ZhongwuAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ZhongWuPrintServiceImpl implements ZhongWuPrintService {
    @Inject
    private StoreUserRepository storeUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(ZhongWuPrintServiceImpl.class);

    @Override
    public boolean print(long storeUserId, PrinterService.OrderInfo orderInfo) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        ZhongwuAPI.printResult print = ZhongwuAPI.print(storeUser.getDevideid(), ZhongWuHelper.toContent(orderInfo), storeUser.getDevicesecret());

        return print.getRetData().getStatus() == "1";
        //return false;
    }

    @Override
    public boolean addPrinter(long storeUserId, String deviceid, String devicesecret) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        storeUser.setDevideid(deviceid);
        storeUser.setDevicesecret(devicesecret);
        testPrinter(storeUserId);
        storeUserRepository.save(storeUser);
        return false;
    }

    @Override
    public boolean testPrinter(long storeUserId) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        String test = "王小菜测试打印";
        ZhongwuAPI.print(storeUser.getDevideid(), test, storeUser.getDevicesecret());
        return true;
    }
}
