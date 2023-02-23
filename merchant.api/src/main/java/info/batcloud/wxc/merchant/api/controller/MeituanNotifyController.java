package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderFoodDetailParam;
import info.batcloud.wxc.core.annotation.OrderNotificationTrace;
import info.batcloud.wxc.core.aop.OrderNotificationTraceAspect;
import info.batcloud.wxc.core.domain.meituan.OrderPartRefundRequest;
import info.batcloud.wxc.core.domain.meituan.OrderRefundRequest;
import info.batcloud.wxc.core.domain.meituan.RefundFood;
import info.batcloud.wxc.core.enums.*;
import info.batcloud.wxc.core.helper.MeituanHelper;
import info.batcloud.wxc.core.helper.URLDecodeHelper;
import info.batcloud.wxc.core.service.*;
import info.batcloud.wxc.core.settings.MeituanWaimaiAppSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/meituan/notify")
@RestController
public class MeituanNotifyController {

    @Inject
    private OrderService orderService;

    @Inject
    private MeituanWaimaiService meituanWaimaiService;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private StoreService storeService;

    private static final Logger logger = LoggerFactory.getLogger(MeituanNotifyController.class);

    @RequestMapping("/order")
    @OrderNotificationTrace(plat = Plat.MEITUAN, type = OrderNotificationType.ORDER, orderId = "#0.order_id", data = "#0")
    public Object order(@RequestParam Map<String, String> data, HttpServletRequest request) {
        try {
            URLDecodeHelper.decode(data);
            MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
            if (!meituanWaimaiService.checkSign(data, setting.getOrderPaidNotifyUrl()) && !request.getRequestURL().toString().startsWith("http://dev.")) {
                return MeituanHelper.ok();
            }
            if (StringUtils.isEmpty(data.get("order_id"))) {
                return MeituanHelper.ok();
            }
            logger.info("接收到美团订单通知，orderId：" + data.get("order_id"));
            OrderDetailParam orderDetailParam = APIFactory.getOrderAPI().orderGetOrderDetail(meituanWaimaiService.getSystemParam(),
                    Long.valueOf(data.get("order_id")), 0L);

            OrderService.OrderSaveResult rs = orderService.saveOrder(MeituanWaimaiService.toOrder(orderDetailParam));
            if (rs.isSuccess()) {
                logger.info("订单保存成功，orderId：" + data.get("order_id"));
            } else {
                logger.info("订单保存失败，orderId：" + data.get("order_id"));
            }
            OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
            tr.setErrMsg(rs.getMsg() + " " + rs.getErrMsg());
            tr.setSuccess(rs.isSuccess());
            OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        } catch (Exception e) {
            logger.error("美团订单同步出错", e);
        }
        return MeituanHelper.ok();
    }

    @RequestMapping("/order/peisong")
    public Object orderPeisong(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        logger.info("接收到美团订单配送状态改变通知");
        logger.info(data.toString());
        MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
        if (!meituanWaimaiService.checkSign(data, "https://api.wangxiaocai.cn/meituan/notify/order/peisong")) {
            logger.error("签名错误");
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
            info.setPlat(Plat.MEITUAN);
            if (status == 10 || status == 15) {
                String name = data.get("dispatcher_name");
                String phone = data.get("dispatcher_mobile");
                info.setStatus(DeliveryStatus.ACCEPT);
                info.setCourierName(name);
                info.setCourierPhone(phone);
                logger.info("骑手已接单" + name + phone);
            } else if (status == 20) {
                String name = data.get("dispatcher_name");
                String phone = data.get("dispatcher_mobile");
                logger.info("骑手已取货" + name + phone);
                info.setStatus(DeliveryStatus.TAKEN);
                info.setCourierName(name);
                info.setCourierPhone(phone);
            } else if (status == 40) {
                String name = data.get("dispatcher_name");
                String phone = data.get("dispatcher_mobile");
                logger.info("骑手已送达" + name + phone);
                info.setStatus(DeliveryStatus.ARRIVED);
                info.setCourierName(name);
                info.setCourierPhone(phone);
            } else if (status == 100) {
                String name = data.get("dispatcher_name");
                String phone = data.get("dispatcher_mobile");
                logger.info("骑手已取消" + name + phone);
                info.setStatus(DeliveryStatus.CANCELED);
                info.setCourierName(name);
                info.setCourierPhone(phone);
            } else if (status == 10 || status == 0 || status == 5) {
                logger.info("订单待调度");
                info.setStatus(DeliveryStatus.WAIT_SCHEDULE);
            }

//            if (info.getStatus() == DeliveryStatus.WAIT_SCHEDULE) {
//                //待调度不同步到系统中，减少并发
//                logger.info("美团跑腿待调度 不同步" + platOrderId);
//                return MeituanHelper.ok();
//            }

            DeliveryType type = null;
            try {
                type = orderService.updateOrderPeisongInfo(info);
            } catch (Exception e) {
                logger.error("同步美团跑腿配送状态出错  订单保存出错 一分钟后重新接收" + platOrderId, e);
                p = false;
            }
            if (type != null) {
                logger.info("开始同步配送信息到平台");
                orderService.updatePiesonInfoToPlat(info, type);
            }
        } catch (Exception e) {
            logger.error("同步美团配送状态出错" + e.getMessage());
            p = false;
        }

        if (p) {
            return MeituanHelper.ok();
        } else {
            return "fail";
        }
    }


