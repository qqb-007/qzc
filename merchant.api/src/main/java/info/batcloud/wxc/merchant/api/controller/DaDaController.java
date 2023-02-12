package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import info.batcloud.wxc.core.enums.DeliveryStatus;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/dd-peisong")
@RestController
public class DaDaController {
    private static final Logger logger = LoggerFactory.getLogger(DaDaController.class);

    @Inject
    private OrderService orderService;

    @PostMapping("/order/status")
    public Object orderStatus(@RequestBody String data, HttpServletRequest request) {
        OrderService.PeisongInfo peisongInfo = new OrderService.PeisongInfo();
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> mdata = (Map<String, Object>) jo.parse(data);
            logger.info("收到达达订单回调" + mdata.toString());
            if (mdata.get("order_id") == null) {
                ReturnInfo info = new ReturnInfo();
                info.setStatus("ok");
                return JSON.toJSONString(info);
            }
            peisongInfo.setType(DeliveryType.DD_OPEN);
            peisongInfo.setDdPeisongId(String.valueOf(mdata.get("order_id")));
            peisongInfo.setOrderId(String.valueOf(mdata.get("order_id")));
            int order_status = Integer.parseInt(String.valueOf(mdata.get("order_status")));
            DeliveryStatus status = DeliveryStatus.WAIT_DELIVERY;
            switch (order_status) {
                case 1:
                    status = DeliveryStatus.WAIT_SCHEDULE;
                    break;
                case 2:
                    status = DeliveryStatus.ACCEPT;
                    break;
                case 3:
                    status = DeliveryStatus.TAKEN;
                    break;
                case 100:
                    status = DeliveryStatus.ACCEPT;
                    break;
                case 4:
                    status = DeliveryStatus.ARRIVED;
                    break;
                case 5:
                    status = DeliveryStatus.CANCELED;
                    break;
            }
            peisongInfo.setStatus(status);
            if (status == DeliveryStatus.CANCELED) {
                peisongInfo.setCancelReasonId(Integer.parseInt(String.valueOf(mdata.get("cancel_from"))));
            }
            if (status != DeliveryStatus.CANCELED && status != DeliveryStatus.WAIT_SCHEDULE && status != DeliveryStatus.WAIT_DELIVERY) {
                peisongInfo.setCourierName(String.valueOf(mdata.get("dm_name")));
                peisongInfo.setCourierPhone(String.valueOf(mdata.get("dm_mobile")));
            }
        } catch (Exception e) {
            logger.error("同步配送信息出错" + e.getMessage());
        }
        Boolean p = true;
        DeliveryType type = null;
        logger.info(peisongInfo.toString());
        try {
            type = orderService.updateOrderPeisongInfo(peisongInfo);
        } catch (Exception e) {
            logger.error("同步配送信息出错 达达 订单保存出错 一分钟后重试" + e.getMessage());
            logger.error("同步配送信息出错 订单保存出错" + peisongInfo.getDdPeisongId(), e);
            p = false;
        }
        if (type != null) {
            logger.info("开始同步达达配送信息到平台");
            try {
                orderService.updatePiesonInfoToPlat(peisongInfo, type);
            } catch (Exception e) {
                logger.error("同步配送信息出错" + e.getMessage());
            }
        }
        ReturnInfo info = new ReturnInfo();
        if (p) {
            info.setStatus("ok");
            return JSON.toJSONString(info);
        } else {
            throw new BizException("同步出错请重试");
        }


    }


    public static class ReturnInfo {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
