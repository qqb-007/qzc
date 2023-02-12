package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.enums.OrderStatus;

public class JddjHelper {
    public static OrderStatus toOrderStatus(int i){
        switch (i) {
            case 20050:
                return OrderStatus.MERCHANT_CONFIRMED;
            case 20010:
                return OrderStatus.MERCHANT_CONFIRMED;
            case 31020:
                return OrderStatus.PAID;
            case 33040:
                return OrderStatus.MERCHANT_CONFIRMED;
            case 33060:
                return OrderStatus.SHIPPED;
            case 90000:
                return OrderStatus.FINISHED;
            case 33080:
                return OrderStatus.MERCHANT_CONFIRMED;
            case 32000:
                return OrderStatus.MERCHANT_CONFIRMED;
            case 41000:
                return OrderStatus.WAIT_MERCHANT_CONFIRM;
            case 20020:
                return OrderStatus.CANCELED;
            case 20030:
                return OrderStatus.MERCHANT_CONFIRMED;
        }
        return null;
    }
}
