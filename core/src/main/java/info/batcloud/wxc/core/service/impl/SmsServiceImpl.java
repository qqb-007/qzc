package info.batcloud.wxc.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import info.batcloud.wxc.core.aliyun.AccessKey;
import info.batcloud.wxc.core.aliyun.Ram;
import info.batcloud.wxc.core.constants.MessageKeyConstants;
import info.batcloud.wxc.core.enums.CacheKeys;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.repository.WalletRepository;
import info.batcloud.wxc.core.service.SmsService;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.settings.SmsSetting;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    @Inject
    private Ram ram;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private WalletRepository walletRepository;

    @Inject
    private StoreUserRepository storeUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    @Inject
    private RedisTemplate<String, Object> redisTemplate;

    static {
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result<String> sendPhoneLoginMsg(String phone) {
        if (storeUserRepository.countByPhone(phone) == 0) {
            throw new BizException(MessageKeyConstants.USER_NOT_EXISTS);
        }
        SmsSetting smsSetting = systemSettingService.findActiveSetting(SmsSetting.class);
        return sendMsg(smsSetting.getPhoneLoginTemplateCode(), phone);
    }

    private Result<String> sendMsg(String templateCode, String phone) {
        SmsSetting smsSetting = systemSettingService.findActiveSetting(SmsSetting.class);
        Result<String> result = new Result<>();
        result.setPhone(phone);
        PhoneCodeValue ePcv = (PhoneCodeValue) redisTemplate.opsForValue().get(CacheKeys.SMS_PHONE_CODE.name(phone));
        if (ePcv != null) {
            long timestamp = System.currentTimeMillis();
            int seconds = Long.valueOf(timestamp - ePcv.getTimestamp()).intValue() / 1000;
            /**
             * 如果两次发送的秒数差小于冷却秒数，则直接失败返回
             * */
            if (seconds < smsSetting.getCooldown()) {
                result.setSuccess(false);
                result.setCooldown(smsSetting.getCooldown() - seconds);
                return result;
            }
        }
        IAcsClient acsClient = iAcsClient();
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(phone);
        request.setSignName(smsSetting.getSignName());
        request.setTemplateCode(templateCode);
        Map<String, Object> param = new HashMap<>();
        String verifyCode = RandomStringUtils.random(6, false, true);

        param.put("code", verifyCode);
        request.setTemplateParam(JSON.toJSONString(param));
        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            result.setData(verifyCode);
            if (sendSmsResponse.getCode() != null
                    && sendSmsResponse.getCode().equals("OK")) {
                result.setSuccess(true);
                result.setCooldown(smsSetting.getCooldown());
                result.setTimestamp(System.currentTimeMillis());
                PhoneCodeValue pcv = new PhoneCodeValue();
                pcv.setCode(verifyCode);
                pcv.setTimestamp(System.currentTimeMillis());
                redisTemplate.opsForValue().set(CacheKeys.SMS_PHONE_CODE.name(phone), pcv);
            } else {
                result.setErrMsg(sendSmsResponse.getMessage());
            }
            return result;
        } catch (ClientException e) {
            logger.error("发送注册短信失败", e);
        }
        return result;
    }

    @Override
    public boolean validatePhoneCode(String phone, String code) {
        PhoneCodeValue ePcv = (PhoneCodeValue) redisTemplate.opsForValue().get(CacheKeys.SMS_PHONE_CODE.name(phone));
        if (ePcv == null) {
            return false;
        }
        boolean flag = ePcv.getCode().equals(code);
        return flag;
    }

    @Override
    public void unlockPhone(String phone) {
        redisTemplate.delete(CacheKeys.SMS_PHONE_CODE.name(phone));
    }

    private IAcsClient iAcsClient() {
        AccessKey accessKey = ram.getUsers().getAliyunManager().getAccessKey();
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey.getId(),
                accessKey.getSecret());
        return new DefaultAcsClient(profile);
    }

    private static class PhoneCodeValue implements Serializable {
        private long timestamp;
        private String code;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
