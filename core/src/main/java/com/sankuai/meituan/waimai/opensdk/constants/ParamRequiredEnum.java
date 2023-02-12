package com.sankuai.meituan.waimai.opensdk.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yangzhiqi on 15/10/29.
 */
public enum ParamRequiredEnum {
    SysParam("appId, appSecret, timestamp"),
    //门店
    //PoiSave("app_poi_code, name, address, latitude, longitude, phone, shipping_fee, shipping_time, open_level, is_online, third_tag_name"),
    PoiSave("app_poi_code"),
    PoiMGet("app_poi_codes"),
    PoiOPen("app_poi_code"),
    PoiClose("app_poi_code"),
    PoiOffline("app_poi_code, reason"),
    PoiOnline("app_poi_code"),
    PoiQualifySave("app_poi_code, type, qualification_url"), // 已废弃
    PoiSendTimeSave("app_poi_codes, send_time"),
    PoiAdditionalSave("app_poi_code"),
    PoiUpdatePromotionInfo("app_poi_code"),
    PoiUpdateShippingTime("app_poi_code, shipping_time"),
    PoiIsDelayPushLogistics("app_poi_code"),
    PoiSetDelayPushLogistics("app_poi_code, delay_seconds"),
    PoiComment("app_poi_code, start_time, end_time, pageoffset, pagesize"),
    PoiCommentQuery("app_poi_code, start_time, end_time, pageoffset, pagesize,replyStatus"),
    PoiCommentAddReply("app_poi_code, comment_id, reply"),
    PoiCommentScore("app_poi_code"),

    //配送
    ShippingSave("app_poi_code, app_shipping_code, type, area, min_price"),
    ShippingList("app_poi_code"),
    ShippingBatchSave("app_poi_code, shipping_data"),
    ShippingFetch("app_poi_code"),
    ShippingDelete("app_poi_code, app_shipping_code"),

    //菜品
    FoodCatUpdate("app_poi_code, category_name"),
    FoodCatDelete("app_poi_code, category_name"),
    FoodSave("app_poi_code, app_food_code"),
    FoodInitData("app_poi_code, app_food_code, category_name"),
    FoodDelete("app_poi_code, app_food_code"),
    FoodList("app_poi_code"),
    FoodListByPage("app_poi_code, offset, limit"),
    FoodBatchSave("app_poi_code, food_data"),
    FoodBatchInitData("app_poi_code, food_data"),
    FoodCatList("app_poi_code"),
    FoodSkuSave("app_poi_code, app_food_code, skus"),
    FoodSkuDelete("app_poi_code, app_food_code, sku_id"),
    UpdateFoodSkuPrice("app_poi_code, food_data"),
    UpdateFoodSkuStock("app_poi_code, food_data"),
    incFoodSkuStock("app_poi_code, food_data"),
    descFoodSkuStock("app_poi_code, food_data"),
    FoodBindProperty("app_poi_code, food_property"),
    FoodPropertyList("app_poi_code, app_food_code"),
    FoodGet("app_poi_code, app_food_code"),
    FoodSkuSellStatusUpdate("app_poi_code, food_data, sell_status"),
    UpdateAppFoodCodeByOrigin("app_food_code_origin,app_poi_code, app_food_code, sku_id_origin, sku_id"),
    UpdateAppFoodCodeByNameAndSpec("app_poi_code,name,category_name, app_food_code, sku_id, spec"),


    //药品
    MedicineCatSave("app_poi_code, category_code, category_name, sequence"),
    MedicineCatUpdate("app_poi_code, category_code"),
    MedicineCatDelete("app_poi_code, category_code"),
    MedicineCatList("app_poi_code"),
    MedicineSave("app_poi_code, app_medicine_code, upc, medicine_no, spec, price, stock, category_code, category_name, is_sold_out"),
    MedicineUpdate("app_poi_code, app_medicine_code"),
    MedicineBatchSave("app_poi_code, medicine_data"),
    MedicineBatchUpdate("app_poi_code, medicine_data"),
    MedicineDelete("app_poi_code, app_medicine_code"),
    MedicineList("app_poi_code"),
    MedicineStock("app_poi_code, medicine_data"),


    //零售
    RetailCatUpdate("app_poi_code, category_name"),
    RetailCatDelete("app_poi_code, category_name"),
    RetailCatList("app_poi_code"),
    RetailInitdata("app_poi_code, app_food_code, category_name"),
    RetailList("app_poi_code"),
    RetailListByPage("app_poi_code, offset, limit"),
    RetailAuditStatus("app_poi_code, audit_status, page_num,page_size"),
    RetailBatchInitdata("app_poi_code, food_data"),
    UpdateRetailSkuPrice("app_poi_code, food_data"),
    UpdateRetailSkuStock("app_poi_code, food_data"),
    RetailGet("app_poi_code, app_food_code"),
    RetailSkuSave("app_poi_code, app_food_code"),
    RetailSkuSaveWithStandardSku("sku_id,price,stock"),
    RetailSkuSaveWithUnStandardSku("sku_id,spec,price,stock"),
    RetailPropertyList("app_poi_code, app_food_code"),
    RetailSkuSellStatus("app_poi_code, food_data, sell_status"),
    RetailDelete("app_poi_code, app_food_code"),
    RetailBindProperty("app_poi_code, food_property"),
    RetailSkuDelete("app_poi_code, app_food_code, sku_id"),
    RetailBatchInitDataByUpc("app_poi_code,food_data"),
    RetailGetSpTagIds("app_poi_code"),


