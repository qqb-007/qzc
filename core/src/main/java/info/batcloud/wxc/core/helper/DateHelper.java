package info.batcloud.wxc.core.helper;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {


    public static Date firstDayOfWeekByDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Sunday
        Date startDate = c.getTime();
        return startDate;
    }

    public static Date lastDayOfWeekByDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        Date startDate = c.getTime();
        return startDate;
    }

    public static int[] yearAndWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        int year = c.get(Calendar.YEAR);
        int week = c.get(Calendar.WEEK_OF_YEAR);
        return new int[]{year, week};
    }

}
