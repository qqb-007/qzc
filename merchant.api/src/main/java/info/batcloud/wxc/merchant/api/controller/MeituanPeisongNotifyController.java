package info.batcloud.wxc.merchant.api.controller;


import com.sankuai.meituan.banma.sign.SignHelper;
import info.batcloud.wxc.core.config.MeituanPeisongApp;
import info.batcloud.wxc.core.enums.DeliveryStatus;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/mt-peisong")
@RestController
public class MeituanPeisongNotifyController {

    private static final Logger logger = LoggerFactory.getLogger(MeituanPeisongNotifyController.class);

    @Inject
    private MeituanPeisongApp meituanPeisongApp;

    @Inject
    private OrderService orderService;

    @PostMapping("/exception/notify")
    public Object exceptionNotify() {

        return ReturnData.of(0);
    }

    @PostMapping("/scope/notify")
    public Object scopeNotify() {

        return ReturnData.of(0);
    }

    @PostMapping("/risk-level/notify")
    public Object riskLevelNotify() {

        return ReturnData.of(0);
    }

    @PostMapping("/shop-create/notify")
    public Object shopCreateNotify() {

        return ReturnData.of(0);
    }

    @PostMapping("/order/notify")
    public Object orderNotify(PushForm form, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> params = new HashMap<>(11);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String val = request.getParameter(key);
            params.put(key, val);
        }
        String sign = SignHelper.generateSign(params, meituanPeisongApp.getSecret());
        if (!sign.equals(form.getSign())) {
            logger.error("美团配送通知，签名错误");
            throw new BizException("签名错误");
        }
        logger.info("接收到美团配送订单通知，状态：" + form.getStatus());
        DeliveryStatus status = null;
        switch (form.getStatus()) {
            case 0:
                status = DeliveryStatus.WAIT_SCHEDULE;
                break;
            case 20:
                status = DeliveryStatus.ACCEPT;
                break;
            case 30:
                status = DeliveryStatus.TAKEN;
                break;
            case 50:
                status = DeliveryStatus.ARRIVED;
                break;
            case 99:
                status = DeliveryStatus.CANCELED;
                break;
        }
        OrderService.PeisongInfo peisongInfo = new OrderService.PeisongInfo();
        peisongInfo.setDeliveryId(form.getDelivery_id());
        peisongInfo.setCancelReason(form.getCancel_reason());
        peisongInfo.setCancelReasonId(form.getCancel_reason_id());
        peisongInfo.setCourierName(form.getCourier_name());
        peisongInfo.setCourierPhone(form.getCourier_phone());
        peisongInfo.setMtPeisongId(form.getMt_peisong_id());
        peisongInfo.setOrderId(form.getOrder_id());
        peisongInfo.setStatus(status);
        peisongInfo.setType(DeliveryType.MEITUAN_OPEN);
        if(status == DeliveryStatus.WAIT_SCHEDULE){
            logger.info("海葵待调度不同步到系统中" + peisongInfo.getDeliveryId());
            return ReturnData.of(0);
        }
        DeliveryType type = null;
        Boolean p = true;
        try {
            type = orderService.updateOrderPeisongInfo(peisongInfo);
        } catch (Exception e) {
            logger.error("同步海葵配送信息出错 订单保存出错 一分钟后重试" + peisongInfo.getOrderId(), e);
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
        if (p) {
            return ReturnData.of(0);
        } else {
            return "fail";
        }

    }

    public static class ReturnData {
        private int code;

        public static ReturnData of(int code) {
            ReturnData data = new ReturnData();
            data.code = code;
            return data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static class PushForm {
        private long delivery_id;
        private String mt_peisong_id;
        private String order_id;
        private int status;
        private String courier_name;
        private String courier_phone;
        private int cancel_reason_id;
        private String cancel_reason;
        private String appkey;
        private long timestamp;
        private String sign;

        public long getDelivery_id() {
            return delivery_id;
        }

        public void setDelivery_id(long delivery_id) {
            this.delivery_id = delivery_id;
        }

        public String getMt_peisong_id() {
            return mt_peisong_id;
        }

        public void setMt_peisong_id(String mt_peisong_id) {
            this.mt_peisong_id = mt_peisong_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCourier_name() {
            return courier_name;
        }

        public void setCourier_name(String courier_name) {
            this.courier_name = courier_name;
        }

        public String getCourier_phone() {
            return courier_phone;
        }

        public void setCourier_phone(String courier_phone) {
            this.courier_phone = courier_phone;
        }

        public int getCancel_reason_id() {
            return cancel_reason_id;
        }

        public void setCancel_reason_id(int cancel_reason_id) {
            this.cancel_reason_id = cancel_reason_id;
        }

        public String getCancel_reason() {
            return cancel_reason;
        }

        public void setCancel_reason(String cancel_reason) {
            this.cancel_reason = cancel_reason;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

}