    //活动
    ActDiscountBatchSave("app_poi_code, act_data"),
    ActDiscountDelete("app_poi_code, item_ids"),
    ActDiscountStock("app_poi_code, act_data"),
    ActDiscountList("app_poi_code, offset, limit"),
    ActDiscountActivityOrderLimit("app_poi_code, act_type, act_prod_count"),
    ActSecondHalfBatchSave("app_poi_code, act_data"),
    ActSecondHalfDelete("app_poi_code, app_food_codes"),
    ActSecondHalfStock("app_poi_code, act_data"),
    ActSecondHalfList("app_poi_code, offset, limit"),
    ActBuyGiftsBatchSave("app_poi_code, act_data"),
    ActBuyGiftsDelete("app_poi_code, item_ids"),
    ActBuyGiftsStock("app_poi_code, act_data"),
    ActBuyGiftsList("app_poi_code, offset, limit"),
    ActFullDiscountBatchSave("app_poi_code, act_info, act_details, app_foods"),
    ActFullDiscountList("app_poi_code"),
    ActFullDiscountDelete("app_poi_code, act_ids"),
    ActFullDiscountFoodsBatchSave("app_poi_code, act_id, app_foods"),
    ActFullDiscountFoodsList("app_poi_code, act_id, offset, limit"),
    ActFullDiscountFoodsDelete("app_poi_code, act_id, app_food_codes"),
    ActFullDiscountFoodsDayLimit("app_poi_code, act_id, app_foods"),
    ActRetailDiscountBatchsave("app_poi_code, act_data"),
    ActGoodsCouponSave("goods_coupon_data"),
    ActDeleteActsByProducts("app_poi_code, app_spu_codes, type"),
    ActRetailDiscountList("app_poi_code, offset, limit"),
    ActGoodsCouponList("app_poi_code, act_status,start_time,end_time,page_num,page_size"),
    ActAllGetByAppFoodCodes("app_poi_code, app_food_codes,status,query_type,page_num, page_size"),
    ActItemBundlesSave("app_poi_code, act_data"),


    //订单
    OrderReceived("order_id"),
    OrderConfirm("order_id"),
    OrderCancel("order_id, reason, reason_code"),
    OrderDelivering("order_id"),
    OrderArrived("order_id"),
    OrderRefundAgree("order_id, reason"),
    OrderRefundReject("order_id, reason"),
    OrderSubsidy("order_id"),
    OrderViewStatus("order_id"),
    OrderGetActDetailByAcId("act_detail_id"),
    OrderGetOrderDetail("order_id"),
    OrderLogisticsPush("order_id"),
    OrderLogisticsCancel("order_id"),
    OrderLogisticsStatus("order_id"),
    OrderGetDaySeq("app_poi_code"),
    OrderGetOrderIdByDaySeq("app_poi_code, date_time, day_seq"),
    OrderZhongbaoShippingFee("order_ids"),
    OrderZhongbaoDispatch("order_id, shipping_fee, tip_amount"),
    OrderPrepareZhongbaoDispatch("order_id"),
    OrderConfirmZhongbaoDispatch("order_id"),
    OrderUpdateZhongbaoTip("order_id, tip_amount"),
    GetCancelDeliveryReason("order_id, app_poi_code"),
    CancelLogisticsByWmOrderId("order_id, app_poi_code"),
    OrderCommentOrder("order_id"), // 已废弃
    OrderCommentAddReply("order_id, comment_id, reply"), // 已废弃
    OrderGetPartRefundFoods("order_id"),
    OrderRefundDetail("wm_order_id_view"),
    OrderApplyPartRefund("order_id, reason, food_data"),
    OrderRemindReply("order_id, reply_id, reply_content,remind_id"),
    OrderGetSupportedCompensation("app_poi_code, offset, limit"),
    OrderApplyCompensation("order_id, apply_status, amount,reason"),
    OrderGetCompensationResult("order_id"),
    OrderBatchPullPhoneNumber("app_poi_code, offset, limit"),
    OrderPreparationMealComplete("order_id"),
    OrderGetPreparationMealTime("order_id"),
    PreparationMealComplete("order_id"),
    OrderBatchFetchAbnormalOrder("type, start_time, end_time, offset, limit"),
    OrderChangeToPoiSelfShipping("order_id"),
    OrderSetPickCode("order_id, pick_code"),
    //图片
    VideoUpload("app_poi_codes, video_name"),
    VideoDelete("app_poi_code, video_id"),

    //图片
    ImageUpload("app_poi_code, img_name");


    private String paramNames;

    ParamRequiredEnum(String paramNames) {
        this.paramNames = paramNames;
    }

    public String getParamNames() {
        return paramNames;
    }

    public static List<String> getParams(ParamRequiredEnum paramRequiredEnum) {
        String paramNames = paramRequiredEnum.getParamNames();
        List<String> paramNameListTemp = Arrays.asList(paramNames.split(","));
        List<String> paramNameList = new ArrayList<>();
        for (String paramName : paramNameListTemp) {
            paramNameList.add(paramName.trim());
        }
        return paramNameList;
    }
}
