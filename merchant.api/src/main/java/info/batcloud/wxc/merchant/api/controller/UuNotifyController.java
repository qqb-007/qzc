package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import info.batcloud.wxc.core.enums.DeliveryStatus;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.service.OrderNotificationService;
import info.batcloud.wxc.core.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@RequestMapping("/uu-peisong")
@RestController
public class UuNotifyController {
    private static final Logger logger = LoggerFactory.getLogger(UuNotifyController.class);

    @Inject
    private OrderService orderService;

    @Inject
    private OrderNotificationService orderNotificationService;

    @PostMapping("/order/status")
    public Object orderStatus(@RequestBody String data, HttpServletRequest request) {
        logger.info("接收到UU跑腿订单状态通知");
        try {
            data = URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info("uu跑腿状态通知data: " + data);
        String[] split = data.split("=");
        String s = split[1];
        JSONObject jo = new JSONObject();
        Map<String, Object> mdata = (Map<String, Object>) jo.parse(s);
        OrderService.PeisongInfo peisongInfo = new OrderService.PeisongInfo();
        peisongInfo.setUuPeisongId(String.valueOf(mdata.get("order_code")));
        peisongInfo.setCourierName(String.valueOf(mdata.get("driver_name")));
        peisongInfo.setCourierPhone(String.valueOf(mdata.get("driver_mobile")));
        peisongInfo.setOrderId(String.valueOf(mdata.get("origin_id")));
        int order_status = Integer.parseInt(String.valueOf(mdata.get("state")));
        DeliveryStatus status = null;
        switch (order_status) {
            case 1:
                status = DeliveryStatus.WAIT_SCHEDULE;
                break;
            case 3:
            case 4:
                status = DeliveryStatus.ACCEPT;
                break;
            case 5:
                status = DeliveryStatus.TAKEN;
                break;
            case 6:
            case 10:
                status = DeliveryStatus.ARRIVED;
                break;
            case -1:
                status = DeliveryStatus.CANCELED;
                break;
        }
        peisongInfo.setStatus(status);
        peisongInfo.setType(DeliveryType.UU_OPEN);
        //logger.info(peisongInfo.toString());
//        if (peisongInfo.getStatus() == DeliveryStatus.WAIT_SCHEDULE) {
//            logger.info("UU订单待调度不同步到系统" + peisongInfo.getUuPeisongId());
//            return "ok";
//        }
        Boolean p = true;
        DeliveryType type = null;
        try {
            type = orderService.updateOrderPeisongInfo(peisongInfo);
        } catch (Exception e) {
            logger.error("同步UU配送状态出错  订单保存出错 创建异常通知" + peisongInfo.getOrderId(), e);
            p = false;
            String id;
            if (StringUtils.isNotBlank(peisongInfo.getOrderId())) {
                //是聚合配送同步失败的情况（包括发起保存成功 就是有uuid和发起保存失败 没有uuid）
                orderNotificationService.saveOrderNotification(Plat.JDDJ, OrderNotificationType.UU, peisongInfo.getOrderId(), false, peisongInfo.getUuPeisongId(), peisongInfo.getUuPeisongId());
            } else {
                //是平常的uu同步失败的情况  这种情况是没有订单的id的  只能通过uuid来同步
                orderNotificationService.saveOrderNotification(Plat.ELE, OrderNotificationType.UU, peisongInfo.getUuPeisongId(), false, e.getMessage(), peisongInfo.toString());
            }


        }
        if (type != null) {
            logger.info("开始同步配送信息到平台");
            try {
                orderService.updatePiesonInfoToPlat(peisongInfo, type);
            } catch (Exception e) {
                logger.error("同步配送信息出错" + e.getMessage());
            }
        }
        if (p) {
            return "ok";
        } else {
            return "fail";
        }

    }


    public static class ReturnInfo {
        @JSONField(name = "error_code")
        private int errorCode;
        @JSONField(name = "error_msg")
        private String errorMsg;

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }
}
