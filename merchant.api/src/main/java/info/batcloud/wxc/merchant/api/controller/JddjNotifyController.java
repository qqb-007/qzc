package info.batcloud.wxc.merchant.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import info.batcloud.wxc.core.aop.OrderNotificationTraceAspect;
import info.batcloud.wxc.core.config.JingdongApp;
import info.batcloud.wxc.core.enums.OrderRefundStatus;
import info.batcloud.wxc.core.enums.Plat;
import info.batcloud.wxc.core.exception.BizException;
import info.batcloud.wxc.core.service.JddjWaimaiService;
import info.batcloud.wxc.core.service.OrderService;
import info.batcloud.wxc.core.service.StoreService;
import jd.sdk.JingdongClient;
import jd.sdk.request.GetStoreInfoReq;
import jd.sdk.request.OrderQueryReq;
import jd.sdk.response.GetStoreInfoRes;
import jd.sdk.response.OrderQueryRes;
import jd.sdk.util.AESUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/jddj/notify/djsw")
@RestController
public class JddjNotifyController {
    private static final Logger logger = LoggerFactory.getLogger(JddjNotifyController.class);
    @Inject
    private JingdongClient jingdongClient;

    @Inject
    private OrderService orderService;

    @Inject
    private StoreService storeService;

    @PostMapping("getToken")
    public Object getAppToken(@RequestParam String token, HttpServletRequest request) {
        logger.info("接收到京东授权码");
        logger.info("授权码：" + token);
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("newOrder")
    public Object newOrder(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东创建订单消息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String platOrderId = String.valueOf(data.get("billId"));
            logger.info("订单id" + platOrderId);
            OrderQueryReq queryReq = new OrderQueryReq();
            queryReq.setOrderId(Long.valueOf(platOrderId));
            OrderService.OrderSaveResult rs = new OrderService.OrderSaveResult();
            OrderQueryRes queryRes = jingdongClient.request(queryReq);
            if (!queryRes.getCode().equals("0") || !queryRes.getData().getCode().equals("0")) {
                rs.setSuccess(false);
                rs.setMsg(queryRes.getMsg());
            } else {
                OrderQueryRes.Result result = queryRes.getData().getResult();
                OrderService.OrderParam orderParam = JddjWaimaiService.toOrder(result);
                List<OrderQueryRes.OrderProductDTO> product = result.getResultList().get(0).getProduct();
                List<OrderService.OrderDetailParam> detailParams = new ArrayList<>();
                try {
                    for (OrderQueryRes.OrderProductDTO dto : product) {
                        OrderService.OrderDetailParam p = new OrderService.OrderDetailParam();
                        p.setFoodName(dto.getSkuName());
                        p.setQuantity(Float.valueOf(dto.getSkuCount()));
                        String skuIdIsv = dto.getSkuIdIsv();
                        p.setSkuId(orderService.getJddjFoodSku(skuIdIsv));
                        p.setPrice(Float.valueOf(dto.getSkuJdPrice()) / 100);
                        p.setBoxNum(Float.valueOf(dto.getSkuCount()));
                        p.setBoxPrice(Float.valueOf(dto.getCanteenMoney() / dto.getSkuCount()));
                        p.setUnit("");
                        p.setSpec("");
                        p.setCode(skuIdIsv.substring(0, skuIdIsv.length() - 2));
                        detailParams.add(p);
                    }
                } catch (Exception e) {
                    logger.error("同步京东订单商品出错" + e.getMessage());
                }
                orderParam.setDetailParamList(detailParams);
                rs = orderService.saveOrder(orderParam);
            }
            if (rs.isSuccess()) {
                logger.info("订单保存成功，orderId：" + platOrderId);
            } else {
                logger.error("订单保存失败，orderId：" + platOrderId);
            }
            OrderNotificationTraceAspect.TraceResult tr = new OrderNotificationTraceAspect.TraceResult();
            tr.setErrMsg(rs.getMsg() + " " + rs.getErrMsg());
            tr.setSuccess(rs.isSuccess());
            OrderNotificationTraceAspect.TRACE_RESULT.set(tr);
        } catch (Exception e) {
            logger.error("保存京东订单出错" + e.getMessage());
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("userCancelOrder")
    public Object userCancelOrder(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东订单取消通知");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String platOrderId = (String) data.get("billId");
            orderService.cancelOrderByPlat(Plat.JDDJ, platOrderId, 1002, "其他原因");
            orderService.printCancelInfo(Plat.JDDJ, platOrderId);
        } catch (Exception e) {
            logger.error("京东订单取消出错");
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("applyCancelOrder")
    public Object applyCancelOrder(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东用户申请取消订单通知");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String statusId = String.valueOf(data.get("statusId"));
            if ("20030".equals(statusId)) {
                String id = String.valueOf(data.get("billId"));
                orderService.refundOrder(Plat.JDDJ, id, OrderRefundStatus.PENDING, null);
                //orderService.printCancelInfo(Plat.JDDJ, id);
            }
        } catch (Exception e) {
            logger.error("同步京东申请取消出错");
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("deliveryOrder")
    public Object deliveryOrder(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东订单配送消息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String platOrderId = String.valueOf(data.get("billId"));
            orderService.syncPlatOrder(Plat.JDDJ, platOrderId, true);
        } catch (Exception e) {
            logger.error("同步京东订单配送消息出错");
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("finishOrder")
    public Object finishOrder(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东订单完成消息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String platOrderId = String.valueOf(data.get("billId"));
            orderService.syncPlatOrder(Plat.JDDJ, platOrderId, true);
        } catch (Exception e) {
            logger.error("同步京东订单完成出错");
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }


    @PostMapping("orderWaitOutStore")
    public Object orderWaitOutStore(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东订单待出库（确认订单）消息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String platOrderId = String.valueOf(data.get("billId"));
            orderService.confirmOrderByPlat(Plat.JDDJ, platOrderId);
        } catch (Exception e) {
            logger.error("京东确认订单出错");
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }


    @PostMapping("pushDeliveryStatus")
    public Object pushDeliveryStatus(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东订单配送状态信息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String platOrderId = String.valueOf(data.get("orderId"));
            String sstatus = String.valueOf(data.get("deliveryStatus"));
            Integer status = Integer.valueOf(sstatus);
            OrderService.JdOrderInfo orderInfo = new OrderService.JdOrderInfo();
            orderInfo.setPlatOrderId(platOrderId);
            if (status == 20 || status == 22 || status == 23 || status == 30 || status == 40) {
                //已经抢单或者更换了配送员，更新订单达达接单转态和订单配送信息
                orderInfo.setDadaTake(true);
                orderInfo.setPhone(String.valueOf(data.get("deliveryManPhone")));
            } else if (status == 21 || status == 25) {
                //被达达配送员取消或者取货失败，更新订单达达接单状态和订单配送信息
                orderInfo.setDadaTake(false);
            }
            orderService.updateJddjOrderStatus(orderInfo);
        } catch (Exception e) {
            logger.error("接收京东配送状态出错", e.getMessage());
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("newAfterSaleBill")
    public Object newAfterSaleBill(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东创建售后订单消息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String platOrderId = String.valueOf(data.get("billId"));
            orderService.syncPlatOrder(Plat.JDDJ, platOrderId, true);
        } catch (Exception e) {
            logger.error("接收京东创建售后订单出错");
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("newApplyAfterSaleBill")
    public Object newApplyAfterSaleBill(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东创建售后单申请消息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        JSONObject jo = new JSONObject();
        Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
        data.toString();
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("updateApplyAfterSaleBill")
    public Object updateApplyAfterSaleBill(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东修改售后单申请消息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        JSONObject jo = new JSONObject();
        Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
        data.toString();
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }

    @PostMapping("afterSaleBillStatus")
    public Object afterSaleBillStatus(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东售后单状态消息");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            String platOrderId = String.valueOf(data.get("billId"));
            orderService.syncPlatOrder(Plat.JDDJ, platOrderId, true);
        } catch (Exception e) {
            logger.error("同步京东订单出错");
        }
        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }


    @PostMapping("storeCrud")
    public Object storeCrud(@RequestParam String sign, String jd_param_json, String timestamp, String encrypt_jd_param_json, HttpServletRequest request) {
        logger.info("接收到京东店铺状态更新");
        if (StringUtils.isNotBlank(encrypt_jd_param_json)) {
            try {
                jd_param_json = AESUtils.decryptAES(encrypt_jd_param_json, jingdongClient.getAppSecret().substring(0, 16), jingdongClient.getAppSecret().substring(16, 32));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String checkSign = jingdongClient.getSign(jd_param_json, timestamp);
        if (!checkSign.equals(sign)) {
            logger.error("京东到家签名错误");
            throw new BizException("签名错误");
        }
        try {
            JSONObject jo = new JSONObject();
            Map<String, Object> data = (Map<String, Object>) jo.parse(jd_param_json);
            System.out.println(data.toString());
            String code = String.valueOf(data.get("billId"));
            System.out.println(code + "*****");
            String status = String.valueOf(data.get("statusId"));
            if ("12009".equals(status)) {
                GetStoreInfoReq req = new GetStoreInfoReq();
                req.setStoreNo(code);
                GetStoreInfoRes res = jingdongClient.request(req);
                if ("0".equals(res.getCode()) && "0".equals(res.getData().getCode())) {
                    GetStoreInfoRes.StoreInfo info = res.getData().getResult();
                    if (info.getCloseStatus() == 0) {
                        storeService.updateOpenStatus(code, Plat.JDDJ, true);
                    } else if (info.getCloseStatus() == 1) {
                        storeService.updateOpenStatus(code, Plat.JDDJ, false);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("同步京东到家店铺状态出错" + e.getMessage());
        }

        ReturnInfo info = new ReturnInfo();
        info.setCode("0");
        info.setMsg("success");
        info.setData("");
        return JSON.toJSONString(info);
    }


    public static class ReturnInfo {
        private String code;
        private String msg;
        private String data;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
