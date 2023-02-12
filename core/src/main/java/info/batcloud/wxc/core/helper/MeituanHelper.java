package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.enums.OrderRefundStatus;
import info.batcloud.wxc.core.enums.OrderStatus;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public class MeituanHelper {

    public static OrderStatus toOrderStatus(int i) {
        switch (i) {
            case 1:
                return OrderStatus.PAID;
            case 2:
                return OrderStatus.WAIT_MERCHANT_CONFIRM;
            case 4:
                return OrderStatus.MERCHANT_CONFIRMED;
            case 6:
                return OrderStatus.SHIPPING;
            case 7:
                return OrderStatus.SHIPPED;
            case 8:
                return OrderStatus.FINISHED;
            case 9:
                return OrderStatus.CANCELED;
        }
        return null;
    }

    public static OrderRefundStatus getRefundStatus(int resType) {
        OrderRefundStatus status;
        switch (resType) {
            case 0:
                status = OrderRefundStatus.PENDING;
                break;
            case 1:
            case 3:
                status = OrderRefundStatus.REJECT;
                break;
            case 2:
            case 4:
            case 5:
            case 6:
                status = OrderRefundStatus.AGREE;
                break;
            case 7:
                status = OrderRefundStatus.CANCELED;
                break;
            default:
                status = null;
        }
        return status;
    }

    public static Map<String, Object> ok() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", "ok");
        return map;
    }

    public static boolean checkSign(String sign, Map<String, String> param, String url) {
        param.remove("sig");
        Map<String, String> sortMap = new TreeMap<>(
                String::compareTo);
        sortMap.putAll(param);
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue());
        }
        url += "?" + String.join("&", list);
        return DigestUtils.md5Hex(url).equals(sign);
    }
}
