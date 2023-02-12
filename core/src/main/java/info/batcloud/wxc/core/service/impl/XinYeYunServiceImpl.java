package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import info.batcloud.wxc.core.entity.StoreUser;
import info.batcloud.wxc.core.helper.PrintHelper;
import info.batcloud.wxc.core.printers.xinyeyun.opensdk.util.Config;
import info.batcloud.wxc.core.printers.xinyeyun.opensdk.util.HttpClientUtil;
import info.batcloud.wxc.core.printers.xinyeyun.opensdk.vo.*;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.PrinterService;
import info.batcloud.wxc.core.service.XinYeYunService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class XinYeYunServiceImpl implements XinYeYunService {

    private static String BASE_URL = "https://open.xpyun.net/api/openapi";


    @Inject
    private StoreUserRepository storeUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(XinYeYunService.class);

    @Override
    public boolean delPrinter(long storeUserId) {
        StoreUser user = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isEmpty(user.getXyySn())) {
            logger.error("该商家未绑定芯烨云打印机");
            return false;
        }
        DelPrinterRequest restRequest = new DelPrinterRequest();
        String[] s = new String[1];
        s[0] = user.getXyySn();
        restRequest.setSnlist(s);
        Config.createRequestHeader(restRequest);
        String url = BASE_URL + "/xprinter/delPrinters";
        String jsonRequest = JSON.toJSONString(restRequest);
        String resp = HttpClientUtil.doPostJSON(url, jsonRequest);
        ObjectRestResponse<PrinterResult> result = JSON.parseObject(resp, new TypeReference<ObjectRestResponse<PrinterResult>>() {
        });
        logger.error("芯烨云删除打印机返回" + result.toString());
        if (result.getCode() == 0) {
            user.setXyySn(null);
            storeUserRepository.save(user);
        }
        return result.getCode() == 0;
    }

    @Override
    public boolean addPrinter(long storeUserId, String xyySn) {
        //首先试着删除打印机
        delPrinter(storeUserId);
        StoreUser user = storeUserRepository.findOne(storeUserId);
        AddPrinterRequest restRequest = new AddPrinterRequest();
        AddPrinterRequestItem item = new AddPrinterRequestItem();
        item.setName(user.getName());
        item.setSn(xyySn);
        AddPrinterRequestItem[] list = new AddPrinterRequestItem[1];
        list[0] = item;
        restRequest.setItems(list);
        Config.createRequestHeader(restRequest);
        String url = BASE_URL + "/xprinter/addPrinters";
        String jsonRequest = JSON.toJSONString(restRequest);
        String resp = HttpClientUtil.doPostJSON(url, jsonRequest);
        ObjectRestResponse<PrinterResult> result = JSON.parseObject(resp, new TypeReference<ObjectRestResponse<PrinterResult>>() {
        });
        logger.info("添加打印机返回" + result.toString());
        if (result.getCode() == 0) {
            user.setXyySn(xyySn);
            storeUserRepository.save(user);
            this.testPrinter(user.getId());
        }
        return result.getCode() == 0;
    }

    @Override
    public boolean testPrinter(long storeUserId) {
        StoreUser user = storeUserRepository.findOne(storeUserId);
        PrintRequest restRequest = new PrintRequest();
        restRequest.setSn(user.getXyySn());
        restRequest.setContent("<CB>王小菜测试打印</CB>");
        Config.createRequestHeader(restRequest);
        String url = BASE_URL + "/xprinter/print";
        String jsonRequest = JSON.toJSONString(restRequest);
        String resp = HttpClientUtil.doPostJSON(url, jsonRequest);
        ObjectRestResponse<String> result = JSON.parseObject(resp, new TypeReference<ObjectRestResponse<String>>() {
        });
        logger.info("芯烨云打印返回" + result.toString());
        return result.getCode() == 0;
    }

    @Override
    public boolean print(long storeUserId, PrinterService.OrderInfo orderInfo) {
        StoreUser user = storeUserRepository.findOne(storeUserId);
        if (StringUtils.isEmpty(user.getXyySn())) {
            logger.error("该商家未绑定芯烨云打印机");
            return false;
        }

        PrintRequest restRequest = new PrintRequest();
        restRequest.setSn(user.getXyySn());
        restRequest.setContent(PrintHelper.toXyyContent(orderInfo));
        Config.createRequestHeader(restRequest);
        String url = BASE_URL + "/xprinter/print";
        String jsonRequest = JSON.toJSONString(restRequest);
        String resp = HttpClientUtil.doPostJSON(url, jsonRequest);
        ObjectRestResponse<String> result = JSON.parseObject(resp, new TypeReference<ObjectRestResponse<String>>() {
        });
        logger.info("芯烨云打印返回" + result.toString());
        return result.getCode() == 0;
    }
}
