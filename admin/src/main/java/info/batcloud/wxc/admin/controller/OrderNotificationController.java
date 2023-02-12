package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.core.service.OrderNotificationService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RequestMapping("/api/order-notification")
@RestController
public class OrderNotificationController {

    @Inject
    private OrderNotificationService orderNotificationService;

    @GetMapping("/search")
    public Object search(OrderNotificationService.SearchParam param) {
        return orderNotificationService.search(param);
    }

    @PutMapping("/success/{id}")
    public Object success(@PathVariable long id) {
        orderNotificationService.setSuccess(id);
        return true;
    }
}
