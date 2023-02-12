package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import info.batcloud.wxc.core.enums.DeliveryStatus;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("/ss-peisong")
@RestController
public class ShanSongController {

    private static final Logger logger = LoggerFactory.getLogger(ShanSongController.class);

    @Inject
    private OrderService orderService;

    @PostMapping("/order/status")
    public Object orderStatus(@RequestBody String data, HttpServletRequest request) {
        //logger.info(data);
        JSONObject jo = new JSONObject();
        Map<String, Object> mdata = (Map<String, Object>) jo.parse(data);
        //logger.info(mdata.toString());
        OrderService.PeisongInfo peisongInfo = new OrderService.PeisongInfo();
        peisongInfo.setType(DeliveryType.SS_OPEN);
        peisongInfo.setSsPeisongId(String.valueOf(mdata.get("issOrderNo")));
        int order_status = Integer.parseInt(String.valueOf(mdata.get("status")));
        DeliveryStatus status = null;
        switch (order_status) {
            case 20:
                status = DeliveryStatus.WAIT_SCHEDULE;
                break;
            case 30:
                status = DeliveryStatus.ACCEPT;
                break;
            case 40:
                status = DeliveryStatus.TAKEN;
                break;
            case 50:
                status = DeliveryStatus.ARRIVED;
                break;
            case 60:
                status = DeliveryStatus.CANCELED;
                break;
        }
        peisongInfo.setStatus(status);
        peisongInfo.setOrderId(String.valueOf(mdata.get("orderNo")));
        if (status != DeliveryStatus.CANCELED && status != DeliveryStatus.WAIT_SCHEDULE) {
            Map<String, Object> courer = (Map<String, Object>) jo.parse(String.valueOf(mdata.get("courier")));
            logger.info(String.valueOf(courer.get("name")));
            peisongInfo.setCourierName(String.valueOf(courer.get("name")));
            peisongInfo.setCourierPhone(String.valueOf(courer.get("mobile")));
            peisongInfo.setLatitude(String.valueOf(courer.get("latitude")));
            peisongInfo.setLongitude(String.valueOf(courer.get("longitude")));
        }

        logger.info(peisongInfo.toString());
//        if (status == DeliveryStatus.WAIT_SCHEDULE) {
//            logger.info("闪送订单待调度不同步到系统中" + peisongInfo.getSsPeisongId());
//            ReturnInfo info = new ReturnInfo();
//            info.setStatus(200);
//            info.setMsg("");
//            info.setData(null);
//            logger.info(JSON.toJSONString(info));
//            return JSON.toJSONString(info);
//        }
        Boolean p = true;
        DeliveryType type = null;
        try {
            type = orderService.updateOrderPeisongInfo(peisongInfo);
        } catch (Exception e) {
            logger.error("同步配送信息出错 闪送 订单保存出错 一分钟后重试" + e.getMessage());
            logger.error("同步配送信息出错 订单保存出错" + peisongInfo.getSsPeisongId(), e);
            p = false;
        }
        if (type != null) {
            logger.info("开始同步配送信息到平台");
            try {
                orderService.updatePiesonInfoToPlat(peisongInfo, type);
            } catch (Exception e) {
                logger.error("同步配送信息出错" + e.getMessage());
            }
        }

        ReturnInfo info = new ReturnInfo();
        if(p){
            info.setStatus(200);
            info.setMsg("");
        }else {
            info.setStatus(500);
            info.setMsg("fail");
        }
        info.setData(null);
        logger.info(JSON.toJSONString(info));
        return JSON.toJSONString(info);
    }


    public static class ReturnInfo {
        private Integer status;

        private String msg;

        private String data;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
