package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.enums.OrderStatus;

public class EleHelper {

    public static OrderStatus toOrderStatus(int i) {
        switch (i) {
            case 1:
                return OrderStatus.WAIT_MERCHANT_CONFIRM;
            case 5:
                return OrderStatus.MERCHANT_CONFIRMED;
            case 7:
            case 8:
                return OrderStatus.SHIPPING;
            case 9:
                return OrderStatus.FINISHED;
            case 10:
            case 15:
                return OrderStatus.CANCELED;
        }
        return null;
    }

    public static String getFoodUpc(String upc) {

        String[] s = upc.split("_");
        if (s.length > 1) {
            return s[1];
        } else {
            return s[0];
        }

    }

}
