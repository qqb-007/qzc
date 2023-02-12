package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.mapper.stat.OrderStatMapper;
import info.batcloud.wxc.core.mapper.stat.domain.StoreUserBizData;
import info.batcloud.wxc.merchant.api.controller.form.StoreUserBizDataStatForm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/stat")
@PreAuthorize("hasRole('STORE_USER')")
public class StatController {

    @Inject
    private OrderStatMapper orderStatMapper;

    @GetMapping("/store-user-biz-data")
    public Object statStoreUserBizData(StoreUserBizDataStatForm form) {
        Date now = DateUtils.truncate(new Date(), Calendar.DATE);
        Date startTime = null, endTime = now;
        switch (form.getDayType()) {
            case "last1Days":
                startTime = DateUtils.truncate(DateUtils.addDays(now, -1), Calendar.DATE);
                endTime = DateUtils.addDays(startTime, 1);
                break;
            case "last7Days":
                startTime = DateUtils.addDays(now, -7);
                break;
            case "last30Days":
                startTime = DateUtils.addDays(now, -30);
                break;
            case "customDays":
                startTime = form.getStartTime();
                endTime = DateUtils.addDays(form.getEndTime(), 1);
            default:
                break;
        }
        StoreUserBizData data = orderStatMapper.statStoreUserDayBizDataByStoreUserId(SecurityHelper.loginStoreUserId(), startTime, endTime);
        return BusinessResponse.ok(data == null ? new StoreUserBizData() : data);
    }

}