    @RequestMapping("/order/cancel")
    @OrderNotificationTrace(plat = Plat.MEITUAN, type = OrderNotificationType.CANCEL, orderId = "#0.order_id", data = "#0")
    public Object orderCancel(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
        if (!meituanWaimaiService.checkSign(data, setting.getOrderCancelNotifyUrl()) && !request.getRequestURL().toString().startsWith("http://dev.")) {
            return MeituanHelper.ok();
        }
        Long orderId = Long.valueOf(data.get("order_id"));
        if (orderId == null) {
            return MeituanHelper.ok();
        }
        logger.info("接收到订单取消通知" + orderId);
        orderService.cancelOrderByPlat(Plat.MEITUAN, orderId.toString(), Integer.valueOf(data.get("reason_code")), data.get("reason"));
        orderService.printCancelInfo(Plat.MEITUAN, orderId.toString());
        orderService.syncCancelOrderStock(orderId.toString(), Plat.MEITUAN);
        OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
        tr.setSuccess(true);
        OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        return MeituanHelper.ok();
    }

    @RequestMapping("/order/finish")
    @OrderNotificationTrace(plat = Plat.MEITUAN, type = OrderNotificationType.FINISH, orderId = "#0.order_id", data = "#0")
    public Object orderFinish(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
        if (!meituanWaimaiService.checkSign(data, setting.getOrderFinishNotifyUrl()) && !request.getRequestURL().toString().startsWith("http://dev.")) {
            return MeituanHelper.ok();
        }
        if (StringUtils.isEmpty(data.get("order_id"))) {
            return MeituanHelper.ok();
        }
        logger.info("接收到订单完成通知");
        try {
            OrderService.OrderSaveResult rs = orderService.syncPlatOrder(Plat.MEITUAN, data.get("order_id"), true);
            OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
            tr.setErrMsg(rs.getMsg() + " " + rs.getErrMsg());
            tr.setSuccess(rs.isSuccess());
            OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        } catch (Exception e) {
            logger.error("美团订单同步出错", e);
        }
        return MeituanHelper.ok();
    }

    @RequestMapping("/order/remind")
    public Object remindOrder() {

        return MeituanHelper.ok();
    }

    @RequestMapping("/order/confirm")
    @OrderNotificationTrace(plat = Plat.MEITUAN, type = OrderNotificationType.CONFIRM, orderId = "#0.order_id", data = "#0")
    public Object confirmOrder(@RequestParam Map<String, String> data, HttpServletRequest request) {
        try {
            URLDecodeHelper.decode(data);
            MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
            if (!meituanWaimaiService.checkSign(data, setting.getOrderConfirmNotifyUrl()) && !request.getRequestURL().toString().startsWith("http://dev.")) {
                return MeituanHelper.ok();
            }
            if (StringUtils.isEmpty(data.get("order_id"))) {
                return MeituanHelper.ok();
            }
            String platOrderId = data.get("order_id");
            logger.info("接收到订单确认通知, platOrderId:" + platOrderId);
            orderService.confirmOrderByPlat(Plat.MEITUAN, platOrderId);
            try {
                //orderService.sycnStock(platOrderId);
            } catch (Exception e) {
                logger.error("同步美团商品库存出错", e);
            }
            OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
            tr.setErrMsg("");
            tr.setSuccess(true);
            OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        } catch (Exception e) {
            logger.error("美团订单同步出错", e);
        }
        return MeituanHelper.ok();
    }

