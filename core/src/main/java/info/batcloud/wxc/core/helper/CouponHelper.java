package info.batcloud.wxc.core.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CouponHelper {

    private static final String COUPON_AMOUNT_PATTERN = "^满(\\d+)元减(\\d+)元$";
    private static final String WTJ_COUPON_AMOUNT_PATTERN = "^(\\d+)元无条件券$";

    public static float[] parseCouponAmount(String couponInfo) {
        if(couponInfo == null) {
            return new float[]{0f,0f};
        }
        couponInfo = couponInfo.replaceAll("\\.00", "");
        Pattern p = Pattern.compile(COUPON_AMOUNT_PATTERN);
        Matcher m = p.matcher(couponInfo);
        if(m.find()) {
            return new float[]{Float.valueOf(m.group(1)), Float.valueOf(m.group(2))};
        }
        p = Pattern.compile(WTJ_COUPON_AMOUNT_PATTERN);
        m = p.matcher(couponInfo);
        if(m.find()) {
            return new float[]{0f, Float.valueOf(m.group(1))};
        }
        return new float[]{0f, 0f};
    }

}
