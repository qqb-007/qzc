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

/**
 * Created by yujun05 on 2017-12-29.
 */
public class ActApi extends API {

    /**
     * 批量创建/更新折扣商品
     *
     * @param systemParam       系统参数
     * @param appPoiCode        三方门店ID
     * @param actDiscountParams 批量创建/更新折扣商品数据
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actDiscountBatchSave(SystemParam systemParam, String appPoiCode, List<ActDiscountParam> actDiscountParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_data", JSON.toJSONString(actDiscountParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActDiscountBatchSave);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量删除折扣商品
     *
     * @param systemParam  系统参数
     * @param appPoiCode   三方门店ID
     * @param itemIds APP方商品id，删除商品数量上限为100，如果删除门店多个商品的折扣活动，用英文逗号隔开。
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actDiscountDelete(SystemParam systemParam, String appPoiCode, String itemIds) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("item_ids", itemIds);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActDiscountDelete);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量修改折扣商品当日活动库存
     *
     * @param systemParam       系统参数
     * @param appPoiCode        三方门店ID
     * @param actDiscountParams 修改库存数据，数量上限为200
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actDiscountStock(SystemParam systemParam, String appPoiCode, List<ActDiscountParam> actDiscountParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_data", JSON.toJSONString(actDiscountParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActDiscountStock);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量查询门店折扣商品
     *
     * @param systemParam 系统参数
     * @param appPoiCode  三方门店ID
     * @param offset      分页查询偏移量
     * @param limit       分页查询，每页查询的数量不大于200
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<ActDiscountParam> actDiscountList(SystemParam systemParam, String appPoiCode, int offset, int limit) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActDiscountList);
        String data = requestApi(methodName, systemParam, applicationParamsMap);
        return JSONArray.parseArray(data, ActDiscountParam.class);
    }

    /**
     * 修改门店每单折扣商品限购数量(已经修改了请求地址 现在该接口为修改零售综合的每单限购数量 api数量 act/retail/discount/productcount 【折扣】修改折扣/爆品活动门店级每单限购商品总数)
     *
     * @param systemParam        系统参数
     * @param appPoiCode         三方门店ID
     * @param activityOrderLimit 本门店每单可购买的折扣商品数量
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actDiscountActivityOrderLimit(SystemParam systemParam, String appPoiCode, int activityOrderLimit) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_prod_count", String.valueOf(activityOrderLimit));
        applicationParamsMap.put("act_type", String.valueOf(1001));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActDiscountActivityOrderLimit);
        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 批量创建/更新第二份半价商品
     *
     * @param systemParam   系统参数
     * @param appPoiCode    三方门店ID
     * @param actItemParams 活动信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actSecondHalfBatchSave(SystemParam systemParam, String appPoiCode, List<ActItemParam> actItemParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActSecondHalfBatchSave, systemParam, appPoiCode, JSON.toJSONString(actItemParams));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_data", JSON.toJSONString(actItemParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActSecondHalfBatchSave);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量删除第二份半价商品
     *
     * @param systemParam  系统参数
     * @param appPoiCode   三方门店ID
     * @param appFoodCodes 商品code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actSecondHalfDelete(SystemParam systemParam, String appPoiCode, String appFoodCodes) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActSecondHalfDelete, systemParam, appPoiCode, JSON.toJSONString(appFoodCodes));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_codes", appFoodCodes);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActSecondHalfDelete);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量修改第二份半价商品当日活动库存
     *
     * @param systemParam   系统参数
     * @param appPoiCode    三方门店ID
     * @param actItemParams 活动信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actSecondHalfStock(SystemParam systemParam, String appPoiCode, List<ActItemParam> actItemParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActSecondHalfStock, systemParam, appPoiCode, JSON.toJSONString(actItemParams));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_data", JSON.toJSONString(actItemParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActSecondHalfStock);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量查询门店第二份半价商品
     *
     * @param systemParam 系统参数
     * @param appPoiCode  三方门店ID
     * @param offset      分页查询偏移量
     * @param limit       分页查询，每页查询的数量不大于200
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<ActItemParam> actSecondHalfList(SystemParam systemParam, String appPoiCode, int offset, int limit) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActSecondHalfList, systemParam, appPoiCode, offset, limit);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActSecondHalfList);
        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<ActItemParam> result = null;
        try {
            result = JSONArray.parseArray(data, ActItemParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return result;
    }

    /**
     * 批量创建买赠活动
     *
     * @param systemParam           系统参数
     * @param appPoiCode            三方门店ID
     * @param actBuyGiftsItemParams 买赠活动参数
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actBuyGiftsBatchSave(SystemParam systemParam, String appPoiCode, List<ActBuyGiftsItemParam> actBuyGiftsItemParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActBuyGiftsBatchSave, systemParam, appPoiCode, JSON.toJSONString(actBuyGiftsItemParams));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_data", JSON.toJSONString(actBuyGiftsItemParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActBuyGiftsBatchSave);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量删除买赠活动
     *
     * @param systemParam 系统参数
     * @param appPoiCode  三方门店ID
     * @param item_ids    买赠活动ID
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actBuyGiftsDelete(SystemParam systemParam, String appPoiCode, String item_ids) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActBuyGiftsDelete, systemParam, appPoiCode, JSON.toJSONString(item_ids));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("item_ids", item_ids);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActBuyGiftsDelete);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量修改买赠活动当日活动库存
     *
     * @param systemParam           系统参数
     * @param appPoiCode            三方门店ID
     * @param actBuyGiftsItemParams 买赠活动参数
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actBuyGiftsStock(SystemParam systemParam, String appPoiCode, List<ActBuyGiftsItemParam> actBuyGiftsItemParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActBuyGiftsStock, systemParam, appPoiCode, JSON.toJSONString(actBuyGiftsItemParams));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_data", JSON.toJSONString(actBuyGiftsItemParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActBuyGiftsStock);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量查询门店买赠活动
     *
     * @param systemParam 系统参数
     * @param appPoiCode  三方门店ID
     * @param offset      分页查询偏移量
     * @param limit       分页查询，每页查询的数量不大于200
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<ActBuyGiftsItemParam> actBuyGiftsList(SystemParam systemParam, String appPoiCode, int offset, int limit) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActBuyGiftsList, systemParam, appPoiCode, offset, limit);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActBuyGiftsList);
        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<ActBuyGiftsItemParam> result = null;
        try {
            result = JSONArray.parseArray(data, ActBuyGiftsItemParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return result;
    }

    /**
     * 批量创建指定商品满减活动
     *
     * @param systemParam              系统参数
     * @param appPoiCode               三方门店ID
     * @param actFullDiscountItemParam 活动信息
     * @param actDetails               活动详情
     * @param appFoods                 活动商品
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actFullDiscountBatchSave(SystemParam systemParam, String appPoiCode, ActFullDiscountItemParam actFullDiscountItemParam,
                                           List<ActItemParam> actDetails, List<ActItemParam> appFoods) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActFullDiscountBatchSave, systemParam, appPoiCode, actFullDiscountItemParam,
                JSON.toJSONString(actDetails), JSON.toJSONString(appFoods));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_info", JSON.toJSONString(actFullDiscountItemParam));
        applicationParamsMap.put("act_details", JSON.toJSONString(actDetails));
        applicationParamsMap.put("app_foods", JSON.toJSONString(appFoods));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActFullDiscountBatchSave);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量查询指定商品满减活动
     *
     * @param systemParam 系统参数
     * @param appPoiCode  三方门店ID
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<ActFullDiscountParam> actFullDiscountList(SystemParam systemParam, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActFullDiscountList, systemParam, appPoiCode);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActFullDiscountList);
        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<ActFullDiscountParam> result = null;
        try {
            result = JSONArray.parseArray(data, ActFullDiscountParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return result;
    }

    /**
     * 批量删除指定商品满减活动
     *
     * @param systemParam 系统参数
     * @param appPoiCode  三方门店ID
     * @param actIds      活动ID
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actFullDiscountDelete(SystemParam systemParam, String appPoiCode, String actIds) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActFullDiscountDelete, systemParam, appPoiCode, actIds);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_ids", actIds);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActFullDiscountDelete);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量添加活动商品至指定商品满减活动
     *
     * @param systemParam   系统参数
     * @param appPoiCode    三方门店ID
     * @param actId         活动ID
     * @param actItemParams 活动商品
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actFullDiscountFoodsBatchSave(SystemParam systemParam, String appPoiCode, String actId, List<ActItemParam> actItemParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActFullDiscountFoodsBatchSave, systemParam, appPoiCode, actId, JSON.toJSONString(actItemParams));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_id", actId);
        applicationParamsMap.put("app_foods", JSON.toJSONString(actItemParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActFullDiscountFoodsBatchSave);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量查询指定商品满减活动中的活动商品
     *
     * @param systemParam 系统参数
     * @param appPoiCode  三方门店ID
     * @param actId       活动ID
     * @param offset      分页查询偏移量
     * @param limit       分页查询，每页查询的数量不大于200
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public ActFullDiscountParam actFullDiscountFoodsList(SystemParam systemParam, String appPoiCode, String actId, int offset, int limit) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActFullDiscountFoodsList, systemParam, appPoiCode, actId, offset, limit);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_id", actId);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActFullDiscountFoodsList);
        String data = requestApi(methodName, systemParam, applicationParamsMap);
        ActFullDiscountParam result = null;
        try {
            result = JSONArray.parseObject(data, ActFullDiscountParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return result;
    }

    /**
     * 批量删除活动商品至指定商品满减活动
     *
     * @param systemParam  系统参数
     * @param appPoiCode   三方门店ID
     * @param actId        活动ID
     * @param appFoodCodes APP方商品ID
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actFullDiscountFoodsDelete(SystemParam systemParam, String appPoiCode, String actId, String appFoodCodes) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActFullDiscountFoodsDelete, systemParam, appPoiCode, actId, JSON.toJSONString(appFoodCodes));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_id", actId);
        applicationParamsMap.put("app_food_codes", appFoodCodes);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActFullDiscountFoodsDelete);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 批量修改指定商品满减活动中商品的当日活动库存
     *
     * @param systemParam   系统参数
     * @param appPoiCode    三方门店ID
     * @param actId         活动ID
     * @param actItemParams 活动商品
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String actFullDiscountFoodsDayLimit(SystemParam systemParam, String appPoiCode, String actId, List<ActItemParam> actItemParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.ActFullDiscountFoodsDayLimit, systemParam, appPoiCode, actId, JSON.toJSONString(actItemParams));
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("act_id", actId);
        applicationParamsMap.put("app_foods", JSON.toJSONString(actItemParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.ActFullDiscountFoodsDayLimit);
        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }
}
