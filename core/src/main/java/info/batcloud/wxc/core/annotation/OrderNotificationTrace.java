package info.batcloud.wxc.core.annotation;

import info.batcloud.wxc.core.enums.OrderNotificationType;
import info.batcloud.wxc.core.enums.Plat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderNotificationTrace {
    Plat plat();

    OrderNotificationType type();

    String orderId();

    String data();
}
