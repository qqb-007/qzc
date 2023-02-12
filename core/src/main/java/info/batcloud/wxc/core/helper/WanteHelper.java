package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.domain.LocateInfo;
import info.batcloud.wxc.core.enums.OrderStatus;

public class WanteHelper {
    public static OrderStatus toOrderStatus(String i) {
        switch (i) {
            case "WAIT_PAYMENT":
                return OrderStatus.MERCHANT_CONFIRMED;
            case "TO_BE_EVALUATED":
                return OrderStatus.SHIPPING;
            case "PAYMENT":
                return OrderStatus.MERCHANT_CONFIRMED;
            case "TO_BE_RECEIVED":
                return OrderStatus.MERCHANT_CONFIRMED;
            case "COMPILE":
                return OrderStatus.FINISHED;
            case "CANCEL":
                return OrderStatus.CANCELED;
        }
        return null;
    }
}
