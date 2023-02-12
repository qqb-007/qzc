package info.batcloud.wxc.core.service.impl;

import com.sankuai.meituan.waimai.opensdk.vo.AvailableTimeParam;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import info.batcloud.wxc.core.service.MeituanWaimaiService;
import info.batcloud.wxc.core.service.SystemSettingService;
import info.batcloud.wxc.core.settings.MeituanWaimaiAppSetting;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class MeituanWaimaiServiceImpl implements MeituanWaimaiService {

    @Inject
    private SystemSettingService systemSettingService;

    @Override
    public SystemParam getSystemParam() {
        MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
        SystemParam param = new SystemParam(setting.getAppId(), setting.getAppSecret());

        return param;
    }

    @Override
    public boolean checkSign(Map<String, String> param, String url) {
        MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
        String sign = param.remove("sig");
        Map<String, String> sortMap = new TreeMap<>(
                String::compareTo);
        sortMap.putAll(param);
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue());
        }
        url += "?" + String.join("&", list) + setting.getAppSecret();
        return DigestUtils.md5Hex(url).equals(sign);
    }

    @Override
    public AvailableTimeParam getTimeList(Map<String, String> map) {
        AvailableTimeParam timeParam = new AvailableTimeParam();
        if (map.isEmpty()) {
            timeParam.setMonday("00:01-23:59");
            timeParam.setTuesday("00:01-23:59");
            timeParam.setWednesday("00:01-23:59");
            timeParam.setThursday("00:01-23:59");
            timeParam.setFriday("00:01-23:59");
            timeParam.setSaturday("00:01-23:59");
            timeParam.setSunday("00:01-23:59");
        } else {
            timeParam.setMonday(map.get("monday"));
            timeParam.setTuesday(map.get("tuesday"));
            timeParam.setWednesday(map.get("wednesday"));
            timeParam.setThursday(map.get("thursday"));
            timeParam.setFriday(map.get("friday"));
            timeParam.setSaturday(map.get("saturday"));
            timeParam.setSunday(map.get("sunday"));
        }

        return timeParam;
    }
}
