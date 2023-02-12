package com.sankuai.meituan.waimai.opensdk.api;

import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yujun05 on 2017-12-29.
 */
public class MemberApi extends API {

    /**
     * 查询美团会员信息
     */
    public String memberList(SystemParam systemParam, String poiCode, int startTime, int endTime, int offset, int limit) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>(5);
        applicationParamsMap.put("app_poi_code", poiCode);
        applicationParamsMap.put("start_time", String.valueOf(startTime));
        applicationParamsMap.put("end_time", String.valueOf(endTime));
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        return requestApi(methodName, systemParam, applicationParamsMap);
    }
}
