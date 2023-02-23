package info.batcloud.wxc.merchant.api.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import info.batcloud.wxc.core.annotation.OrderNotificationTrace;
import info.batcloud.wxc.core.config.EleApp;
import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.OrderRefundStatus;
import info.batcloud.wxc.core.enums.OrderStatus;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.service.*;
import me.ele.sdk.down.PushForm;
import me.ele.sdk.down.SignUtil;
import me.ele.sdk.down.response.*;
import me.ele.sdk.up.EleClient;
import me.ele.sdk.up.request.OrderGetRequest;
import me.ele.sdk.up.response.OrderGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/ele/notify")
@RestController
public class EleNotifyController {

    @Inject
    private EleApp eleApp;

    @Inject
    private OrderService orderService;

    @Inject
    private EleClient eleClient;

    @Inject
    private OrderNotificationService orderNotificationService;

    @Inject
    private StoreUserService storeUserService;

    @Inject
    private StoreService storeService;

    private static final Logger logger = LoggerFactory.getLogger(EleNotifyController.class);

    @PostMapping
    public Object execute(PushForm form, HttpServletRequest request) {
        /**
         * 订单接口状态变更的sign有问题，暂时不做签名校验
         * */
        if (!"order.status.push".equals(form.getCmd())) {
            boolean flag = SignUtil.checkSign(form, eleApp.getSecret());
            if (!flag) {
                throw new BizException("check sign error");
            }
        }
        if ("order.create".equals(form.getCmd())) {
            JSONObject orderInfo = JSON.parseObject(form.getBody());
            String orderId = orderInfo.getString("order_id");
            try {
                OrderService.OrderSaveResult rs = new OrderService.OrderSaveResult();
                OrderGetRequest req = new OrderGetRequest();
                req.setOrderId(orderId);
                OrderGetResponse orderGetRes = eleClient.request(req);
                orderService.saveOrder(EleWaimaiService.toOrder(orderGetRes.getData()));
                logger.info("ELE订单保存成功，orderId：" + orderId);
                orderNotificationService.saveOrderNotification(Plat.ELE, OrderNotificationType.ORDER, orderId, true, null, JSON.toJSONString(form));
                OrderCreateResponse.Data data = new OrderCreateResponse.Data();
                data.setSource_order_id(rs.getOrderId() + "");
                OrderCreateResponse res = new OrderCreateResponse();
                res.setData(data);
                return eleClient.wrapDownResponse(res);
            } catch (Exception e) {
                orderNotificationService.saveOrderNotification(Plat.ELE, OrderNotificationType.ORDER, orderId, false, e.getMessage(), JSON.toJSONString(form));
            }
        } else if ("order.status.push".equals(form.getCmd())) {
            try {
                JSONObject info = JSON.parseObject(form.getBody());
                logger.info("接收到饿百订单状态信息" + info.toJSONString());
                String orderId = info.getString("order_id");
                int status = info.getInteger("status");
                switch (status) {
                    case 5:  // 确认订单
                        orderService.syncPlatOrder(Plat.ELE, orderId, true);
                        orderService.confirmOrderByPlat(Plat.ELE, orderId);
                        break;
                    case 7: // 开始接单
                        orderService.setPlatOrderStatus(Plat.ELE, orderId, OrderStatus.SHIPPING);
                        break;
                    case 8: // 配送
                        orderService.setPlatOrderStatus(Plat.ELE, orderId, OrderStatus.SHIPPING);
                        break;
                    case 9: // 订单完成
                        orderService.syncPlatOrder(Plat.ELE, orderId, true);
                        break;
                    case 10: // 订单取消
                        orderService.cancelOrderByPlat(Plat.ELE, orderId, 1002, info.getString("reason"));
                        orderService.printCancelInfo(Plat.ELE, orderId);
                        orderService.syncCancelOrderStock(orderId, Plat.ELE);
                        break;
                }
            } catch (Exception e) {
                logger.error("饿百订单状态出错 " + e.getMessage());
            }
            OrderStatusPushResponse res = new OrderStatusPushResponse();
            return eleClient.wrapDownResponse(res);
        } else if ("order.partrefund.push".equals(form.getCmd())) {
            JSONObject orderInfo = JSON.parseObject(form.getBody());
            String orderId = orderInfo.getString("order_id");
            // 部分退款
            OrderService.OrderSaveResult rs = orderService.syncPlatOrder(Plat.ELE, orderId, true);
            OrderPartRefundResponse res = new OrderPartRefundResponse();
            return eleClient.wrapDownResponse(res);
        } else if ("order.user.cancel".equals(form.getCmd())) {
            try {
                JSONObject orderInfo = JSON.parseObject(form.getBody());
                logger.info("接收到饿百用户取消订单消息 " + orderInfo);
                String orderId = orderInfo.getString("order_id");
                String type = orderInfo.getString("type");
                String cancelType = orderInfo.getString("cancel_type");
                String cancelReason = orderInfo.getString("cancel_reason");
                String refundId = orderInfo.getString("refund_order_id");
                if (("40".equals(type) || "60".equals(type)) && "2".equals(cancelType)) {
                    orderService.cancelOrderByPlat(Plat.ELE, orderId, 1002, cancelReason);
                } else if ("10".equals(type)) {
                    orderService.refundOrder(Plat.ELE, orderId, OrderRefundStatus.PENDING, refundId);
                } else {
                    orderService.syncPlatOrder(Plat.ELE, orderId, true);
                }
                //orderService.printCancelInfo(Plat.ELE,orderId);
            } catch (Exception e) {
                logger.error("饿百订单用户取消订单出错" + e.getMessage());
            }
            OrderUserCancelResponse res = new OrderUserCancelResponse();
            return eleClient.wrapDownResponse(res);
        } else if ("order.deliveryStatus.push".equals(form.getCmd())) {
            OrderDeliveryStatusResponse res = new OrderDeliveryStatusResponse();
            return eleClient.wrapDownResponse(res);
        } else if ("order.remind.push".equals(form.getCmd())) {
            OrderRemindResponse res = new OrderRemindResponse();
            return eleClient.wrapDownResponse(res);
        } else if ("shop.msg.push".equals(form.getCmd())) {
            logger.info("接收到饿了么店铺状态变更通知");
            JSONObject orderInfo = JSON.parseObject(form.getBody());
            logger.info("变更内容" + orderInfo);
            String msgType = orderInfo.getString("msg_type");
            if ("shop_open".equals(msgType) || "shop_close".equals(msgType) || "shop_pause".equals(msgType)) {
                try {
                    String status = orderInfo.getString("business_ele");
                    String shopId = orderInfo.getString("shop_id");
                    if ("1".equals(status)) {
                        storeService.updateOpenStatus(shopId, Plat.ELE, true);
                    } else if ("3".equals(status) || "9".equals(status)) {
                        storeService.updateOpenStatus(shopId, Plat.ELE, false);
                    }
                } catch (Exception e) {
                    logger.error("同步饿了么店铺营业状态出错" + e.getMessage());
                }
            }
            ShopStatusResponse res = new ShopStatusResponse();
            return eleClient.wrapDownResponse(res);
        }
        return true;
    }

}
