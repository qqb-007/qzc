package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import info.batcloud.wxc.core.annotation.OrderNotificationTrace;
import info.batcloud.wxc.core.aop.OrderNotificationTraceAspect;
import info.batcloud.wxc.core.config.ClbmApp;
import info.batcloud.wxc.core.domain.meituan.OrderPartRefundRequest;
import info.batcloud.wxc.core.domain.meituan.OrderRefundRequest;
import info.batcloud.wxc.core.enums.*;
import info.batcloud.wxc.core.helper.MeituanHelper;
import info.batcloud.wxc.core.helper.URLDecodeHelper;
import info.batcloud.wxc.core.service.ClbmWaiMaiService;
import info.batcloud.wxc.core.service.OrderService;
import info.batcloud.wxc.core.service.StoreService;
import info.batcloud.wxc.core.settings.MeituanWaimaiAppSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RequestMapping("/clbm/notify")
@RestController
public class ClbmNotifyController {
    @Inject
    private OrderService orderService;
    @Inject
    private ClbmWaiMaiService clbmWaiMaiService;
    @Inject
    private StoreService storeService;
    @Inject
    private ClbmApp clbmApp;

    private static final Logger logger = LoggerFactory.getLogger(ClbmNotifyController.class);

    @RequestMapping("/order")
    @OrderNotificationTrace(plat = Plat.CLBM, type = OrderNotificationType.ORDER, orderId = "#0.order_id", data = "#0")
    public Object order(@RequestParam Map<String, String> data, HttpServletRequest request) {
        try {
            URLDecodeHelper.decode(data);
            if (!clbmWaiMaiService.checkSign(data, clbmApp.getOrderPaidNotifyUrl())) {
                return MeituanHelper.ok();
            }
            if (StringUtils.isEmpty(data.get("order_id"))) {
                return MeituanHelper.ok();
            }
            logger.info("???????????????????????????????????????orderId???" + data.get("order_id"));
            OrderDetailParam orderDetailParam = APIFactory.getOrderAPI().orderGetOrderDetail(clbmWaiMaiService.getSystemParam(),
                    Long.valueOf(data.get("order_id")), 0L);

            OrderService.OrderSaveResult rs = orderService.saveOrder(ClbmWaiMaiService.toOrder(orderDetailParam));
            if (rs.isSuccess()) {
                logger.info("??????????????????????????????orderId???" + data.get("order_id"));
            } else {
                logger.info("??????????????????????????????orderId???" + data.get("order_id"));
            }
            OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
            tr.setErrMsg(rs.getMsg() + " " + rs.getErrMsg());
            tr.setSuccess(rs.isSuccess());
            OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        } catch (Exception e) {
            logger.error("?????????????????????????????????", e);
        }
        return MeituanHelper.ok();
    }

    @RequestMapping("/order/cancel")
    @OrderNotificationTrace(plat = Plat.CLBM, type = OrderNotificationType.CANCEL, orderId = "#0.order_id", data = "#0")
    public Object orderCancel(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        if (!clbmWaiMaiService.checkSign(data, clbmApp.getOrderCancelNotifyUrl())) {
            return MeituanHelper.ok();
        }
        Long orderId = Long.valueOf(data.get("order_id"));
        if (orderId == null) {
            return MeituanHelper.ok();
        }
        logger.info("???????????????????????????");
        orderService.cancelOrderByPlat(Plat.CLBM, orderId.toString(), Integer.valueOf(data.get("reason_code")), data.get("reason"));
        orderService.printCancelInfo(Plat.CLBM, orderId.toString());
        OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
        tr.setSuccess(true);
        OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        return MeituanHelper.ok();
    }

    @RequestMapping("/order/finish")
    @OrderNotificationTrace(plat = Plat.CLBM, type = OrderNotificationType.FINISH, orderId = "#0.order_id", data = "#0")
    public Object orderFinish(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        if (!clbmWaiMaiService.checkSign(data, clbmApp.getOrderFinishNotifyUrl())) {
            return MeituanHelper.ok();
        }
        if (StringUtils.isEmpty(data.get("order_id"))) {
            return MeituanHelper.ok();
        }
        logger.info("????????????????????????????????????");
        try {
            OrderService.OrderSaveResult rs = orderService.syncPlatOrder(Plat.CLBM, data.get("order_id"), true);
            OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
            tr.setErrMsg(rs.getMsg() + " " + rs.getErrMsg());
            tr.setSuccess(rs.isSuccess());
            OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        } catch (Exception e) {
            logger.error("?????????????????????????????????", e);
        }
        return MeituanHelper.ok();
    }

