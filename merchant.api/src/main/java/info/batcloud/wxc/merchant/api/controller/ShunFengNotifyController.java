package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import info.batcloud.wxc.core.config.ShunfengPeisongApp;
import info.batcloud.wxc.core.enums.DeliveryStatus;
import info.batcloud.wxc.core.enums.DeliveryType;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import shunfeng.util.SignUtil;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RequestMapping("/sf-peisong")
@RestController
public class ShunFengNotifyController {
    private static final Logger logger = LoggerFactory.getLogger(ShunFengNotifyController.class);
    @Inject
    private ShunfengPeisongApp shunfengPeisongApp;

    @Inject
    private OrderService orderService;

    @PostMapping("order/status")
    public Object orderStatusChange(@RequestBody String data, @RequestParam("sign") String sign, HttpServletRequest request) {
        logger.info("接收到顺丰订单状态通知");
        logger.info("data: " + data);
        logger.info("sign: " + sign);
        String checkSign = null;
        //验证签名是否正确
        try {
            checkSign = SignUtil.sign(data, shunfengPeisongApp.getDevKey(), shunfengPeisongApp.getDevId());
        } catch (UnsupportedEncodingException e) {
            logger.error("验证签名出错", e);
        }
        if (!sign.equals(checkSign)) {
            logger.error("顺丰配送通知，签名错误");
            //throw new BizException("签名错误");
        }

        JSONObject jo = new JSONObject();
        Map<String, Object> mdata = (Map<String, Object>) jo.parse(data);
        logger.info(mdata.toString());
        OrderService.PeisongInfo peisongInfo = new OrderService.PeisongInfo();
        peisongInfo.setType(DeliveryType.SHUFENG_OPEN);
        peisongInfo.setSfPeisongId(String.valueOf(mdata.get("sf_order_id")));
        peisongInfo.setOrderId(String.valueOf(mdata.get("shop_order_id")));
        peisongInfo.setCourierName(String.valueOf(mdata.get("operator_name")));
        peisongInfo.setCourierPhone(String.valueOf(mdata.get("operator_phone")));
        int order_status = Integer.parseInt(String.valueOf(mdata.get("order_status")));
        if (order_status == 10 || order_status == 12) {
            peisongInfo.setStatus(DeliveryStatus.ACCEPT);
            peisongInfo.setLongitude(String.valueOf(mdata.get("rider_lng")));
            peisongInfo.setLatitude(String.valueOf(mdata.get("rider_lat")));
        } else if (order_status == 15) {
            peisongInfo.setStatus(DeliveryStatus.TAKEN);
            peisongInfo.setLongitude(String.valueOf(mdata.get("rider_lng")));
            peisongInfo.setLatitude(String.valueOf(mdata.get("rider_lat")));
        }
        DeliveryType type = null;
        Boolean p = true;
        try {
            type = orderService.updateOrderPeisongInfo(peisongInfo);
        } catch (Exception e) {
            logger.error("同步顺丰配送信息出错 订单保存出错  一分钟后重试");
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

        ReturnInfo returnInfo = new ReturnInfo();
        if (p) {
            returnInfo.setErrorCode(0);
            returnInfo.setErrorMsg("success");
            return JSON.toJSONString(returnInfo);
        } else {
            throw new BizException("系统出错请重试");
        }

    }

    @PostMapping("order/finish")
    public Object orderFinish(@RequestBody String data, @RequestParam("sign") String sign, HttpServletRequest request) {
        logger.info("接收到顺丰订单完成通知");
        logger.info("data: " + data);
        logger.info("sign: " + sign);
        String checkSign = null;
        //验证签名是否正确
        try {
            checkSign = SignUtil.sign(data, shunfengPeisongApp.getDevKey(), shunfengPeisongApp.getDevId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!sign.equals(checkSign)) {
            logger.error("顺丰配送通知，签名错误");
            //throw new BizException("签名错误");
        }
        JSONObject jo = new JSONObject();
        Map<String, Object> mdata = (Map<String, Object>) jo.parse(data);
        logger.info(mdata.toString());
        OrderService.PeisongInfo peisongInfo = new OrderService.PeisongInfo();
        peisongInfo.setType(DeliveryType.SHUFENG_OPEN);
        peisongInfo.setOrderId(String.valueOf(mdata.get("shop_order_id")));
        peisongInfo.setSfPeisongId(String.valueOf(mdata.get("sf_order_id")));
        peisongInfo.setStatus(DeliveryStatus.ARRIVED);
        DeliveryType type = null;
        Boolean p = true;
        try {
            type = orderService.updateOrderPeisongInfo(peisongInfo);
        } catch (Exception e) {
            logger.error("同步顺丰配送信息出错 订单保存出错  一分钟后重试");
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

        ReturnInfo returnInfo = new ReturnInfo();
        if (p) {
            returnInfo.setErrorCode(0);
            returnInfo.setErrorMsg("success");
            return JSON.toJSONString(returnInfo);
        } else {
            throw new BizException("系统出错请重试");
        }
    }

    @PostMapping("order/cancel")
    public Object orderCancel(@RequestBody String data, @RequestParam("sign") String sign, HttpServletRequest request) {
        logger.info("接收到顺丰订单取消通知");
        logger.info("data: " + data.toString());
        logger.info("sign: " + sign);
        String checkSign = null;
        //验证签名是否正确
        try {
            checkSign = SignUtil.sign(data, shunfengPeisongApp.getDevKey(), shunfengPeisongApp.getDevId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!sign.equals(checkSign)) {
            logger.error("顺丰配送通知，签名错误");
            //throw new BizException("签名错误");
        }

        JSONObject jo = new JSONObject();
        Map<String, Object> mdata = (Map<String, Object>) jo.parse(data);
        logger.info(mdata.toString());
        OrderService.PeisongInfo peisongInfo = new OrderService.PeisongInfo();
        peisongInfo.setType(DeliveryType.SHUFENG_OPEN);
        peisongInfo.setStatus(DeliveryStatus.CANCELED);
        peisongInfo.setSfPeisongId(String.valueOf(mdata.get("sf_order_id")));
        peisongInfo.setOrderId(String.valueOf(mdata.get("shop_order_id")));
        peisongInfo.setCancelReason("cancel_reason");//可能为空字符串
        DeliveryType type = null;
        Boolean p = true;
        try {
            type = orderService.updateOrderPeisongInfo(peisongInfo);
        } catch (Exception e) {
            logger.error("同步顺丰配送信息出错 订单保存出错  一分钟后重试");
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

        ReturnInfo returnInfo = new ReturnInfo();
        if (p) {
            returnInfo.setErrorCode(0);
            returnInfo.setErrorMsg("success");
            return JSON.toJSONString(returnInfo);
        } else {
            throw new BizException("系统出错请重试");
        }
    }

    @PostMapping("order/exception")
    public Object orderException(@RequestBody String data, @RequestParam("sign") String sign, HttpServletRequest request) {
        logger.info("接收到顺丰订单异常通知");
        logger.info("data: " + data.toString());
        logger.info("sign: " + sign);
        String checkSign = null;
        //验证签名是否正确
        try {
            checkSign = SignUtil.sign(data, shunfengPeisongApp.getDevKey(), shunfengPeisongApp.getDevId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!sign.equals(checkSign)) {
            logger.error("顺丰配送通知，签名错误");
            throw new BizException("签名错误");
        }
        JSONObject jo = new JSONObject();
        Map<String, Object> mdata = (Map<String, Object>) jo.parse(data);
        logger.info(mdata.toString());
        OrderService.PeisongInfo peisongInfo = new OrderService.PeisongInfo();
        peisongInfo.setType(DeliveryType.SHUFENG_OPEN);
        peisongInfo.setOrderId(String.valueOf(mdata.get("shop_order_id")));
        peisongInfo.setSfPeisongId(String.valueOf(mdata.get("sf_order_id")));
        peisongInfo.setExId(Integer.parseInt(String.valueOf(mdata.get("ex_id"))));
        peisongInfo.setExContent(String.valueOf(mdata.get("ex_content")));
        peisongInfo.setExpectTime(String.valueOf(mdata.get("expect_time")));
        //???
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setErrorCode(0);
        returnInfo.setErrorMsg("success");
        return JSON.toJSONString(returnInfo);
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
