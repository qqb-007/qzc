package com.sankuai.meituan.waimai.opensdk.api;

import com.alibaba.fastjson.JSON;
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
 * 新零售类：
 * 1.替换RetailApi类中的FoodParam类型参数为RetailParam类型参数，在RetailParam类型中增加了新的字段
 * 2.在RetailApi类的基础上，增加新的接口功能
 *
 * @author wangshiyao02
 * @date 2018-06-14 14:22
 **/
public class NewRetailApi extends API {

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
     * 查询门店商品列表
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<AuditStatusParam> retailAuditStatus(SystemParam systemParam, String appPoiCode, Integer audit_status, Integer page_num, Integer page_size) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailAuditStatus, systemParam, appPoiCode, audit_status, page_num, page_size);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("audit_status", audit_status.toString());
        applicationParamsMap.put("page_num", page_num.toString());
        applicationParamsMap.put("page_size", page_size.toString());
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailAuditStatus);

        String data = requestApi(methodName, systemParam, applicationParamsMap);

        List<AuditStatusParam> auditStatusParams = null;
        try {
            auditStatusParams = JSONArray.parseArray(data, AuditStatusParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return auditStatusParams;
    }

    /**
     * 创建/更新商品(支持商品多规格,不含删除逻辑)
     *
     * @param systemParam 系统参数
     * @param retailParam 商品信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailInitData(SystemParam systemParam, RetailParam retailParam) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailInitdata, systemParam, retailParam);

        //组织应用级参数
        Map<String, String> applicationParamsMap = ConvertUtil.convertToMap(retailParam);
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
    public List<RetailParam> retailList(SystemParam systemParam, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailList, systemParam, appPoiCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailList);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        return parseRetailData(data);
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
    public List<RetailParam> retailListByPage(SystemParam systemParam, String appPoiCode, int offset, int limit) throws ApiOpException,
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
        return parseRetailData(data);
    }

    /**
     * 批量创建/更新商品[支持商品多规格,不含删除逻辑](可以添加sku信息)
     *
     * @param systemParam  系统参数
     * @param retailParams 菜品信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailBatchInitData(SystemParam systemParam, String appPoiCode, List<RetailParam> retailParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailBatchInitdata, systemParam, appPoiCode, JSONObject.toJSONString(retailParams));

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(retailParams));

        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailBatchInitdata);

        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 更新商品sku价格
     *
     * @param systemParam          系统参数
     * @param retailSkuPriceParams 商品sku价格信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String updateRetailSkuPrice(SystemParam systemParam, String appPoiCode, List<RetailSkuPriceParam> retailSkuPriceParams) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(retailSkuPriceParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.UpdateRetailSkuPrice);

        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 更新商品sku库存
     *
     * @param systemParam          系统参数
     * @param retailSkuStockParams 商品sku库存信息
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String updateRetailSkuStock(SystemParam systemParam, String appPoiCode, List<RetailSkuStockParam> retailSkuStockParams) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(retailSkuStockParams));
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
    public RetailParam retailGet(SystemParam systemParam, String appPoiCode, String appFoodCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailGet, systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailGet);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        RetailParam retailParam = null;
        try {
            JSONObject fullFood = JSONObject.parseObject(data);
            if (fullFood.get("skus") != null) {
                JSONArray skuStr = fullFood.getJSONArray("skus");
                fullFood.remove("skus");
                retailParam = fullFood.toJavaObject(RetailParam.class);
                if (!StringUtil.isBlank(skuStr)) {
                    List<RetailSkuParam> skus = skuStr.toJavaList(RetailSkuParam.class);
                    retailParam.setSkus(skus);
                }
            } else {
                retailParam = fullFood.toJavaObject(RetailParam.class);
            }
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return retailParam;
    }

    /**
     * 创建/更新SKU信息
     *
     * @param systemParam     系统参数
     * @param appPoiCode      门店code
     * @param appFoodCode     商品code
     * @param standard_sku    标准sku
     * @param unstandard_skus 非标准sku
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailSkuSave(SystemParam systemParam, String appPoiCode, String appFoodCode, RetailSkuParam standard_sku, List<RetailSkuParam> unstandard_skus) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.RetailSkuSave, systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        if (standard_sku != null) {
            beforeMethod(ParamRequiredEnum.RetailSkuSaveWithStandardSku, standard_sku);
            applicationParamsMap.put("standard_sku", JSON.toJSONString(standard_sku));
        } else {
            applicationParamsMap.put("standard_sku", "");
        }
        if (unstandard_skus != null && unstandard_skus.size() > 0) {
            beforeMethod(ParamRequiredEnum.RetailSkuSaveWithUnStandardSku, JSON.toJSONString(unstandard_skus));
            applicationParamsMap.put("unstandard_skus", JSON.toJSONString(unstandard_skus));
        } else {
            applicationParamsMap.put("unstandard_skus", "");
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailSkuSave);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 获取商品属性
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode 商品code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<RetailProperty> retailPropertyList(SystemParam systemParam, String appPoiCode, String appFoodCode) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //检查必填字段是否都非null及""等特殊值，具体看方法内部处理逻辑
        beforeMethod(ParamRequiredEnum.RetailPropertyList, systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("app_food_code", appFoodCode);
        //检查全部传入的参数（有的方法中会传入非必填字段）是否非null及""等特殊值，具体看房费内部处理逻辑
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.RetailPropertyList);

        String data = requestApi(methodName, systemParam, applicationParamMap);

        List<RetailProperty> retailProperties = null;
        try {
            retailProperties = JSONArray.parseArray(data, RetailProperty.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return retailProperties;
    }

    /**
     * 批量更新售卖状态
     *
     * @param systemParam  系统参数
     * @param appPoiCode   门店code
     * @param retailParams 商品参数
     * @param sellStatus   售卖状态，1表下架，0表上架
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailSkuSellStatus(SystemParam systemParam, String appPoiCode, List<RetailParam> retailParams, Integer sellStatus) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.RetailSkuSellStatus, systemParam, appPoiCode, JSONArray.toJSONString(retailParams), sellStatus);

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("food_data", JSONArray.toJSONString(retailParams));
        applicationParamMap.put("sell_status", String.valueOf(sellStatus));
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.RetailSkuSellStatus);

        return requestApi(methodName, systemParam, applicationParamMap, true);
    }

    /**
     * 删除商品
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode 要删除的商品code
     * @return
     */
    public String retailDelete(SystemParam systemParam, String appPoiCode, String appFoodCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailDelete, systemParam, appPoiCode, appFoodCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailDelete);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 绑定商品属性
     *
     * @param systemParam                 系统参数
     * @param appPoiCode                  门店code
     * @param retailPropertyWithFoodCodes 商品属性
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailBindProperty(SystemParam systemParam, String appPoiCode, List<RetailPropertyWithFoodCode> retailPropertyWithFoodCodes) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        beforeMethod(ParamRequiredEnum.RetailBindProperty, systemParam, appPoiCode, JSONArray.toJSONString(retailPropertyWithFoodCodes));

        //组织应用级参数
        Map<String, String> applicationParamMap = new HashMap<>();
        applicationParamMap.put("app_poi_code", appPoiCode);
        applicationParamMap.put("food_property", JSONArray.toJSONString(retailPropertyWithFoodCodes));
        beforeMethod(systemParam, applicationParamMap, ParamRequiredEnum.RetailBindProperty);

        return requestApi(methodName, systemParam, applicationParamMap);
    }

    /**
     * 删除SKU信息
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param appFoodCode 商品code
     * @param skuId       skuId
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailSkuDelete(SystemParam systemParam, String appPoiCode, String appFoodCode, String skuId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailSkuDelete, systemParam, appPoiCode, appFoodCode, skuId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("app_food_code", appFoodCode);
        applicationParamsMap.put("sku_id", skuId);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailSkuDelete);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 按UPC/EAN批量创建/更新商品
     *
     * @param systemParam  系统参数
     * @param appPoiCode   门店code
     * @param retailParams 商品参数
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String retailBatchInitDataByUpc(SystemParam systemParam, String appPoiCode, List<RetailParam> retailParams) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailBatchInitDataByUpc, systemParam, appPoiCode, JSONObject.toJSONString(retailParams));

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("food_data", JSONObject.toJSONString(retailParams));

        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailBatchInitDataByUpc);

        return requestApi(methodName, systemParam, applicationParamsMap, true);
    }

    /**
     * 获取商品类目
     *
     * @param systemParam 系统参数
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<PLSProductCategory> retailGetSpTagIds(SystemParam systemParam, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailGetSpTagIds, systemParam);

        //组织应用级参数-该接口无参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.RetailGetSpTagIds);

        String data = requestApi(methodName, systemParam, applicationParamsMap);

        List<PLSProductCategory> plsProductCategories = null;
        try {
            plsProductCategories = JSONArray.parseArray(data, PLSProductCategory.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }

        return plsProductCategories;
    }

    /**
     * 视频上传
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param videoDatas  视频字节流
     * @param videoName   视频名称
     * @return
     */
    public List<VideoParam> videoUpload(SystemParam systemParam, String appPoiCode,
                                        byte[] videoDatas, String videoName) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(null, systemParam, appPoiCode, String.valueOf(videoDatas), videoName);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_codes", appPoiCode);
        applicationParamsMap.put("video_name", videoName);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.VideoUpload);

        String data = requestApi(methodName, systemParam, applicationParamsMap, videoDatas, videoName + ".mp4");
        List<VideoParam> videoParams = null;
        try {
            videoParams = JSONArray.parseArray(data, VideoParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }

        return videoParams;
    }

    /**
     * 视频删除
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @return
     */
    public String videoDelete(SystemParam systemParam, String appPoiCode, Long videoId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.RetailDelete, systemParam, appPoiCode, videoId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("video_id", String.valueOf(videoId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.VideoDelete);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }
    /**
     * 将skus转换为list
     *
     * @param data 将单个json格式的对象转换为RetailParam
     * @return com.sankuai.meituan.waimai.opensdk.vo.RetailParam
     * @author wangshiyao02
     * @date 2018/7/3 上午12:23
     */
    private RetailParam parseSingleRetailData(String data) throws ApiSysException {
        RetailParam retailParam = null;
        try {
            JSONObject fullFood = JSONObject.parseObject(data);
            fullFood.put("skus", JSONArray.parseArray(fullFood.getString("skus"), RetailSkuParam.class));
            retailParam = JSONObject.toJavaObject(fullFood, RetailParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return retailParam;
    }

    /**
     * 将json数组转换为list
     *
     * @param data json格式的数组
     * @return java.util.List<com.sankuai.meituan.waimai.opensdk.vo.RetailParam>
     * @author wangshiyao02
     * @date 2018/7/3 上午12:24
     */
    private List<RetailParam> parseRetailData(String data) throws ApiSysException {
        List<RetailParam> retailParams = new ArrayList<>();
        try {
            JSONArray jsonArray = JSONArray.parseArray(data);
            int jsonArraySize = jsonArray.size();
            for (int i = 0; i < jsonArraySize; i++) {
                retailParams.add(parseSingleRetailData(jsonArray.getJSONObject(i).toJSONString()));
            }
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return retailParams;
    }


}
