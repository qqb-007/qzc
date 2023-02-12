package info.batcloud.wxc.admin.controller;


import info.batcloud.wxc.admin.permission.ManagerPermissions;
import info.batcloud.wxc.admin.permission.annotation.Permission;
import info.batcloud.wxc.core.dto.OrderDTO;
import info.batcloud.wxc.core.entity.Order;
import info.batcloud.wxc.core.entity.OrderNotification;
import info.batcloud.wxc.core.enums.*;
import info.batcloud.wxc.core.repository.OrderRepository;
import info.batcloud.wxc.core.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Inject
    private OrderService orderService;

    @Inject
    private OrderRepository orderRepository;


    @GetMapping("/orderRemind")
    public Object orderRemind() {
        Map<String, Object> map = new HashMap<>();
        List<OrderDTO> waitDeliveryOrder = orderService.getWaitDeliveryOrder();
        if (waitDeliveryOrder.size() == 0 || waitDeliveryOrder == null) {
            map.put("check", false);
        } else {
            map.put("check", true);
            StringBuilder ms = new StringBuilder();
            for (OrderDTO order : waitDeliveryOrder) {
                ms.append(order.getStore().getName() + ":" + order.getPlat().getTitle() + order.getDaySeq() + "号单，");
            }
            map.put("msg", ms);
        }

        return map;
    }

    @GetMapping("/searchorderRemind")
    public Object getRemindList() {
        Map<String, Object> map = new HashMap<>();
        List<OrderDTO> list = orderService.getWaitDeliveryOrder();
        map.put("results", list);
        map.put("total", list.size());
        return map;
    }

    @GetMapping("/search")
    public Object search(OrderService.SearchParam param) {
        if (param.getBizStatus() == OrderBizStatus.WAIT_CHECK || param.getBizStatus() == OrderBizStatus.WAIT_SETTLE) {
            param.setOpened(true);
        }
        return orderService.search(param);
    }

    @PutMapping("/receipt/{id}")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object receipt(@PathVariable long id) {
        return orderService.receiptOrder(id);
    }

    @PutMapping("/cancel/{id}")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object cancel(@PathVariable long id, OrderService.OrderCancelParam param) {
        param.setId(id);
        return orderService.cancelOrder(param);
    }

    @PutMapping("/send-delivery/{id}")
    public Object sendDelivery(@PathVariable long id) {
        return orderService.sendDeliveryByOrderId(id);
    }

    @PutMapping("/deliver-self/{id}")
    public Object deliverSelf(@PathVariable long id) {
        return orderService.setOrderSelf(id);
    }

    @PostMapping("/call-delivery")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object callDelivery(long id, DeliveryType type) {
        return orderService.callCourier(id, type);
    }

    @PutMapping("/cancel-delivery/{id}")
    public Object cancelDelivery(@PathVariable long id) {
        return orderService.cancelDeliveryByOrderId(id);
    }

    @PutMapping("/print/{id}")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object print(@PathVariable long id) {
        return orderService.printById(id);
    }

    @PutMapping("/send/{id}")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object sendOrder(@PathVariable long id) {
        return orderService.sendWanteOrder(id);
    }


    @PutMapping("/finish/{id}")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object finishOrder(@PathVariable long id) {
        return orderService.finishWanteOrder(id);
    }


    @PostMapping("/refund/agree")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object refundAgree(long id, String remarks, Double money) {
        return orderService.agreeRefund(id, "其他原因", remarks, money);
    }

    @PostMapping("/switch/order")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object switchOrder(long id, Integer type) {

        return orderService.checkOrderDelivery(id, type);
    }

    @PostMapping("/addFee")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object addFee(long id, Double fee) {
        return orderService.addShunFengOrderFee(fee, id);
    }

    @PostMapping("/addCansun")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object addCansun(long id, Float fee, CansunType type) {
        if(fee == null || type == null){
            return null;
        }
        return orderService.addCansun(fee, id, type);
    }

    @PutMapping("/refund/reject/{id}")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object refundReject(@PathVariable long id, @RequestParam String reason) {
        return orderService.rejectRefund(id, reason);
    }

    @GetMapping("/batch-pull-phone-number/{storeId}")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object batchPullPhoneNumber(@PathVariable long storeId) {
        return orderService.pullPhoneNumberByStoreId(storeId);
    }

    @PostMapping("/sync-plat-order")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object syncPlatOrder(@RequestParam Plat plat, @RequestParam String platOrderId) {
        if (plat != Plat.JDDJ) {

            return orderService.syncPlatOrder(plat, platOrderId, true);


        } else {
            orderService.syncPlatOrder(plat, platOrderId, false);
            return orderService.syncPlatOrder(plat, platOrderId, true);
        }
    }

    @PostMapping("/sync-plat-order/no")
    @Permission(value = ManagerPermissions.ORDER_MANAGE)
    public Object syncPlatOrder(@RequestParam Plat plat, @RequestParam String platOrderId, @RequestParam OrderNotificationType type) {
        if (plat != Plat.JDDJ) {
            if (type == OrderNotificationType.UU) {
                //return orderService.sycnOrderPiesong(DeliveryType.UU_OPEN, platOrderId);
                return null;
            } else {
                return orderService.syncPlatOrder(plat, platOrderId, true);
            }


        } else {
            orderService.syncPlatOrder(plat, platOrderId, false);
            return orderService.syncPlatOrder(plat, platOrderId, true);
        }
    }

    @GetMapping("/export")
    public Object export(OrderService.ExportParam param) throws IOException {
        Map<String, Object> rs = new HashMap<>();
        rs.put("file", orderService.export(param));
        return rs;
    }

    @GetMapping("/download")
    public Object download(OrderService.ExportParam param) throws IOException {
        Map<String, Object> rs = new HashMap<>();
        rs.put("file", orderService.downLoad());
        return rs;
    }
}
