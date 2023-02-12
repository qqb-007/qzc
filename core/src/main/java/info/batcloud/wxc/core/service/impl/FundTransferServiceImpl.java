package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import info.batcloud.wxc.core.alipay.domain.FundTransToAccountTransferContent;
import info.batcloud.wxc.core.config.AlipayConfig;
import info.batcloud.wxc.core.entity.FundTransferOrder;
import info.batcloud.wxc.core.repository.FundTransferOrderRepository;
import info.batcloud.wxc.core.service.FundTransferService;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.settings.AlipaySetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service
public class FundTransferServiceImpl implements FundTransferService {

    private static final Logger logger = LoggerFactory.getLogger(FundTransferServiceImpl.class);

    @Inject
    private AlipayConfig alipayConfig;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private FundTransferOrderRepository fundTransferOrderRepository;

    @Override
    public FundTransferResult transferFund(FundTransferParam param) {
        logger.info("开始支付宝转账：" + JSON.toJSONString(param));
        FundTransferOrder order = new FundTransferOrder();
        AlipaySetting alipaySetting = systemSettingService.findActiveSetting(AlipaySetting.class);
        order.setPayerShowName(alipaySetting.getPayerShowName());
        order.setCreateTime(new Date());
        order.setAmount(param.getAmount());
        order.setPayeeAccount(param.getPayeeAccount());
        order.setPayeeRealName(param.getPayeeRealName());
        order.setPayerShowName(alipaySetting.getPayerShowName());
        fundTransferOrderRepository.save(order);
        AlipayClient alipayClient =
                new DefaultAlipayClient(alipayConfig.getServerUrl(), alipayConfig.getAppId(),
                        alipayConfig.getPrivateKey(), "json", "utf-8",
                        alipayConfig.getAlipayPublicKey(), "RSA2");
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        FundTransToAccountTransferContent content = new FundTransToAccountTransferContent();
        content.setAmount(param.getAmount());
        content.setOutBizNo(order.getId() + "");
        content.setPayeeAccount(param.getPayeeAccount());
        content.setPayeeRealName(param.getPayeeRealName());
        content.setPayerShowName(order.getPayerShowName());
        content.setPayeeType("ALIPAY_LOGONID");
        content.setRemark(param.getRemark());
        request.setBizContent(JSON.toJSONString(content));
        FundTransferResult result = new FundTransferResult();
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            logger.info("支付宝转账返回:" + JSON.toJSONString(response));
            order.setCode(response.getCode());
            order.setMsg(response.getMsg());
            order.setSubMsg(response.getSubMsg());
            order.setPayDate(response.getPayDate());
            order.setSubCode(response.getSubCode());
            order.setSuccess(response.isSuccess());
            order.setAlipayOrderId(response.getOrderId());
            fundTransferOrderRepository.save(order);
            result.setFundTransferOrderId(order.getId());
            result.setPayDate(response.getPayDate());
            result.setAlipayOrderId(response.getOrderId());
            if (response.isSuccess()) {
                logger.info("支付宝转账成功");
                result.setSuccess(true);
            } else {
                logger.error("支付宝转账失败");
                result.setSuccess(false);
                result.setErrorMsg(response.getMsg() + " : " + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝转账失败", e);
            result.setSuccess(false);
            result.setFundTransferOrderId(order.getId());
            result.setErrorMsg(e.getErrMsg());
        }

        return result;
    }

}
