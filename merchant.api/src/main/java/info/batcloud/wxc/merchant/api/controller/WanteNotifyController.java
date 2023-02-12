package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.service.OrderNotificationService;
import info.batcloud.wxc.core.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


@RequestMapping("/wante/notify")
@RestController
public class WanteNotifyController {
    @Inject
    private OrderService orderService;
    @Inject
    private OrderNotificationService orderNotificationService;

    private static final Logger logger = LoggerFactory.getLogger(WanteNotifyController.class);

    @RequestMapping("order/create")
    public Object createOrder(@RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            logger.error("订单号异常" + orderId);
            return "订单号异常";
        }
        logger.info("接收到客户端订单通知，orderId：" + orderId);
        OrderService.OrderSaveResult rs = orderService.syncPlatOrder(Plat.WANTE, orderId, true);
        if (rs.isSuccess()) {
            logger.info("订单保存成功，orderId：" + orderId);
        } else {
            logger.info("订单保存失败，orderId：" + orderId);
            orderNotificationService.saveOrderNotification(Plat.WANTE, OrderNotificationType.ORDER, orderId, false, rs.getErrMsg(), rs.getMsg());
        }

        return "ok";
    }


    @RequestMapping("/order/finish")
    public Object finishOrder(@RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            logger.error("订单号异常" + orderId);
            return "订单号异常";
        }
        logger.info("接收到订单完成通知");
        OrderService.OrderSaveResult rs = orderService.syncPlatOrder(Plat.WANTE, orderId, true);
        return "ok";
    }

    @RequestMapping("/order/cancel")
    public Object orderCancel(@RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            logger.error("订单号异常" + orderId);
            return "订单号异常";
        }
        logger.info("接收到订单取消通知"+orderId);
        orderService.cancelOrderByPlat(Plat.WANTE, orderId, 1002, "其他原因");
        return "ok";
    }

    @RequestMapping("/order/refund")
    public Object orderRefund(@RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            logger.error("订单号异常" + orderId);
            return "订单号异常";
        }
        logger.info("接收到订单退款通知");
        OrderService.OrderSaveResult rs = orderService.syncPlatOrder(Plat.WANTE, orderId, true);
        return "ok";
    }

    @RequestMapping("/order/part-refund")
    public Object orderPartRefund(@RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            logger.error("订单号异常" + orderId);
            return "订单号异常";
        }
        logger.info("接收到订单部分退款通知");
        OrderService.OrderSaveResult rs = orderService.syncPlatOrder(Plat.WANTE, orderId, true);
        return "ok";
    }
}
