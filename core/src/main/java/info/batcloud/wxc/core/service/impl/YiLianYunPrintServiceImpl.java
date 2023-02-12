package info.batcloud.wxc.core.service.impl;

import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.helper.YiLianYunHelper;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.PrinterService;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.service.YilainyunPrintService;
import info.batcloud.wxc.core.printers.yilianyun.YilianyunAPI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.concurrent.ThreadPoolExecutor;
@Service
public class YiLianYunPrintServiceImpl implements YilainyunPrintService {

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private ThreadPoolExecutor threadPoolExecutor;

    private static final Logger logger = LoggerFactory.getLogger(YiLianYunPrintServiceImpl.class);

    @Override
    public boolean delPrinter(long storeUserId) {
        StoreUser user = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isEmpty(user.getMachineCode())) {
            logger.error("该商家未绑定易联云打印机");
            return false;
        }

        YilianyunAPI.delResult delResult = YilianyunAPI.delPrinter(user.getAccessToken(), user.getMachineCode());
        logger.info("解绑结果" + delResult.toString());
        user.setMachineCode(null);
        user.setAccessToken(null);
        user.setRefreshToken(null);
        storeUserRepository.save(user);
        return true;
    }

    @Override
    public boolean addPrinter(long storeUserId, String machineCode, String msign) {
        //首先试着删除打印机
        delPrinter(storeUserId);
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        YilianyunAPI.addResult addResult = YilianyunAPI.addPrinter(machineCode, msign);
        if (addResult.getError() == 0) {
            YilianyunAPI.Body body = addResult.getBody();
            storeUser.setMachineCode(body.getMachine_code());
            storeUser.setAccessToken(body.getAccess_token());
            storeUser.setRefreshToken(body.getRefresh_token());
            storeUserRepository.save(storeUser);
            this.testPrinter(storeUserId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean testPrinter(long storeUserId) {
        String testContent = "<FS><center>王小菜测试打印</center></FS>";
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isEmpty(storeUser.getMachineCode())) {
            logger.error("该商家未绑定易联云打印机");
            return false;
        }
        YilianyunAPI.print(storeUser.getAccessToken(), testContent, "001", storeUser.getMachineCode());
        return true;
    }

    @Override
    public boolean print(long storeUserId, PrinterService.OrderInfo orderInfo) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        YilianyunAPI.printResult print = YilianyunAPI.print(storeUser.getAccessToken(), YiLianYunHelper.toContent(orderInfo), orderInfo.getOrderId(), storeUser.getMachineCode());

        return print.getError() == 0;
    }

    @Override
    public boolean getToken(long storeUserId) {
        StoreUser storeUser = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isEmpty(storeUser.getMachineCode())) {
            logger.error("该商家未绑定易联云打印机,无法重新获取token");
            return false;
        }
        YilianyunAPI.TokenResult token = YilianyunAPI.getToken(storeUser.getRefreshToken());
        if (token.getError() == 0) {
            storeUser.setAccessToken(token.getBody().getAccess_token());
            storeUser.setRefreshToken(token.getBody().getRefresh_token());
            storeUserRepository.save(storeUser);
        }
        return true;
    }
}
