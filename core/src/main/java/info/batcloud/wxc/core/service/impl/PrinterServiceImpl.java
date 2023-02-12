package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.printers.feieyun.FeieyunAPI;
import info.batcloud.wxc.core.helper.PrintHelper;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.PrinterService;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.settings.FeieyunSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class PrinterServiceImpl implements PrinterService {

    @Inject
    private StoreUserRepository storeUserRepository;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private ThreadPoolExecutor threadPoolExecutor;


    private static final Logger logger = LoggerFactory.getLogger(PrinterServiceImpl.class);

    @Override
    public boolean delPrinter(long storeUserId) {
        StoreUser user = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isEmpty(user.getFeiePrinterSn())) {
            logger.error("该商家未绑定飞蛾打印机");
            return false;
        }
        FeieyunSetting activeSetting = systemSettingService.findActiveSetting(FeieyunSetting.class);
        String cleanSn = getSn(user.getFeiePrinterSn());
        FeieyunAPI.Result<FeieyunAPI.OkNoData> result = FeieyunAPI.delPrinter(activeSetting.getUser(), activeSetting.getUkey(), cleanSn);
        logger.info("解绑结果" + result.toString());
        user.setFeiePrinterSn(null);
        storeUserRepository.save(user);
        return true;
    }

    @Override
    public boolean addPrinter(long storeUserId, String sn, String key) {
        FeieyunSetting setting = systemSettingService.findActiveSetting(FeieyunSetting.class);
        //首先试着删除打印机
        String cleanSn = getSn(sn);
        FeieyunAPI.delPrinter(setting.getUser(), setting.getUkey(), cleanSn);
        StoreUser _su = storeUserRepository.findByFeiePrinterSn(cleanSn);
        if (_su != null) {
            _su.setFeiePrinterSn(null);
            storeUserRepository.save(_su);
        }
        FeieyunAPI.Result<FeieyunAPI.OkNoData> result = FeieyunAPI.addPrinter(setting.getUser(), setting.getUkey(), sn + "#" + key + "##");
        int ret = result.getRet();
        if (ret == 0) {
            FeieyunAPI.OkNoData data = result.getData();
            List<String> ok = data.getOk();
            boolean flag = false;
            for (int i = 0; i < ok.size(); i++) {
                if (sn.equals(ok.get(i).split("\\#")[0])) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                //说明添加成功
                StoreUser su = storeUserRepository.findOne(storeUserId);
                su.setFeiePrinterSn(cleanSn);
                storeUserRepository.save(su);
                //测试打印
                this.testPrinter(cleanSn);
            }
            return flag;
        } else {
            return false;
        }
    }

    @Override
    public boolean testPrinter(String sn) {
        String testContent = "<CB>王小菜测试打印</CB>";
        FeieyunSetting setting = systemSettingService.findActiveSetting(FeieyunSetting.class);
        FeieyunAPI.Result<String> result = FeieyunAPI.print(setting.getUser(), setting.getUkey(), sn, testContent);
        if (result.getRet() == 0) {
            return true;
        }
        logger.warn(result.getMsg());
        return false;
    }

    @Override
    public boolean print(String sn, String content) {
        FeieyunSetting setting = systemSettingService.findActiveSetting(FeieyunSetting.class);
        FeieyunAPI.StringResult result = FeieyunAPI.print(setting.getUser(), setting.getUkey(), getSn(sn), content);
        logger.info("打印返回：" + JSON.toJSONString(result));
        return result.getRet() == 0;
    }

    @Override
    public boolean print(String sn, OrderInfo orderInfo) {
        return print(sn, PrintHelper.toContent(orderInfo));
    }

    @Override
    public boolean print(String sn, CancelInfo cancelInfo) {
        return print(sn, PrintHelper.toCancelContent(cancelInfo));
    }

    @Override
    public void queuePrint(String sn, OrderInfo orderInfo) {
        FeieyunSetting setting = systemSettingService.findActiveSetting(FeieyunSetting.class);
        threadPoolExecutor.execute(new PrintRunnable(sn, PrintHelper.toContent(orderInfo), setting.getUser(), setting.getUkey(), 10, threadPoolExecutor));
    }

    class PrintRunnable implements Runnable {

        private int tryTimes = 0;

        private int maxTryTimes;

        private String content;

        private String sn;

        private String user;

        private String ukey;

        private ThreadPoolExecutor threadPoolExecutor;

        PrintRunnable(String sn, String content, String user, String ukey, int maxTryTimes, ThreadPoolExecutor threadPoolExecutor) {
            this.sn = sn;
            this.content = content;
            this.maxTryTimes = maxTryTimes;
            this.user = user;
            this.ukey = ukey;
            this.threadPoolExecutor = threadPoolExecutor;
        }


        @Override
        public void run() {
            FeieyunAPI.StringResult result = FeieyunAPI.print(user, ukey, getSn(sn), content);
            if (result.getRet() != 0 && tryTimes < maxTryTimes) {
                //try
                threadPoolExecutor.execute(this);
            }
        }
    }

    private String getSn(String sn) {
        if (StringUtils.isEmpty(sn)) {
            FeieyunSetting setting = systemSettingService.findActiveSetting(FeieyunSetting.class);
            return setting.getSn();
        }
        return sn.split("\\#")[0];
    }
}
