package com.sankuai.meituan.waimai.opensdk.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sankuai.meituan.waimai.opensdk.constants.ErrorEnum;
import com.sankuai.meituan.waimai.opensdk.constants.ParamRequiredEnum;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActRetailApi extends API {
    public String actRetailDiscountBatchsave(SystemParam systemParam, String appPoiCode, List<ActRetailDiscountParam> actDiscountParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_data", JSON.toJSONString(actDiscountParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActRetailDiscountBatchsave);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    //X元M件活动接口
    public String actItemBundlesSave(SystemParam systemParam,String appPoiCode, ItemBundParam param) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_data", JSON.toJSONString(param));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActItemBundlesSave);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    //商品券活动接口
    public String actGoodsCouponSave(SystemParam systemParam, GoodsCouponParam param) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        //applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("goods_coupon_data", JSON.toJSONString(param));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActGoodsCouponSave);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    //将商品从指定活动移除接口
    public String actDeleteActsByProducts(SystemParam systemParam, String app_poi_code, String app_spu_codes, Integer type) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", app_poi_code);
        applicationParamsMap.put("app_spu_codes", app_spu_codes);
        applicationParamsMap.put("type", type.toString());
        applicationParamsMap.put("status", "1");
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActDeleteActsByProducts);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    public List<ActRetailDiscountParam> actRetailDiscountList(SystemParam systemParam, String appPoiCode, Integer offset, Integer limit) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActRetailDiscountList);
        String data = requestApi(methodName, systemParam, applicationParamsMap);
        return JSONArray.parseArray(data, ActRetailDiscountParam.class);
    }

    public List<ActCouponListParam> actGoodsCouponList(SystemParam systemParam, String appPoiCode, Integer start_time, Integer end_time, Integer act_status, Integer page_num, Integer page_size) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_status", String.valueOf(act_status));
        applicationParamsMap.put("page_num", String.valueOf(page_num));
        applicationParamsMap.put("start_time", String.valueOf(start_time));
        applicationParamsMap.put("end_time", String.valueOf(end_time));
        applicationParamsMap.put("page_size", String.valueOf(page_size));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActGoodsCouponList);
        String data = requestApi(methodName, systemParam, applicationParamsMap);
        return JSONArray.parseArray(data, ActCouponListParam.class);
    }

    //查询商品参加的活动
    public GetActListParam actAllGetByAppFoodCodes(SystemParam systemParam, String appPoiCode, String appFoodCodes, Integer status, Integer query_type, Integer pageNum, Integer pageSize) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_codes", appFoodCodes);
        applicationParamsMap.put("query_type", query_type.toString());
        applicationParamsMap.put("status", status.toString());
        applicationParamsMap.put("page_num", String.valueOf(pageNum));
        applicationParamsMap.put("page_size", String.valueOf(pageSize));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActAllGetByAppFoodCodes);
        String data = requestApi(methodName, systemParam, applicationParamsMap);
        GetActListParam result = null;
        try {
            result = JSONArray.parseObject(data, GetActListParam.class);

        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return result;
    }
}