    @RequestMapping("/order/remind")
    public Object remindOrder() {

        return MeituanHelper.ok();
    }

    @RequestMapping("/order/peisong")
    public Object orderPeisong(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        logger.info("????????????????????????????????????????????????");
        logger.info(data.toString());
        if (!clbmWaiMaiService.checkSign(data, "https://api.wangxiaocai.cn/clbm/notify/order/peisong")) {
            logger.error("????????????");
            return MeituanHelper.ok();
        }

        Boolean p = true;

        try {
            String platOrderId = data.get("order_id");
            if (platOrderId == null) {
                return MeituanHelper.ok();
            }
            Integer status = Integer.valueOf(data.get("logistics_status"));
            OrderService.PeisongInfo info = new OrderService.PeisongInfo();
            info.setPtPeisongId(platOrderId);
            info.setType(DeliveryType.ZHONGBAO);
            info.setPlat(Plat.CLBM);
            if (status == 10 || status == 15) {
                String name = data.get("dispatcher_name");
                String phone = data.get("dispatcher_mobile");
                info.setStatus(DeliveryStatus.ACCEPT);
                info.setCourierName(name);
                info.setCourierPhone(phone);
                logger.info("???????????????" + name + phone);
            } else if (status == 20) {
                String name = data.get("dispatcher_name");
                String phone = data.get("dispatcher_mobile");
                logger.info("???????????????" + name + phone);
                info.setStatus(DeliveryStatus.TAKEN);
                info.setCourierName(name);
                info.setCourierPhone(phone);
            } else if (status == 40) {
                String name = data.get("dispatcher_name");
                String phone = data.get("dispatcher_mobile");
                logger.info("???????????????" + name + phone);
                info.setStatus(DeliveryStatus.ARRIVED);
                info.setCourierName(name);
                info.setCourierPhone(phone);
            } else if (status == 100) {
                String name = data.get("dispatcher_name");
                String phone = data.get("dispatcher_mobile");
                logger.info("???????????????" + name + phone);
                info.setStatus(DeliveryStatus.CANCELED);
                info.setCourierName(name);
                info.setCourierPhone(phone);
            } else if (status == 10 || status == 0 || status == 5) {
                logger.info("???????????????");
                info.setStatus(DeliveryStatus.WAIT_SCHEDULE);

            }
//            if (info.getStatus() == DeliveryStatus.WAIT_SCHEDULE) {
//                //?????????????????????????????????????????????
//                logger.info("????????????????????? ?????????" + platOrderId);
//                return MeituanHelper.ok();
//            }
            DeliveryType type = null;
            try {
                type = orderService.updateOrderPeisongInfo(info);
            } catch (Exception e) {
                logger.error("????????????????????????????????????  ?????????????????? ????????????????????????" + platOrderId, e);
                p = false;
            }
            if (type != null) {
                logger.info("?????????????????????????????????");
                orderService.updatePiesonInfoToPlat(info, type);
            }


        } catch (Exception e) {
            logger.error("????????????????????????????????? ???????????????????????????????????????" + e.getMessage());
            p = false;
        }
        if (p) {
            return MeituanHelper.ok();
        } else {
            return "fail";
        }


    }

    @RequestMapping("/order/confirm")
    @OrderNotificationTrace(plat = Plat.CLBM, type = OrderNotificationType.CONFIRM, orderId = "#0.order_id", data = "#0")
    public Object confirmOrder(@RequestParam Map<String, String> data, HttpServletRequest request) {
        try {
            URLDecodeHelper.decode(data);
            if (!clbmWaiMaiService.checkSign(data, clbmApp.getOrderConfirmNotifyUrl())) {
                return MeituanHelper.ok();
            }
            if (StringUtils.isEmpty(data.get("order_id"))) {
                return MeituanHelper.ok();
            }
            String platOrderId = data.get("order_id");
            logger.info("????????????????????????????????????, platOrderId:" + platOrderId);
            orderService.confirmOrderByPlat(Plat.CLBM, platOrderId);
            OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
            tr.setErrMsg("");
            tr.setSuccess(true);
            OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        } catch (Exception e) {
            logger.error("????????????????????????", e);
        }
        return MeituanHelper.ok();
    }


