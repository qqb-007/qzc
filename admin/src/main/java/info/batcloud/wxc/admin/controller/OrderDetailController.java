package info.batcloud.wxc.admin.controller;

import info.batcloud.wxc.core.service.OrderDetailService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {

    @Inject
    private OrderDetailService orderDetailService;

    @GetMapping("/search")
    public Object search(OrderDetailService.SearchParam param) {
        return orderDetailService.search(param);
    }

    @PutMapping("/bind-sku/{id}")
    public Object bindSku(@PathVariable long id, OrderDetailService.FoodBindParam param) {
        orderDetailService.bindFood(id, param);
        return true;
    }
}
