package info.batcloud.wxc.merchant.api.comsumers;

import com.alibaba.fastjson.JSON;
import com.sankuai.meituan.banma.PeisongClient;
import com.sankuai.meituan.banma.constants.CancelOrderReasonId;
import com.sankuai.meituan.banma.request.CancelOrderRequest;
import com.sankuai.meituan.banma.response.CancelOrderResponse;
import info.batcloud.wxc.core.constants.KafkaConstants;
import info.batcloud.wxc.core.entity.Order;
import info.batcloud.wxc.core.enums.DeliveryStatus;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.service.OrderService;
import info.batcloud.wxc.core.service.StoreUserService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;

@Component
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    @Inject
    private PeisongClient peisongClient;

    @Inject
    private OrderService orderService;

    @Inject
    private StoreUserService storeUserService;

    @KafkaListener(topics = KafkaConstants.TOPIC_ORDER_CANCEL)
    public void orderCancelConsumer(ConsumerRecord<String, String> cr) {
        logger.info("接收到的取消订单信息" + cr.value());
        Order order = JSON.parseObject(cr.value(), Order.class);
        logger.info("需要取消配送的订单id " + order.getId());
        try {
            orderService.cancelDeliveryByOrderId(order.getId());
        } catch (Exception e) {
            logger.error("取消配送失败，" + e.getMessage());
        }
    }

    @KafkaListener(topics = KafkaConstants.TOPIC_ORDER_MERCHANT_CONFIRMED)
    public void orderConfirmConsumer(ConsumerRecord<String, String> cr) {
        try {
            //logger.info("接收到订单确认的消息," + cr.value());
            Order order = JSON.parseObject(cr.value(), Order.class);
            logger.info("接收到订单确认的消息," + order.getId());
            boolean flag = this.orderService.printById(order.getId());
            if (flag) {
                logger.info("打印订单消息发送成功");
            } else {
                logger.info("打印订单消息发送失败");
            }
            /**
             * 如果是即时单或者是当天的预定单，则直接发起配送
             * */
            if ((order.getExpectedDeliveryTime() == null
                    || order.getExpectedDeliveryTime() == 0)
                    || DateUtils.isSameDay(new Date(), new Date(order.getExpectedDeliveryTime() * 1000))) {

                if (order.getStore().getDeliverySelf()) {
                    logger.info("店铺需要自己点配送，不自动发起配送");
                } else {
                    logger.info("自动发起配送");
                    this.orderService.sendDeliveryByOrderId(order.getId());
                }

                logger.info("店铺信息" + order.getStore().toString());


                //海葵订单先不发起配送，三分钟之后由定时任务发起配送
//                if (order.getDeliveryType() != DeliveryType.MEITUAN_OPEN) {
//                    this.orderService.sendDeliveryByOrderId(order.getId());
//                } else {
//                    logger.info("海葵订单，三分钟之后再发起配送" + order.getPlatOrderId());
//                }

            }
        } catch (Exception e) {
            logger.error("TOPIC_ORDER_MERCHANT_CONFIRMED error", e);
            throw e;
        }
    }

    @KafkaListener(topics = KafkaConstants.TOPIC_ORDER_NEW)
    public void orderNewConsumer(ConsumerRecord<String, String> cr) {
        Order order = JSON.parseObject(cr.value(), Order.class);
        logger.info("接收到订单创建的消息," + order.getId());
        if (storeUserService.isAutoReceiptOrderById(order.getStore().getStoreUser().getId())) {
            logger.info("进行自动接单操作，" + order.getId());
            boolean flag = this.orderService.receiptOrder(order.getId());
            if (flag) {
                logger.info("自动接单成功，" + order.getId());
            } else {
                logger.error("自动接单失败，" + order.getId());
            }
        } else {
            logger.info("未开启自动接单，" + order.getId());
        }
    }

}