    /**
     * ????????????????????????
     */
    @RequestMapping("/shop/status")
    public Object shopStatus(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        logger.info("??????????????????????????????????????????????????????");
        if (!clbmWaiMaiService.checkSign(data, "https://api.wangxiaocai.cn/clbm/notify/shop/status")) {
            logger.error("????????????");
            return MeituanHelper.ok();
        }
        if (StringUtils.isEmpty(data.get("app_poi_code"))) {
            return MeituanHelper.ok();
        }
        String code = data.get("app_poi_code");
        Integer status = Integer.valueOf(data.get("poi_status"));
        try {
            if (status == 121) {
                storeService.updateOpenStatus(code, Plat.CLBM, true);
            } else if (status == 120) {
                storeService.updateOpenStatus(code, Plat.CLBM, false);
            }
        } catch (Exception e) {
            logger.error("?????????????????????????????????????????????" + e.getMessage());
        }

        return MeituanHelper.ok();
    }

    @RequestMapping("/order/refund")
    @OrderNotificationTrace(plat = Plat.CLBM, type = OrderNotificationType.REFUND, orderId = "#0.order_id", data = "#0")
    public Object confirmRefund(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        if (!clbmWaiMaiService.checkSign(data, clbmApp.getOrderRefundNotifyUrl())) {
            return MeituanHelper.ok();
        }
        if (StringUtils.isEmpty(data.get("order_id"))) {
            return MeituanHelper.ok();
        }
        logger.info("???????????????????????????");
        String json = JSON.toJSONString(data);
        OrderRefundRequest req = JSON.parseObject(json, OrderRefundRequest.class);
        OrderRefundStatus status = MeituanHelper.getRefundStatus(req.getResType());
        orderService.refundOrder(Plat.CLBM, String.valueOf(req.getOrderId()), status, null);
        if (status == OrderRefundStatus.AGREE) {
            orderService.cancelOrderByPlat(Plat.CLBM, String.valueOf(req.getOrderId()), 1002, req.getReason());
        }
        //orderService.printCancelInfo(Plat.CLBM, String.valueOf(req.getOrderId()));
        OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
        tr.setSuccess(true);
        OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        return MeituanHelper.ok();
    }

    @RequestMapping("/order/part-refund")
    @OrderNotificationTrace(plat = Plat.CLBM, type = OrderNotificationType.PART_REFUND, orderId = "#0.order_id", data = "#0")
    public Object confirmPartRefund(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        if (!clbmWaiMaiService.checkSign(data, clbmApp.getOrderPartRefundNotifyUrl())) {
            return MeituanHelper.ok();
        }
        if (StringUtils.isEmpty(data.get("order_id"))) {
            return MeituanHelper.ok();
        }
        logger.info("?????????????????????????????????");
        String json = JSON.toJSONString(data);
        OrderPartRefundRequest req = JSON.parseObject(json, OrderPartRefundRequest.class);
        /*OrderRefundStatus status = MeituanHelper.getRefundStatus(req.getResType());
        OrderService.OrderPartRefundParam param = new OrderService.OrderPartRefundParam();
        param.setPlat(Plat.MEITUAN);
        param.setPlatOrderId(String.valueOf(req.getOrderId()));
        param.setStatus(status);
        param.setRefundMoney(req.getMoney());
        param.setFoodList(JSON.parseObject(req.getFood(), new TypeReference<List<RefundFood>>() {
        }));
        orderService.partRefund(param);*/
        orderService.syncPlatOrder(Plat.CLBM, String.valueOf(req.getOrderId()), true);
        OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
        tr.setSuccess(true);
        OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        return MeituanHelper.ok();
    }

}
