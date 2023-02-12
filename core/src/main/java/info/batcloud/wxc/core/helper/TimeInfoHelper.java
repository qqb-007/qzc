package info.batcloud.wxc.core.helper;

import info.batcloud.wxc.core.domain.TimeInfo;
import info.batcloud.wxc.core.enums.TimeInfoType;

import java.util.Date;

public class TimeInfoHelper {

    private static final long yearSeconds = 365 * 24 * 60 * 60;
    private static final long monthSeconds = 30 * 24 * 60 * 60;
    private static final long daySeconds = 24 * 60 * 60;
    private static final long hourSseconds = 60 * 60;
    private static final long minSeconds = 60;

    public static TimeInfo shortTimeInfo(Date time) {
        long timestamp = time.getTime();
        long currentTimestamp = System.currentTimeMillis();
        long range = (currentTimestamp - timestamp) / 1000;
        int year = Long.valueOf(range / yearSeconds).intValue();
        TimeInfo timeInfo = new TimeInfo();
        if(year > 0) {
            timeInfo.setValue(year);
            timeInfo.setType(TimeInfoType.YEAR);
        } else {
            int month = Long.valueOf(range / monthSeconds).intValue();
            if(month > 0) {
                timeInfo.setValue(month);
                timeInfo.setType(TimeInfoType.MONTH);
            } else {
                int days = Long.valueOf(range / daySeconds).intValue();
                if(days > 0) {
                    timeInfo.setValue(days);
                    timeInfo.setType(TimeInfoType.DAY);
                } else {
                    int hours = Long.valueOf(range / hourSseconds).intValue();
                    if(hours > 0) {
                        timeInfo.setValue(hours);
                        timeInfo.setType(TimeInfoType.HOUR);
                    } else {
                        int mins = Long.valueOf(range / minSeconds).intValue();
                        if(mins > 0) {
                            timeInfo.setValue(mins);
                            timeInfo.setType(TimeInfoType.MIN);
                        } else {
                            int seconds = Long.valueOf(range).intValue();
                            timeInfo.setValue(seconds);
                            timeInfo.setType(TimeInfoType.SEC);
                        }
                    }
                }
            }
        }

        return timeInfo;
    }

}
