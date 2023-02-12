package info.batcloud.wxc.core.service.impl;

import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import info.batcloud.wxc.core.config.ClbmApp;
import info.batcloud.wxc.core.service.ClbmWaiMaiService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ClbmWaiMaiServiceImpl implements ClbmWaiMaiService {
    @Inject
    private ClbmApp clbmApp;

    @Override
    public SystemParam getSystemParam() {
        SystemParam param = new SystemParam(clbmApp.getAppId(), clbmApp.getAppSecret());
        return param;
    }

    @Override
    public boolean checkSign(Map<String, String> param, String url) {
        String sign = param.remove("sig");
        Map<String, String> sortMap = new TreeMap<>(
                String::compareTo);
        sortMap.putAll(param);
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue());
        }
        url += "?" + String.join("&", list) + clbmApp.getAppSecret();
        return DigestUtils.md5Hex(url).equals(sign);
    }
}