    /**
     * 隐私降级
     */
    @RequestMapping("/privacy-degrade")
    public Object privacyDegrade() {

        return MeituanHelper.ok();
    }

    /**
     * 同步店铺营业状态
     */
    @RequestMapping("/shop/status")
    public Object shopStatus(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        logger.info("接收到美团店铺营业状态改变通知");
        MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
        if (!meituanWaimaiService.checkSign(data, "https://api.wangxiaocai.cn/meituan/notify/shop/status")) {
            logger.error("签名错误");
            return MeituanHelper.ok();
        }
        if (StringUtils.isEmpty(data.get("app_poi_code"))) {
            return MeituanHelper.ok();
        }
        String code = data.get("app_poi_code");
        Integer status = Integer.valueOf(data.get("poi_status"));
        try {
            if (status == 121) {
                storeService.updateOpenStatus(code, Plat.MEITUAN, true);
            } else if (status == 120) {
                storeService.updateOpenStatus(code, Plat.MEITUAN, false);
            }
        } catch (Exception e) {
            logger.error("同步美团店铺营业状态出错" + e.getMessage());
        }

        return MeituanHelper.ok();
    }

    @RequestMapping("/order/refund")
    @OrderNotificationTrace(plat = Plat.MEITUAN, type = OrderNotificationType.REFUND, orderId = "#0.order_id", data = "#0")
    public Object confirmRefund(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
        if (!meituanWaimaiService.checkSign(data, setting.getOrderRefundNotifyUrl()) && !request.getRequestURL().toString().startsWith("http://dev.")) {
            return MeituanHelper.ok();
        }
        if (StringUtils.isEmpty(data.get("order_id"))) {
            return MeituanHelper.ok();
        }
        String json = JSON.toJSONString(data);
        OrderRefundRequest req = JSON.parseObject(json, OrderRefundRequest.class);
        logger.info("接收到订单退款通知" + String.valueOf(req.getOrderId()));
        OrderRefundStatus status = MeituanHelper.getRefundStatus(req.getResType());
        orderService.refundOrder(Plat.MEITUAN, String.valueOf(req.getOrderId()), status, null);
        if (status == OrderRefundStatus.AGREE) {
            orderService.cancelOrderByPlat(Plat.MEITUAN, String.valueOf(req.getOrderId()), 1002, req.getReason());
        }
        //orderService.printCancelInfo(Plat.MEITUAN, String.valueOf(req.getOrderId()));
        OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
        tr.setSuccess(true);
        OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        return MeituanHelper.ok();
    }

    @RequestMapping("/order/part-refund")
    @OrderNotificationTrace(plat = Plat.MEITUAN, type = OrderNotificationType.PART_REFUND, orderId = "#0.order_id", data = "#0")
    public Object confirmPartRefund(@RequestParam Map<String, String> data, HttpServletRequest request) throws UnsupportedEncodingException {
        URLDecodeHelper.decode(data);
        MeituanWaimaiAppSetting setting = systemSettingService.findActiveSetting(MeituanWaimaiAppSetting.class);
        if (!meituanWaimaiService.checkSign(data, setting.getOrderPartRefundNotifyUrl()) && !request.getRequestURL().toString().startsWith("http://dev.")) {
            return MeituanHelper.ok();
        }
        if (StringUtils.isEmpty(data.get("order_id"))) {
            return MeituanHelper.ok();
        }
        logger.info("接收到订单部分退款通知");
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
        orderService.syncPlatOrder(Plat.MEITUAN, String.valueOf(req.getOrderId()), true);
        OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
        tr.setSuccess(true);
        OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        return MeituanHelper.ok();
    }

    @PostMapping("/foodChange")
    public Object foodChange(@RequestParam Map<String, String> data, String retail_data, HttpServletRequest request) {
        try {
            URLDecodeHelper.decode(data);
            String decode = URLDecoder.decode(retail_data, "utf8");
            String s = URLDecoder.decode(decode, "utf8");
            String json = JSON.toJSONString(s);
            logger.info("接收到非接口修改商品信息 " + json);
        } catch (Exception e) {
            logger.error("接收美团店铺更改商品出错", e);
        }
        return MeituanHelper.ok();
    }


}
