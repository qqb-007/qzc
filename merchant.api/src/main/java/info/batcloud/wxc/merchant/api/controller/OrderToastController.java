package info.batcloud.wxc.merchant.api.controller;

import info.batcloud.wxc.core.dto.OrderDTO;
import info.batcloud.wxc.core.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lvling
 * Date: 2020/5/10
 * Time: 22:47
 */
@RequestMapping("/order/toast")
@RestController
public class OrderToastController {

    @Inject
    private OrderService orderService;

    @GetMapping("/list")
    public Object fetch() {
        List<OrderDTO> orderList = orderService.fetchTop20ValidLatestOrder();
        List<OrderToast> list = orderList.stream().map(o -> {
            OrderToast ot = new OrderToast();
            ot.setCreateTime(o.getCreateTime());
            ot.setRecipientName(o.getRecipientName());
            ot.setTotal(o.getTotal());
            return ot;
        }).collect(Collectors.toList());
        return list;
    }

    public static class OrderToast {
        private String recipientName;
        private Date createTime;
        private Float total;

        public String getRecipientName() {
            return recipientName;
        }

        public void setRecipientName(String recipientName) {
            this.recipientName = recipientName;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Float getTotal() {
            return total;
        }

        public void setTotal(Float total) {
            this.total = total;
        }
    }
}
