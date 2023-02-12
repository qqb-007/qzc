package info.batcloud.wxc.core.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import info.batcloud.wxc.core.annotation.OrderNotificationTrace;
import info.batcloud.wxc.core.service.OrderNotificationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@Aspect
public class OrderNotificationTraceAspect {

    @Inject
    private OrderNotificationService orderNotificationService;

    private final static Logger logger = LoggerFactory.getLogger(OrderNotificationTraceAspect.class);

    public static ThreadLocal<TraceResult> TRACE_RESULT = new ThreadLocal<>();

    @Around("@annotation(ont)")
    public Object setOrderNotificationTrace(ProceedingJoinPoint joinPoint, OrderNotificationTrace ont) {
        String[] tmps = ont.orderId().split("\\.");
        String platOrderId;
        Object data = joinPoint.getArgs()[Integer.valueOf(ont.data().substring(1))];
        int idx = Integer.valueOf(tmps[0].replace("#", ""));
        if (tmps.length == 1) {
            platOrderId = (String) joinPoint.getArgs()[idx];
        } else {
            String arg = JSON.toJSONString(joinPoint.getArgs()[idx]);
            JSONObject param = JSON.parseObject(arg);
            platOrderId = param.getString(tmps[1]);
        }
        try {
            TraceResult traceResult = null;
            if (platOrderId == null) {
                return joinPoint.proceed();
            }
            try {
                Object rs = joinPoint.proceed();
                traceResult = TRACE_RESULT.get();
                TRACE_RESULT.remove();
                return rs;
            } catch (Exception e) {
                traceResult = new TraceResult();
                traceResult.setSuccess(false);
                traceResult.setErrMsg(e.getLocalizedMessage());
            } finally {
                orderNotificationService.saveOrderNotification(ont.plat(), ont.type(), platOrderId, traceResult.isSuccess(), traceResult.getErrMsg(), data);
            }
        } catch (Exception e) {
            orderNotificationService.saveOrderNotification(ont.plat(), ont.type(), platOrderId, false, e.getLocalizedMessage(), data);
            logger.error("Order Notification Trace Error", e);
            throw new RuntimeException(e);
        } catch (Throwable throwable) {
            orderNotificationService.saveOrderNotification(ont.plat(), ont.type(), platOrderId, false, throwable.getLocalizedMessage(), data);
            logger.error("Order Notification Trace Error", throwable);
            throw new RuntimeException(throwable);
        }
        return null;
    }

    public static class TraceResult {
        private boolean success;
        private String errMsg;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }
    }

}
