package info.batcloud.wxc.background.task.scheduler;

import info.batcloud.wxc.core.entity.Order;
import info.batcloud.wxc.core.entity.OrderNotification;
import info.batcloud.wxc.core.entity.Store;
import info.batcloud.wxc.core.enums.*;
import info.batcloud.wxc.core.repository.OrderNotificationRepository;
import info.batcloud.wxc.core.repository.OrderRepository;
import info.batcloud.wxc.core.repository.StoreRepository;
import info.batcloud.wxc.core.repository.StoreUserRepository;
import info.batcloud.wxc.core.service.OrderNotificationService;
import info.batcloud.wxc.core.service.OrderService;
import info.batcloud.wxc.core.service.SingleCallService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;

@Component
public class OrderScheduler {

    @Inject
    private OrderService orderService;

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private SingleCallService singleCallService;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private OrderNotificationRepository notificationRepository;

    @Inject
    private OrderNotificationService notificationService;

    private static final Logger logger = LoggerFactory.getLogger(OrderScheduler.class);


    @Scheduled(cron = "${cron.order.sync.check}")
    public void checkOrderSync() {
        /**
         * 找到当天2小时之前还未处于最终状态的订单，进行重新同步
         * */
        Date now = new Date();
        Date startTime = DateUtils.truncate(now, Calendar.DATE);
        Date endTime = DateUtils.addHours(now, -2);
        List<Order> orderList = orderRepository.findByCreateTimeBetweenAndStatusNotIn(startTime, endTime, Arrays.asList(OrderStatus.CANCELED, OrderStatus.FINISHED));
        for (Order order : orderList) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                orderService.checkOrder(order.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Scheduled(cron = "${cron.order.book.delivery}")
    public void sendBookDelivery() {
        /**
         * 找到当天预定单时间小于1小时的订单进行发起配送
         * */
        logger.info("执行预定单配送任务");
        long expectedDeliveryTime = (System.currentTimeMillis() + 3600 * 1000) / 1000;
        Date nowDate = new Date();
        Date startUTime = DateUtils.addMinutes(nowDate, -5);
        List<Order> orderList = orderRepository.findByPlatNotAndDeliveryStatusAndExpectedDeliveryTimeBetweenAndUpdateTimeLessThanAndStatusIn(Plat.JDDJ,
                DeliveryStatus.WAIT_DELIVERY, System.currentTimeMillis() / 1000, expectedDeliveryTime, startUTime,
                OrderStatus.MERCHANT_CONFIRMED);
        logger.info("找到需要发起预定单配送的订单" + orderList.size() + "个");
        for (Order order : orderList) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("发起预定单配送：" + order.getId());
            if (order.getPlat() != Plat.JDDJ) {
                try {
                    Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
                    if (store.getDeliverySelf()) {
                        logger.info("店铺需要自己点配送，不自动发起配送");
                    } else {
                        orderService.sendDeliveryByOrderId(order.getId());
                    }

                } catch (Exception e) {
                    logger.error(e.getMessage());
                }


            }
            //this.orderService.printById(order.getId());
        }

        /**
         * 拣货完成任务
         * */
        logger.info("执行订单拣货完成");
        List<Plat> plats = new ArrayList<>();
        plats.add(Plat.MEITUAN);
        plats.add(Plat.CLBM);
        Date now = new Date();
        Date startTime = DateUtils.addMinutes(now, -20);
        Date endTime = DateUtils.addMinutes(now, -10);
        List<Order> porderList = orderRepository.findByStatusAndUpdateTimeBetweenAndPlatInAndPreparationIsNull(OrderStatus.MERCHANT_CONFIRMED, startTime, endTime, plats);
        logger.info("找到需要拣货完成的订单" + porderList.size() + "个");
        for (Order order : porderList) {
            try {
                Thread.sleep(200L);
                orderService.preparationMealComplete(order.getId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    @Scheduled(cron = "${cron.order.book.deliveryjd}")
    public void sendJddjDelivery() {
        /**
         * 找到当天预定单时间小于2小时的订单进行发起配送
         * */
        logger.info("执行京东订单配送任务");
        long expectedDeliveryTime = (System.currentTimeMillis() + 3600 * 1000) / 1000;
        List<Order> orderList = orderRepository.findByPlatAndDeliveryStatusAndExpectedDeliveryTimeBetweenAndStatusIn(Plat.JDDJ,
                DeliveryStatus.WAIT_DELIVERY, System.currentTimeMillis() / 1000, expectedDeliveryTime,
                OrderStatus.MERCHANT_CONFIRMED);
        logger.info("找到需要发起预定单配送的订单" + orderList.size() + "个");
        for (Order order : orderList) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("发起预定单配送：" + order.getId());
            try {
                Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
                if (store.getDeliverySelf()) {
                    logger.info("店铺需要自己点配送，不自动发起配送");
                } else {
                    logger.info("店铺需要自动发起配送");
                    orderService.sendDeliveryByOrderId(order.getId());
                }
                //orderService.sendDeliveryByOrderId(order.getId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            //this.orderService.printById(order.getId());
        }

        //取消订单配送

        List<DeliveryStatus> statuses = new ArrayList<>();
        statuses.add(DeliveryStatus.WAIT_SCHEDULE);
        statuses.add(DeliveryStatus.ACCEPT);
        statuses.add(DeliveryStatus.TAKEN);
        //statuses.add(DeliveryStatus.ARRIVED);
        List<DeliveryType> types = new ArrayList<>();
        types.add(DeliveryType.MEITUAN_OPEN);
        types.add(DeliveryType.SHUFENG_OPEN);
        types.add(DeliveryType.UU_OPEN);
        types.add(DeliveryType.SS_OPEN);
        types.add(DeliveryType.DD_OPEN);
        types.add(DeliveryType.UNDETERMINED);
        List<Order> orders = orderRepository.findByStatusAndDeliveryStatusInAndDeliveryTypeIn(OrderStatus.CANCELED, statuses, types);
        logger.info("执行取消配送任务，共找到" + orders.size() + "个");
        for (Order order : orders) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            try {
                logger.info(order.getPlatOrderId() + "执行取消配送");
                orderService.cancelDeliveryByOrderId(order.getId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }


        //同步闪送和uu订单状态
//        List<DeliveryStatus> statusList = new ArrayList<>();
//        statusList.add(DeliveryStatus.WAIT_SCHEDULE);
//        List<DeliveryType> typeList = new ArrayList<>();
//        typeList.add(DeliveryType.UU_OPEN);
//        typeList.add(DeliveryType.SS_OPEN);
//        List<Order> list = orderRepository.findByStatusAndDeliveryStatusInAndDeliveryTypeIn(OrderStatus.MERCHANT_CONFIRMED, statusList, typeList);
//        logger.info("执行同步UU和闪送订单状态，共找到 " + list.size() + "个");
//        for (Order order : list) {
//            try {
//                logger.info(order.getPlatOrderId() + "执行取消配送");
//                orderService.updateDeliveryState(order.getId());
//                Thread.sleep(500L);
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
//
//        }

        List<OrderNotification> notificationList = notificationRepository.findByTypeAndSuccess(OrderNotificationType.UU, false);
        logger.info("开始同步UU通知异常配送 共" + notificationList.size());
        for (OrderNotification orderNotification : notificationList) {
            try {
                orderService.sycnOrderPiesong(orderNotification.getId());
                notificationService.setSuccess(orderNotification.getId());
            } catch (Exception e) {
                logger.error("同步UU异常配送失败" + orderNotification.getId(), e);
            }
        }


        List<DeliveryStatus> status = new ArrayList<>();
        status.add(DeliveryStatus.WAIT_SCHEDULE);
        status.add(DeliveryStatus.WAIT_DELIVERY);
        List<Order> orderList1 = orderRepository.findByStatusAndDeliveryStatusInAndDeliveryType(OrderStatus.MERCHANT_CONFIRMED, status, DeliveryType.UNDETERMINED);
        logger.info("执行聚合配送发起配送任务");
        for (Order order : orderList1) {
            try {
                logger.info(order.getPlatOrderId() + "执行聚合配送");
                orderService.sendDeliveryByOrderId(order.getId());
                Thread.sleep(500L);
            } catch (Exception e) {
                logger.error("聚合配送发起失败" + order.getId(), e);
            }
        }

    }

    //@Scheduled(cron = "${cron.order.book.preparation}")
    public void preparationOrder() {
        logger.info("海葵订单即时单发起配送");
        Date now = new Date();
        Date startTime = DateUtils.addMinutes(now, -4);
        Date endTime = DateUtils.addMinutes(now, -2);
        List<Order> orderList = orderRepository.findByStatusAndUpdateTimeBetweenAndDeliveryTypeAndDeliveryStatus(OrderStatus.MERCHANT_CONFIRMED, startTime, endTime, DeliveryType.MEITUAN_OPEN, DeliveryStatus.WAIT_DELIVERY);
        for (Order order : orderList) {
            try {
                logger.info("即时单发起配送" + order.getPlatOrderId());
                orderService.sendDeliveryByOrderId(order.getId());
                Thread.sleep(500L);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Scheduled(cron = "${cron.order.book.phone}")
    public void sendOrderCall() {
        /**
         * 找到生成订单后三分钟到五分钟之间没有接单的订单进行电话提醒（排除预订单）
         * */
        logger.info("执行订单提醒任务");
        Date now = new Date();
        Date startTime = DateUtils.addMinutes(now, -8);
        Date endTime = DateUtils.addMinutes(now, -3);
        List<Order> orderList = orderRepository.findByStatusAndCreateTimeBetween(OrderStatus.WAIT_MERCHANT_CONFIRM, startTime, endTime);
        logger.info("找到需要电话提醒的订单" + orderList.size() + "个");
        for (Order order : orderList) {
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
            String phone = store.getStoreUser().getPhone();
            logger.info("电话提醒订单：" + order.getId() + " 电话：" + phone);
            try {
                singleCallService.sendCall(order.getPlat(), phone, order.getId());
                Thread.sleep(100L);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    @Scheduled(cron = "${cron.order.book.call}")
    public void sendCall() {
        /**
         * 找到前一天晚上十点到第二天早上六点之间生成的 状态为接单中的订单 六点开始七点结束 七分钟一次
         * */
        logger.info("执行预订单提醒任务");
        long current = System.currentTimeMillis();
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//当天零点时间戳
        Date startTime = new Date(zero - 2 * 60 * 60 * 1000);
        Date endTime = new Date(zero + 6 * 60 * 60 * 1000);
        List<Order> orderList = orderRepository.findByStatusAndCreateTimeBetween(OrderStatus.WAIT_MERCHANT_CONFIRM, startTime, endTime);
        logger.info("找到需要电话提醒的预订单" + orderList.size() + "个");
        for (Order order : orderList) {
            Store store = storeRepository.findByCodeAndPlat(order.getAppPoiCode(), order.getPlat());
            String phone = store.getStoreUser().getPhone();
            logger.info("电话提醒预订单：" + order.getId() + " 电话：" + phone);
            try {
                singleCallService.sendCall(order.getPlat(), phone, order.getId());
                Thread.sleep(100L);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }


}
