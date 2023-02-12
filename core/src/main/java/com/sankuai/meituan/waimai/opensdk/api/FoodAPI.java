package com.sankuai.meituan.waimai.opensdk.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
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
 * Created by yangzhiqi on 15/10/15.
 */
public class FoodAPI extends API {

    /**
     * 更新菜品分类
     *
     * @param systemParam        系统参数
     * @param appPoiCode         门店code
     * @param originCategoryName 原始的菜品分类
     * @param newCategoryName    新建的菜品分类
     * @param sequence           菜品排序序号
     * @return
     */
    public String foodCatUpdate(SystemParam systemParam, String appPoiCode, String originCategoryName, String newCategoryName, int sequence) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodCatUpdate, systemParam, appPoiCode, newCategoryName);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        if (originCategoryName != null && !"".equals(originCategoryName) && !"null".equals(originCategoryName) && !"NULL".equals
                (originCategoryName)) {
            applicationParamsMap.put("category_name_origin", originCategoryName);
        }
        applicationParamsMap.put("category_name", newCategoryName);
        String sequence_str = String.valueOf(sequence);
        if (sequence_str != null && !"".equals(sequence_str) && !"null".equals(sequence_str) && !"NULL".equals(sequence_str)) {
            applicationParamsMap.put("sequence", sequence_str);
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodCatUpdate);


        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 删除菜品分类
     *
     * @param systemParam  系统参数
     * @param appPoiCode   门店code
     * @param categoryName 要删除的菜品分类
     * @return
     */
    public String foodCatDelete(SystemParam systemParam, String appPoiCode, String categoryName) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("category_name", categoryName);
        beforeMethod(ParamRequiredEnum.FoodCatDelete, systemParam, appPoiCode, categoryName);
        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 创建／更新菜品
     *
     * @param systemParam 系统参数
     * @param foodParam   菜品信息
     * @return
     */
    public String foodSave(SystemParam systemParam, FoodParam foodParam) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodSave, systemParam, foodParam);

        //组织应用级参数
        Map<String, String> applicationParamsMap = ConvertUtil.convertToMap(foodParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodSave);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 创建／更新菜品(支持菜品多规格,不含删除逻辑)
     *
     * @param systemParam 系统参数
     * @param foodParam   菜品信息
     * @return
     */
    public String foodInitData(SystemParam systemParam, FoodParam foodParam) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodInitData, systemParam, foodParam);

        //组织应用级参数
        Map<String, String> applicationParamsMap = ConvertUtil.convertToMap(foodParam);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodInitData);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 删除菜品
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode 要删除的菜品code
     * @return
     */
    public String foodDelete(SystemParam systemParam, String appPoiCode, String appFoodCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodDelete, systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodDelete);


        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 查询门店菜品列表
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @return
     */
    public List<FoodParam> foodList(SystemParam systemParam, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodList, systemParam, appPoiCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodList);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        //        System.out.println(data);
        List<FoodParam> foodParams = new ArrayList<>();
        try {
            JSONArray jsonArray = JSONArray.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject fullFood = JSONObject.parseObject(jsonArray.get(i).toString());
                FoodParam foodParam = null;
                if (fullFood.get("skus") != null) {
                    String skuStr = fullFood.get("skus").toString();
                    fullFood.remove("skus");
                    foodParam = JSONObject.parseObject(fullFood.toString(), FoodParam.class);
                    if (!StringUtil.isBlank(skuStr)) {
                        List<FoodSkuParam> skus = JSONArray.parseArray(skuStr, FoodSkuParam.class);
                        foodParam.setSkus(skus);
                    }
                } else {
                    foodParam = JSONObject.parseObject(fullFood.toString(), FoodParam.class);
                }
                foodParams.add(foodParam);
            }
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodParams;
    }

    /**
     * 查询门店菜品列表
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param offset      偏移量
     * @param limit       查询个数
     * @return
     */
    public List<FoodParam> foodListByPage(SystemParam systemParam, String appPoiCode, int offset, int limit) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodListByPage, systemParam, appPoiCode, offset, limit);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodListByPage);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<FoodParam> foodParams = new ArrayList<>();
        try {
            JSONArray jsonArray = JSONArray.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject fullFood = JSONObject.parseObject(jsonArray.get(i).toString());
                FoodParam foodParam = null;
                if (fullFood.get("skus") != null) {
                    String skuStr = fullFood.get("skus").toString();
                    fullFood.remove("skus");
                    foodParam = JSONObject.parseObject(fullFood.toString(), FoodParam.class);
                    if (!StringUtil.isBlank(skuStr)) {
                        List<FoodSkuParam> skus = JSONArray.parseArray(skuStr, FoodSkuParam.class);
                        foodParam.setSkus(skus);
                    }
                } else {
                    foodParam = JSONObject.parseObject(fullFood.toString(), FoodParam.class);
                }
                foodParams.add(foodParam);
            }
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodParams;
    }

    /**
     * 批量保存菜品(不包含sku信息)
     *
     * @param systemParam 系统参数
     * @param foodParams  菜品信息
     * @return
     */
    public String foodBatchSave(SystemParam systemParam, String appPoiCode, List<FoodParam> foodParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodParams));
        beforeMethod(ParamRequiredEnum.FoodBatchSave, systemParam, appPoiCode, JSONObject.toJSONString(foodParams));

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 批量保存菜品(可以添加sku信息)
     *
     * @param systemParam 系统参数
     * @param foodParams  菜品信息
     * @return
     */
    public String foodBatchInitData(SystemParam systemParam, String appPoiCode, List<FoodParam> foodParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodParams));

        beforeMethod(ParamRequiredEnum.FoodBatchInitData, systemParam, appPoiCode, JSONObject.toJSONString(foodParams));


        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 获取门店下菜品分类
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @return
     */
    public List<FoodCatParam> foodCatList(SystemParam systemParam, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodCatList, systemParam, appPoiCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<FoodCatParam> foodCatParams = null;
        try {
            foodCatParams = JSONArray.parseArray(data, FoodCatParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodCatParams;
    }

    /**
     * 保存菜品sku
     *
     * @param systemParam   系统参数
     * @param foodSkuParams 菜品sku信息
     * @return
     */
    public String foodSkuSave(SystemParam systemParam, String app_poi_code, String app_food_code, List<FoodSkuParam> foodSkuParams) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null, systemParam, app_poi_code, JSON.toJSONString(foodSkuParams));

        //组织应用级参数
        //        Map<String,String> applicationParamsMap = ConvertUtil.convertToMap(foodSkuParams);
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", app_poi_code);
        applicationParamsMap.put("app_food_code", app_food_code);
        applicationParamsMap.put("skus", JSON.toJSONString(foodSkuParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodSkuSave);


        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 删除菜品sku
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode 要删除的菜品code
     * @param skuId       要删除的菜品skuId
     * @return
     */
    public String foodSkuDelete(SystemParam systemParam, String appPoiCode, String appFoodCode, String skuId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null, systemParam, appPoiCode, appFoodCode, skuId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        applicationParamsMap.put("sku_id", skuId);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodSkuDelete);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 更新菜品sku价格
     *
     * @param systemParam        系统参数
     * @param foodSkuPriceParams 菜品sku价格信息
     * @return
     */
    public String updateFoodSkuPrice(SystemParam systemParam, String appPoiCode, List<FoodSkuPriceParam> foodSkuPriceParams) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null, systemParam, appPoiCode, JSONObject.toJSONString(foodSkuPriceParams));

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodSkuPriceParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.UpdateFoodSkuPrice);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 更新菜品sku库存
     *
     * @param systemParam        系统参数
     * @param foodSkuStockParams 菜品sku库存信息
     * @return
     */
    public String updateFoodSkuStock(SystemParam systemParam, String appPoiCode, List<FoodSkuStockParam> foodSkuStockParams) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null, systemParam, appPoiCode, JSONObject.toJSONString(foodSkuStockParams));

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(foodSkuStockParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.UpdateFoodSkuStock);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 增加菜品库存
     *
     * @param systemParam
     * @param appPoiCode
     * @param foodSkuStockParams
     * @return
     */
    public String incFoodSkuStock(SystemParam systemParam, String appPoiCode, List<FoodSkuStockParam> foodSkuStockParams) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(null, systemParam, appPoiCode, JSONObject.toJSONString(foodSkuStockParams));

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("food_data", JSON.toJSONString(foodSkuStockParams));
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.incFoodSkuStock);

        return requestApi(methodName, systemParam, applicationParamMap);
    }

    /**
     * 绑定菜品属性
     *
     * @param systemParam               系统参数
     * @param appPoiCode                门店code
     * @param foodPropertyWithFoodCodes 菜品属性
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String foodBindProperty(SystemParam systemParam, String appPoiCode, List<FoodPropertyWithFoodCode> foodPropertyWithFoodCodes) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.FoodBindProperty, systemParam, appPoiCode, JSONArray.toJSONString(foodPropertyWithFoodCodes));

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("food_property", JSONArray.toJSONString(foodPropertyWithFoodCodes));
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.FoodBindProperty);

        return requestApi(methodName, systemParam, applicationParamMap);
    }

    /**
     * 获取菜品属性
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode 菜品code
     * @return 菜品属性
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<FoodProperty> foodPropertyList(SystemParam systemParam, String appPoiCode, String appFoodCode) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.FoodPropertyList, systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("app_food_code", appFoodCode);
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.FoodPropertyList);

        String data = requestApi(methodName, systemParam, applicationParamMap);

        List<FoodProperty> foodProperties = null;
        try {
            foodProperties = JSONArray.parseArray(data, FoodProperty.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodProperties;
    }

    /**
     * 减少菜品库存
     *
     * @param systemParam
     * @param appPoiCode
     * @param foodSkuStockParams
     * @return
     */
    public String descFoodSkuStock(SystemParam systemParam, String appPoiCode, List<FoodSkuStockParam> foodSkuStockParams) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(null, systemParam, appPoiCode, JSONObject.toJSONString(foodSkuStockParams));

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("food_data", JSON.toJSONString(foodSkuStockParams));
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.descFoodSkuStock);

        return requestApi(methodName, systemParam, applicationParamMap);
    }

    /**
     * 查询门店菜品详情
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode 菜品code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public FoodParam foodGet(SystemParam systemParam, String appPoiCode, String appFoodCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.FoodGet, systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.FoodGet);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        FoodParam foodParam = null;
        try {
            JSONObject fullFood = JSONObject.parseObject(data);
            if (fullFood.get("skus") != null) {
                JSONArray skuStr = fullFood.getJSONArray("skus");
                fullFood.remove("skus");
                foodParam = JSON.parseObject(data, FoodParam.class);
                if (!StringUtil.isBlank(skuStr)) {
                    List<FoodSkuParam> skus = JSON.parseObject(skuStr.toJSONString(), new TypeReference<List<FoodSkuParam>>(){});
                    foodParam.setSkus(skus);
                }
            } else {
                foodParam = JSON.parseObject(data, FoodParam.class);
            }
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodParam;
    }

    /**
     * 批量更新售卖状态
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param foodParams  菜品参数
     * @param sellStatus  售卖状态
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String foodSkuSellStatusUpdate(SystemParam systemParam, String appPoiCode, List<FoodParam> foodParams, Integer sellStatus) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.FoodSkuSellStatusUpdate, systemParam, appPoiCode, JSONArray.toJSONString(foodParams), sellStatus);

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("food_data", JSONArray.toJSONString(foodParams));
        applicationParamMap.put("sell_status", String.valueOf(sellStatus));
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.FoodSkuSellStatusUpdate);

        return requestApi(methodName, systemParam, applicationParamMap);
    }

    /**
     * 根据原商品编码更换新商品编码
     *
     * @param app_poi_code
     * @param app_food_code_origin
     * @param app_food_code
     * @param sku_id_origin
     * @param sku_id
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String updateAppFoodCodeByOrigin(SystemParam systemParam, String app_food_code_origin,
                                            String app_poi_code, String app_food_code, String sku_id_origin, String sku_id) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.UpdateAppFoodCodeByOrigin, systemParam, app_poi_code);

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", app_poi_code);
        applicationParamMap.put("app_food_code_origin", app_food_code_origin);
        applicationParamMap.put("app_food_code", app_food_code);
        applicationParamMap.put("sku_id_origin", sku_id_origin);
        applicationParamMap.put("sku_id", sku_id);
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.UpdateAppFoodCodeByOrigin);

        return requestApi(methodName, systemParam, applicationParamMap);
    }

    /**
     * 根据商品名称和规格名称更换新的商品编码
     *
     * @param app_poi_code
     * @param name
     * @param category_name
     * @param app_food_code
     * @param sku_id
     * @param spec
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String updateAppFoodCodeByNameAndSpec(SystemParam systemParam,String app_poi_code,
                                                 String name, String category_name, String app_food_code, String sku_id,String spec) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.UpdateAppFoodCodeByNameAndSpec, systemParam, app_poi_code);

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", app_poi_code);
        applicationParamMap.put("name", name);
        applicationParamMap.put("category_name", category_name);
        applicationParamMap.put("app_food_code", app_food_code);
        applicationParamMap.put("sku_id", sku_id);
        applicationParamMap.put("spec", spec);
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.UpdateAppFoodCodeByNameAndSpec);

        return requestApi(methodName, systemParam, applicationParamMap);
    }
}

