package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.StringHelper;
import info.batcloud.wxc.core.service.SmsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Inject
    private SmsService smsService;

    @PostMapping("/phone-login/{phone}")
    public Object sendLoginPhone(@PathVariable String phone) {
        SmsService.Result<String> rs = smsService.sendPhoneLoginMsg(phone);
        rs.setPhone(StringHelper.protect(rs.getPhone()));
        rs.setData(null);
        return BusinessResponse.ok(rs);
    }

}
