package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsRequest;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import info.batcloud.wxc.core.aliyun.AccessKey;
import info.batcloud.wxc.core.aliyun.Ram;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.service.SingleCallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SingleCallServiceImpl implements SingleCallService {
    @Inject
    private Ram ram;

    private static final Logger logger = LoggerFactory.getLogger(SingleCallService.class);

    private static final String PRODUCT = "Dyvmsapi";
    private static final String DOMAIN = "dyvmsapi.aliyuncs.com";

    static {
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean sendCall(Plat plat, String phone, Long orderId) {
        IAcsClient acsClient = iAcsClient();
        SingleCallByTtsRequest request = new SingleCallByTtsRequest();
        //request.setCalledShowNumber("购买的手机号");
        request.setCalledNumber(phone);
        request.setTtsCode("TTS_195870400");
        CallPlat callPlat = new CallPlat();
        switch (plat) {
            case CLBM:
                callPlat.setPlat("美团印龙菜老板");
                break;
            case ELE:
                callPlat.setPlat("饿了么王小菜");
                break;
            case MEITUAN:
                callPlat.setPlat("美团王小菜");
                break;
            case JDDJ:
                callPlat.setPlat("京东到家王小菜");
                break;

        }
        request.setTtsParam(JSON.toJSONString(callPlat));//品牌名称添加进去用于提醒
        request.setVolume(100);
        //可选-播放次数
        request.setPlayTimes(3);
        request.setOutId(String.valueOf(orderId));
        SingleCallByTtsResponse singleCallByTtsResponse;
        try {
            singleCallByTtsResponse = acsClient.getAcsResponse(request);
            if (singleCallByTtsResponse.getCode() != null && singleCallByTtsResponse.getCode().equals("OK")) {
                //请求成功
                logger.info("语音文本外呼---------------");
                logger.info("RequestId=" + singleCallByTtsResponse.getRequestId());
                logger.info("Code=" + singleCallByTtsResponse.getCode());
                logger.info("Message=" + singleCallByTtsResponse.getMessage());
                logger.info("CallId=" + singleCallByTtsResponse.getCallId());
                return true;
            } else {
                logger.error("语音文本外呼失败 订单id " + singleCallByTtsResponse.getCallId() + " 失败原因" + singleCallByTtsResponse.getMessage());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean sendCustomerCall(String info, Long orderId) {
        IAcsClient acsClient = iAcsClient();
        SingleCallByTtsRequest request = new SingleCallByTtsRequest();
        //request.setCalledShowNumber("购买的手机号");
        request.setCalledNumber("057187389919");
        request.setTtsCode("TTS_205070233");
        CallCustomer callCustomer = new CallCustomer();
        callCustomer.setInfo(info);
        request.setTtsParam(JSON.toJSONString(callCustomer));//品牌名称添加进去用于提醒
        request.setVolume(100);
        //可选-播放次数
        request.setPlayTimes(2);
        request.setOutId(String.valueOf(orderId));
        SingleCallByTtsResponse singleCallByTtsResponse;
        try {
            singleCallByTtsResponse = acsClient.getAcsResponse(request);
            if (singleCallByTtsResponse.getCode() != null && singleCallByTtsResponse.getCode().equals("OK")) {
                //请求成功
                logger.info("语音文本外呼---------------订单id:"+orderId);
                logger.info("RequestId=" + singleCallByTtsResponse.getRequestId());
                logger.info("Code=" + singleCallByTtsResponse.getCode());
                logger.info("Message=" + singleCallByTtsResponse.getMessage());
                logger.info("CallId=" + singleCallByTtsResponse.getCallId());
                return true;
            } else {
                logger.error("语音文本外呼失败 订单id " + singleCallByTtsResponse.getCallId() + " 失败原因" + singleCallByTtsResponse.getMessage());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return true;
    }

    private IAcsClient iAcsClient() {
        AccessKey accessKey = ram.getUsers().getAliyunManager().getAccessKey();
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey.getId(),
                accessKey.getSecret());
        return new DefaultAcsClient(profile);
    }
}
