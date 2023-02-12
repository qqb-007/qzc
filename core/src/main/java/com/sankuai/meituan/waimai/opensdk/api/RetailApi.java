package com.sankuai.meituan.waimai.opensdk.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sankuai.meituan.waimai.opensdk.constants.ErrorEnum;
import com.sankuai.meituan.waimai.opensdk.constants.ParamRequiredEnum;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.util.ConvertUtil;
import com.sankuai.meituan.waimai.opensdk.util.StringUtil;
import com.sankuai.meituan.waimai.opensdk.vo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songxu on 2017/08/04.
 */
@Deprecated
public class RetailApi extends API {

    /**
     * 更新商品分类
     *
     * @param systemParam           系统参数
     * @param appPoiCode            门店code
     * @param originCategoryName    原始商品分类名
     * @param categoryName          新商品分类名(一级分类)
     * @param secondaryCategoryName 商品二级分类名
     * @param sequence              商品分类排序(数字越小，排名越靠前)
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailCatUpdate(SystemParam systemParam, String appPoiCode, String originCategoryName, String categoryName, String
            secondaryCategoryName, Integer sequence) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailCatUpdate, systemParam, appPoiCode, categoryName);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        if (StringUtil.isNotBlank(originCategoryName)) {
            applicationParamsMap.put("category_name_origin", originCategoryName);
        }
        applicationParamsMap.put("category_name", categoryName);
        if (StringUtil.isNotBlank(secondaryCategoryName)) {
            applicationParamsMap.put("secondary_category_name", secondaryCategoryName);
        }
        if (StringUtil.isNotBlank(sequence)) {
            applicationParamsMap.put("sequence", String.valueOf(sequence));
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailCatUpdate);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 删除商品分类
     *
     * @param systemParam  系统参数
     * @param appPoiCode   门店code
     * @param categoryName 商品分类名
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailCatDelete(SystemParam systemParam, String appPoiCode, String categoryName) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailCatDelete, systemParam, appPoiCode, categoryName);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("category_name", categoryName);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailCatDelete);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 获取门店下商品分类
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<RetailCatParam> retailCatList(SystemParam systemParam, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailCatList, systemParam, appPoiCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<RetailCatParam> retailCatParams = null;
        try {
            retailCatParams = JSONArray.parseArray(data, RetailCatParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return retailCatParams;
    }

    /**
     * 创建/更新商品(支持商品多规格,不含删除逻辑)
     *
     * @param systemParam 系统参数
     * @param foodParam   商品信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailInitData(SystemParam systemParam, FoodParam foodParam) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailInitdata, systemParam, foodParam);

        //组织应用级参数
        Map<String, String> applicationParamsMap = ConvertUtil.convertToMap(foodParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailInitdata);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 查询门店商品列表
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<FoodParam> retailList(SystemParam systemParam, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailList, systemParam, appPoiCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailList);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        return parseFoodData(data);
    }

    /**
     * 分页查询门店商品列表
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<FoodParam> retailListByPage(SystemParam systemParam, String appPoiCode, int offset, int limit) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailListByPage, systemParam, appPoiCode, offset, limit);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailListByPage);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        return parseFoodData(data);
    }

    /**
     * 批量创建/更新商品[支持商品多规格,不含删除逻辑](可以添加sku信息)
     *
     * @param systemParam 系统参数
     * @param foodParams  菜品信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailBatchInitData(SystemParam systemParam, String appPoiCode, List<FoodParam> foodParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailBatchInitdata, systemParam, appPoiCode, JSONObject.toJSONString(foodParams));

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodParams));

        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailBatchInitdata);

        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 更新商品sku价格
     *
     * @param systemParam        系统参数
     * @param foodSkuPriceParams 商品sku价格信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String updateRetailSkuPrice(SystemParam systemParam, String appPoiCode, List<FoodSkuPriceParam> foodSkuPriceParams) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodSkuPriceParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.UpdateRetailSkuPrice);

        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 更新商品sku库存
     *
     * @param systemParam        系统参数
     * @param foodSkuStockParams 商品sku库存信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String updateRetailSkuStock(SystemParam systemParam, String appPoiCode, List<FoodSkuStockParam> foodSkuStockParams) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodSkuStockParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.UpdateRetailSkuStock);

        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 查询门店商品详情
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode 商品code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public FoodParam retailGet(SystemParam systemParam, String appPoiCode, String appFoodCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailGet, systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailGet);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        FoodParam foodParam = null;
        try {
            JSONObject fullFood = JSONObject.parseObject(data);
            if (fullFood.get("skus") != null) {
                JSONArray skuStr = fullFood.getJSONArray("skus");
                fullFood.remove("skus");
                foodParam = fullFood.toJavaObject(FoodParam.class);
                if (!StringUtil.isBlank(skuStr)) {
                    List<FoodSkuParam> skus = skuStr.toJavaList(FoodSkuParam.class);
                    foodParam.setSkus(skus);
                }
            } else {
                foodParam = fullFood.toJavaObject(FoodParam.class);
            }
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodParam;
    }

    private FoodParam parseSingleFoodData(String data) throws ApiSysException {
        FoodParam foodParam = null;
        try {
            JSONObject fullFood = JSONObject.parseObject(data);
            fullFood.put("skus", JSONArray.parseArray(fullFood.getString("skus"), FoodSkuParam.class));
            foodParam = JSONObject.toJavaObject(fullFood, FoodParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodParam;
    }

    private List<FoodParam> parseFoodData(String data) throws ApiSysException {
        List<FoodParam> foodParams = new ArrayList<>();
        try {
            JSONArray jsonArray = JSONArray.parseArray(data);
            int jsonArraySize = jsonArray.size();
            for (int i = 0; i < jsonArraySize; i++) {
                foodParams.add(parseSingleFoodData(jsonArray.getJSONObject(i).toJSONString()));
            }
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodParams;
    }

}
