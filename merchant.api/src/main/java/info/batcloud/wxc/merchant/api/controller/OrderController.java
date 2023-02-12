package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.domain.BusinessResponse;
import info.batcloud.wxc.core.dto.SupplierOrderDetailGroupDTO;
import info.batcloud.wxc.core.entity.Store;
import info.batcloud.wxc.core.enums.DeliveryStatus;
import info.batcloud.wxc.core.enums.OrderRefundStatus;
import info.batcloud.wxc.core.enums.OrderStatus;
import info.batcloud.wxc.core.enums.OrderStatusType;
import info.batcloud.wxc.core.helper.SecurityHelper;
import info.batcloud.wxc.core.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@PreAuthorize("hasRole('STORE_USER')")
public class OrderController {

    @Inject
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/search")
    public Object search(OrderService.SearchParam param) {
        param.setStoreUserId(SecurityHelper.loginStoreUserId());
        if (param.getDate() == null) {
            param.setDate(new Date());
        }
        if (param.getStatusType() == OrderStatusType.RESERVE) {
            param.setDate(null);
        }
        if (StringUtils.isNotEmpty(param.getPlatOrderId())) {
            param.setDate(null);
            param.setStatusType(null);
        }
        return BusinessResponse.ok(orderService.search(param));
    }

    @GetMapping("/detail-list/{id}")
    public Object searchDetailList(@PathVariable long id) {
        return BusinessResponse.ok(orderService.findDetailByOrderId(id));
    }

    @GetMapping("/group-supplier-detail-list/{id}")
    public Object groupSupplierDetailList(@PathVariable long id) {
        Map<String, Object> map = new HashMap<>(2);
        List<SupplierOrderDetailGroupDTO> list = orderService.groupSupplierDetailByOrderId(id);
        float supplierRemainMoney = 0, merchantRemainMoney = 0;
        for (SupplierOrderDetailGroupDTO dto : list) {
            supplierRemainMoney += dto.getRemainMoney();
            merchantRemainMoney += dto.getMerchantIncome();
        }
        map.put("supplierRemainMoney", supplierRemainMoney);
        map.put("merchantRemainMoney", merchantRemainMoney);
        map.put("groupList", list);
        return BusinessResponse.ok(map);
    }

    @PostMapping("/print/{id}")
    public Object print(@PathVariable long id) {
        return BusinessResponse.ok(orderService.printById(id));
    }

    @PostMapping("/sendDelivery/{id}")
    public Object sendDelivery(@PathVariable long id) {
        logger.info("接收到商家发起订单配送请求" + id);
        return BusinessResponse.ok(orderService.sendDeliveryByOrderId(id));
    }

    @PostMapping("/agreeRefund/{id}")
    public Object agreeRefund(@PathVariable long id) {
        logger.info("接收到商家同意订单退款" + id);
        return BusinessResponse.ok(orderService.agreeRefund(id, "商家同意退款", "商家同意退款", 0.0));
    }

    @PostMapping("/rejectRefund/{id}")
    public Object rejectRefund(@PathVariable long id) {
        logger.info("接收到商家拒绝订单退款" + id);
        return BusinessResponse.ok(orderService.rejectRefund(id, "商家拒绝退款"));
    }

    @PostMapping("/checkDeliverySelf/{id}")
    public Object checkDeliverySelf(@PathVariable long id) {
        orderService.setOrderSelf(id);
        return BusinessResponse.ok(orderService.updateDeliverySelf(id, DeliveryStatus.TAKEN));
    }

    @PostMapping("/cancelDelivery/{id}")
    public Object cancelDelivery(@PathVariable long id) {
        logger.info("接收到商家取消订单配送请求" + id);
        return BusinessResponse.ok(orderService.cancelDeliveryByOrderId(id));
    }

    @PostMapping("/receipt/{id}")
    public Object receipt(@PathVariable long id) {
        logger.info("接收到商家接单消息" + id);
        return BusinessResponse.ok(orderService.receiptOrder(id));
    }

    @GetMapping("/refundNum")
    public Object refundNum() {
        //logger.info("接收到商家接单消息" + SecurityHelper.loginStoreUserId());
        return BusinessResponse.ok(orderService.refundNum(SecurityHelper.loginStoreUserId()));
    }

    @PostMapping("/take/{id}")
    public Object take(@PathVariable long id) {
        return BusinessResponse.ok(orderService.updateDeliverySelf(id, DeliveryStatus.TAKEN));
    }

    @PostMapping("/arrive/{id}")
    public Object arrive(@PathVariable long id) {
        return BusinessResponse.ok(orderService.updateDeliverySelf(id, DeliveryStatus.ARRIVED));
    }


}
