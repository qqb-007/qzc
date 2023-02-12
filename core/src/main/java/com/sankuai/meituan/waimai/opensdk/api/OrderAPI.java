package com.sankuai.meituan.waimai.opensdk.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sankuai.meituan.waimai.opensdk.constants.ErrorEnum;
import com.sankuai.meituan.waimai.opensdk.constants.ParamRequiredEnum;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.util.StringUtil;
import com.sankuai.meituan.waimai.opensdk.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangzhiqi on 15/10/15.
 */
public class OrderAPI extends API {

    /**
     * 将订单设为商家已收到
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public String orderReceived(SystemParam systemParam, Long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderReceived);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 将订单设为拣货完成
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public String preparationMealComplete(SystemParam systemParam, Long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.PreparationMealComplete);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 商家确认订单
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public String orderConfirm(SystemParam systemParam, Long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderConfirm);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 商家取消订单
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @param reason      取消原因
     * @param reasonCode  取消原因code, 通过取消订单原因列表接口方法获取
     * @return
     */
    public String orderCancel(SystemParam systemParam, Long orderId, String reason, String reasonCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("reason", reason);
        applicationParamsMap.put("reason_code", reasonCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderCancel);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 订单配送中
     *
     * @param systemParam  系统参数
     * @param orderId      订单id
     * @param courierName  配送员姓名
     * @param courierPhone 配送电话
     * @return
     */
    public String orderDelivering(SystemParam systemParam, Long orderId, String courierName, String courierPhone) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        if (StringUtil.isNotBlank(courierName)) {
            applicationParamsMap.put("courier_name", courierName);
        }
        if (StringUtil.isNotBlank(courierPhone)) {
            applicationParamsMap.put("courier_phone", courierPhone);
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderDelivering);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 订单已送达(如接入美团配送则无需接)
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public String orderArrived(SystemParam systemParam, Long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderArrived);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 订单确认退款请求
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @param reason      确认退款详情
     * @return
     */
    public String orderRefundAgree(SystemParam systemParam, Long orderId, String reason) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderRefundAgree, systemParam, orderId, reason);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("reason", reason);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderRefundAgree);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 驳回订单退款申请
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @param reason      驳回退款详情
     * @return
     */
    public String orderRefundReject(SystemParam systemParam, Long orderId, String reason) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderRefundReject, systemParam, orderId, reason);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("reason", reason);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderRefundReject);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 获取订单商家补贴款
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public OrderSubsidyParam orderSubsidy(SystemParam systemParam, Long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderSubsidy, systemParam, orderId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderSubsidy);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        OrderSubsidyParam orderSubsidyParam = null;
        try {
            orderSubsidyParam = JSONObject.parseObject(data, OrderSubsidyParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orderSubsidyParam;
    }

    /**
     * 查询订单状态
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public Integer orderViewStatus(SystemParam systemParam, Long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderViewStatus, systemParam, orderId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderViewStatus);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        Integer orderStatus = null;
        try {
            String status = JSONObject.parseObject(data).get("status").toString();
            orderStatus = Integer.parseInt(status);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orderStatus;
    }

    /**
     * 查询活动信息
     *
     * @param systemParam 系统参数
     * @param actDetailId 活动ID
     * @return
     */
    public PoiPolicyParam orderGetActDetailByAcId(SystemParam systemParam, int actDetailId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetActDetailByAcId, systemParam, actDetailId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("act_detail_id", String.valueOf(actDetailId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetActDetailByAcId);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        PoiPolicyParam poiPolicyParam = null;
        try {
            poiPolicyParam = JSONObject.parseObject(data, PoiPolicyParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return poiPolicyParam;
    }

    /**
     * 获取订单详细信息
     *
     * @param systemParam   系统参数
     * @param orderId       订单id
     * @param isMtLogistics 是否为美团配送（当需要查询美团配送的详细信息时此字段需要为1）
     * @return
     */
    public OrderDetailParam orderGetOrderDetail(SystemParam systemParam, long orderId, Long isMtLogistics) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetOrderDetail, systemParam, orderId, isMtLogistics);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        if (StringUtil.isNotBlank(isMtLogistics)) {
            applicationParamsMap.put("is_mt_logistics", String.valueOf(isMtLogistics));
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetOrderDetail);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        OrderDetailParam orderDetailParam = null;
        try {
            JSONObject jo = JSONObject.parseObject(data);
            jo.put("detail", JSONArray.parseArray(jo.getString("detail"), OrderFoodDetailParam.class));
            jo.put("extras", JSONArray.parseArray(jo.getString("extras"), OrderExtraParam.class));
            jo.put("poi_receive_detail_yuan", JSONObject.parseObject(jo.getString("poi_receive_detail_yuan"), PoiReceiveDetil.class));
            orderDetailParam = JSONObject.toJavaObject(jo, OrderDetailParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orderDetailParam;
    }

    /**
     * 下发美团配送订单(接入美团配送选接)
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public String orderLogisticsPush(SystemParam systemParam, long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderLogisticsPush, systemParam, orderId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderLogisticsPush);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 取消美团配送订单(接入美团配送选接)
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public String orderLogisticsCancel(SystemParam systemParam, long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderLogisticsCancel, systemParam, orderId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderLogisticsCancel);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 自配送商家同步配送信息
     *
     * @param systemParam 系统参数
     * @param param       订单id
     * @return
     */
    public String orderLogisticsSync(SystemParam systemParam, OrderLogisticsSyncParam param) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

//        beforeMethod(ParamRequiredEnum.OrderLogisticsCancel, systemParam, param);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", param.getOrder_id());
        applicationParamsMap.put("courier_name", param.getCourier_name());
        applicationParamsMap.put("courier_phone", param.getCourier_phone());
        if (param.getLogistics_provider_code() != null) {
            applicationParamsMap.put("logistics_provider_code", param.getLogistics_provider_code());
        }
        if (param.getThird_carrier_order_id() != null) {
            applicationParamsMap.put("third_carrier_order_id", param.getThird_carrier_order_id());
        }
        if (param.getLogistics_status() != null) {
            applicationParamsMap.put("logistics_status", param.getLogistics_status().toString());
        }
        if (param.getLatitude() != null) {
            applicationParamsMap.put("latitude", param.getLatitude());
        }
        if (param.getLongitude() != null) {
            applicationParamsMap.put("longitude", param.getLongitude());
        }
//        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderLogisticsCancel);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 获取配送订单状态(接入美团配送选接)
     *
     * @param systemParam 系统参数
     * @param orderId     订单id
     * @return
     */
    public LogisticsParam orderLogisticsStatus(SystemParam systemParam, long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderLogisticsStatus, systemParam, orderId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderLogisticsStatus);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        LogisticsParam logisticsParam = null;
        try {
            logisticsParam = JSONObject.parseObject(data, LogisticsParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return logisticsParam;
    }

    /**
     * 获取最新日订单流水号
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public Long orderGetDaySeq(SystemParam systemParam, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetDaySeq, systemParam, appPoiCode);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetDaySeq);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        Long daySeq = null;
        try {
            daySeq = JSONObject.parseObject(data).getLong("day_seq");
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return daySeq;
    }

    /**
     * 根据日订单流水号获取订单ID
     *
     * @param systemParam 系统参数
     * @param appPoiCode  门店code
     * @param dateTime    日期，整型数据(eg: 20151201)
     * @param daySeq      订单流水号
     * @return 订单号
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public Long orderGetOrderIdByDaySeq(SystemParam systemParam, String appPoiCode, Integer dateTime, Integer daySeq) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetOrderIdByDaySeq, systemParam, appPoiCode, dateTime, daySeq);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("date_time", String.valueOf(dateTime));
        applicationParamsMap.put("day_seq", String.valueOf(daySeq));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetOrderIdByDaySeq);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        Long orderId = null;
        try {
            orderId = JSONObject.parseObject(data).getLong("order_id");
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orderId;
    }

    /**
     * 批量查询众包配送费
     *
     * @param systemParam
     * @param orderIds
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<OrderZhongbaoShippingFeeParam> orderZhongbaoShippingFee(SystemParam systemParam, String orderIds) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderZhongbaoShippingFee, systemParam, orderIds);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_ids", orderIds);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderZhongbaoShippingFee);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        List<OrderZhongbaoShippingFeeParam> orderZhongbaoShippingFeeParams = null;
        try {
            orderZhongbaoShippingFeeParams = JSONObject.parseArray(data, OrderZhongbaoShippingFeeParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return orderZhongbaoShippingFeeParams;
    }

    /**
     * 众包发配送
     *
     * @param systemParam 系统参数
     * @param orderId     订单号
     * @param shippingFee 配送费
     * @param tipAmount   小费金额
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderZhongbaoDispatch(SystemParam systemParam, long orderId, double shippingFee, double tipAmount) throws ApiOpException,
            ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderZhongbaoDispatch, systemParam, orderId);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("shipping_fee", String.valueOf(shippingFee));
        applicationParamsMap.put("tip_amount", String.valueOf(tipAmount));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderPrepareZhongbaoDispatch);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 众包配送预下单
     *
     * @param systemParam
     * @param orderId
     * @param oldShippingFee
     * @param tipAmount
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     * @deprecated 与 {@link #orderZhongbaoDispatchConfirm}统一合并为{@link #orderZhongbaoDispatch}
     */
    @Deprecated
    public String orderZhongbaoDispatchPrepare(SystemParam systemParam, long orderId, double oldShippingFee, double tipAmount) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderPrepareZhongbaoDispatch, systemParam, orderId);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("old_shipping_fee", String.valueOf(oldShippingFee));
        applicationParamsMap.put("tip_amount", String.valueOf(tipAmount));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderPrepareZhongbaoDispatch);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 众包配送确认下单
     *
     * @param systemParam
     * @param orderId
     * @param tipAmount
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     * @deprecated 与 {@link #orderZhongbaoDispatchPrepare}统一合并为{@link #orderZhongbaoDispatch}
     */
    @Deprecated
    public String orderZhongbaoDispatchConfirm(SystemParam systemParam, long orderId, double tipAmount) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderConfirmZhongbaoDispatch, systemParam, orderId);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("tip_amount", String.valueOf(tipAmount));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderConfirmZhongbaoDispatch);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 众包配送单追加小费
     *
     * @param systemParam
     * @param orderId
     * @param tipAmount
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderZhongbaoUpdateTip(SystemParam systemParam, long orderId, double tipAmount) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderUpdateZhongbaoTip, systemParam, orderId);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("tip_amount", String.valueOf(tipAmount));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderUpdateZhongbaoTip);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 获取取消跑腿配送原因列表
     *
     * @param systemParam
     * @param orderId
     * @param appPoiCode
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public CancelDeliveryReasonParam getCancelDeliveryReason(SystemParam systemParam, long orderId, String appPoiCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.GetCancelDeliveryReason, systemParam, orderId);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("app_poi_code", appPoiCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.GetCancelDeliveryReason);

        String data = requestApi(methodName, systemParam, applicationParamsMap);
        System.out.println(data);
        CancelDeliveryReasonParam cancelDeliveryReasonParam = null;
        try {
            JSONObject jo = JSONObject.parseObject(data);
            jo.put("reasonList", JSONArray.parseArray(jo.getString("reasonList"), CancelReson.class));
            cancelDeliveryReasonParam = JSONObject.toJavaObject(jo, CancelDeliveryReasonParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }



        return cancelDeliveryReasonParam;
    }

    /**
     * 取消跑腿配送
     *
     * @param systemParam
     * @param orderId
     * @param appPoiCode
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String cancelLogisticsByWmOrderId(SystemParam systemParam, String reasonCode, String detailContent, String appPoiCode, long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.CancelLogisticsByWmOrderId, systemParam, orderId);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("app_poi_code", appPoiCode);
        applicationParamsMap.put("reason_code", reasonCode);
        applicationParamsMap.put("detail_content", detailContent);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.CancelLogisticsByWmOrderId);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 获取订单评价信息
     *
     * @param systemParam
     * @param orderId
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     * @deprecated {@date 2017-09-19}起废弃此接口，不再提供与订单有关的评论接口
     */
    @Deprecated
    public String orderCommentOrder(SystemParam systemParam, long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderCommentOrder, systemParam, orderId);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderCommentOrder);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 根据评价id添加商家回复
     *
     * @param systemParam
     * @param orderId
     * @param commentId
     * @param reply
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     * @deprecated {@date 2017-09-19}起废弃此接口，不再提供与订单有关的评论接口
     */
    @Deprecated
    public String orderCommentAddReply(SystemParam systemParam, long orderId, long commentId, String reply) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderCommentAddReply, systemParam, orderId);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("comment_id", String.valueOf(commentId));
        applicationParamsMap.put("reply", reply);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderCommentAddReply);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 查询部分退款菜品详情
     *
     * @param systemParam 系统参数
     * @param orderId     订单号
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<FoodPartRefundParam> orderGetPartRefundFoods(SystemParam systemParam, Long orderId) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetPartRefundFoods, systemParam, orderId);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetPartRefundFoods);

        String data = requestApi(methodName, systemParam, applicationParamsMap);

        List<FoodPartRefundParam> foodPartRefundParams = null;
        try {
            foodPartRefundParams = JSONArray.parseArray(data, FoodPartRefundParam.class);
        } catch (Exception e) {
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
        return foodPartRefundParams;
    }

    /**
     * 获取订单退款记录
     *
     * @param systemParam      系统参数
     * @param wm_order_id_view 订单展示id
     * @param refund_type      1:全额退款,2:部分退款,不传代表全部。
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public List<OrderRefundDetailParam> orderGetRefundDetail(SystemParam systemParam, Long wmOrderIdView, Integer refundType) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderRefundDetail, systemParam, wmOrderIdView);

        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("wm_order_id_view", String.valueOf(wmOrderIdView));
        if (refundType != null) {
            applicationParamsMap.put("refund_type", String.valueOf(refundType));
        }
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderRefundDetail);

        String data = requestApi(methodName, systemParam, applicationParamsMap);

        try {
            List<OrderRefundDetailParam> detailParams = JSONObject.parseArray(data, OrderRefundDetailParam.class);
            return detailParams;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiSysException(ErrorEnum.SYS_ERR);
        }
    }

    /**
     * 申请部分退款
     *
     * @param systemParam          系统参数
     * @param orderId              订单号
     * @param reason               申请部分退款原因
     * @param foodPartRefundParams 退款菜品
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderApplyPartRefund(SystemParam systemParam, Long orderId, String reason, List<FoodPartRefundParam> foodPartRefundParams) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderApplyPartRefund, systemParam, orderId, reason, foodPartRefundParams);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", String.valueOf(orderId));
        applicationParamsMap.put("reason", reason);
        applicationParamsMap.put("food_data", JSONArray.toJSONString(foodPartRefundParams));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderApplyPartRefund);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 催单回复
     *
     * @param systemParam   系统参数
     * @param order_id      订单号
     * @param reply_id      回复ID
     * @param reply_content 回复内容
     * @param remind_id     催单id
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderRemindReply(SystemParam systemParam, String order_id, Integer reply_id,
                                   String reply_content, Long remind_id) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderRemindReply, systemParam, order_id, reply_id, reply_content, remind_id);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", order_id);
        applicationParamsMap.put("reply_id", String.valueOf(reply_id));
        applicationParamsMap.put("reply_content", reply_content);
        applicationParamsMap.put("remind_id", String.valueOf(remind_id));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderRemindReply);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 查询可申请餐损赔付的订单
     *
     * @param systemParam  系统参数
     * @param app_poi_code 门店编号
     * @param offset       偏移量
     * @param limit        条数
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderGetSupportedCompensation(SystemParam systemParam, String app_poi_code, Integer offset, Integer limit) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetSupportedCompensation, systemParam, app_poi_code, offset, limit);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", app_poi_code);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetSupportedCompensation);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 申请餐损赔付
     *
     * @param systemParam  系统参数
     * @param order_id     订单编号
     * @param apply_status 赔付状态
     * @param amount       金额
     * @param reason       赔付原因
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderApplyCompensation(SystemParam systemParam, String order_id, Integer apply_status, Double amount, String reason) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderApplyCompensation, systemParam, order_id, apply_status, amount, reason);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", order_id);
        applicationParamsMap.put("apply_status", String.valueOf(apply_status));
        applicationParamsMap.put("amount", String.valueOf(amount));
        applicationParamsMap.put("reason", reason);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderApplyCompensation);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 查询餐损赔付结果
     *
     * @param systemParam 系统参数
     * @param order_id    订单编号
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderGetCompensationResult(SystemParam systemParam, String order_id) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetCompensationResult, systemParam, order_id);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", order_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetCompensationResult);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 隐私号拉取接口
     *
     * @param systemParam  系统参数
     * @param app_poi_code 门店编号
     * @param offset       偏移量
     * @param limit        条数
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderBatchPullPhoneNumber(SystemParam systemParam, String app_poi_code, Integer offset, Integer limit) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderBatchPullPhoneNumber, systemParam, app_poi_code, offset, limit);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("app_poi_code", app_poi_code);
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderBatchPullPhoneNumber);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 商家确认已完成出餐
     *
     * @param systemParam 系统参数
     * @param order_id    订单编号
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderPreparationMealComplete(SystemParam systemParam, String order_id) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderPreparationMealComplete, systemParam, order_id);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", order_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderPreparationMealComplete);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 商家获取备餐时间
     *
     * @param systemParam 系统参数
     * @param order_id    订单编号
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderGetPreparationMealTime(SystemParam systemParam, String order_id) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderGetPreparationMealTime, systemParam, order_id);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", order_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderGetPreparationMealTime);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 批量拉取异常订单
     *
     * @param systemParam 系统参数
     * @param type        异常订单类型
     * @param start_time  开始时间
     * @param end_time    结束时间
     * @param offset      偏移量
     * @param limit       条数
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderBatchFetchAbnormalOrder(SystemParam systemParam, Integer type, Integer start_time, Integer end_time, Integer offset, Integer limit) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderBatchFetchAbnormalOrder, systemParam, type, start_time, end_time, offset, limit);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("type", String.valueOf(type));
        applicationParamsMap.put("start_time", String.valueOf(start_time));
        applicationParamsMap.put("end_time", String.valueOf(end_time));
        applicationParamsMap.put("offset", String.valueOf(offset));
        applicationParamsMap.put("limit", String.valueOf(limit));
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderBatchFetchAbnormalOrder);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    /**
     * 订单转自配
     *
     * @param systemParam 系统参数
     * @param order_id    订单编号
     * @return
     * @throws ApiOpException
     * @throws ApiSysException
     */
    public String orderChangeToPoiSelfShipping(SystemParam systemParam, String order_id) throws
            ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderChangeToPoiSelfShipping, systemParam, order_id);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", order_id);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderChangeToPoiSelfShipping);

        return requestApi(methodName, systemParam, applicationParamsMap);
    }

    public String orderSetPickCode(SystemParam systemParam, String order_id, String pickCode) throws ApiOpException, ApiSysException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        beforeMethod(ParamRequiredEnum.OrderSetPickCode, systemParam, order_id);
        //组织应用级参数
        Map<String, String> applicationParamsMap = new HashMap<>();
        applicationParamsMap.put("order_id", order_id);
        applicationParamsMap.put("pick_code", pickCode);
        beforeMethod(systemParam, applicationParamsMap, ParamRequiredEnum.OrderSetPickCode);


        return requestApi(methodName, systemParam, applicationParamsMap);
    }

}
