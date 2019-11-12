package com.app.haircutuser.booking;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DateUtil {

    // format 24hre ex. 12:12 , 17:15
    private static String HOUR_FORMAT = "HH:mm";

    private DateUtil() {
    }

    public static String getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT);
        String hour = sdfHour.format(cal.getTime());
        return hour;
    }

    /**
     * @param target hour to check
     * @param start  interval start
     * @param end    interval end
     * @return true    true if the given hour is between
     */
    public static boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0)
                && (target.compareTo(end) <= 0));
    }

    /**
     * @param start interval start
     * @param end   interval end
     * @return true    true if the current hour is between
     */
    public static boolean isNowInInterval(String start, String end) {
        return DateUtil.isHourInInterval
                (DateUtil.getCurrentHour(), start, end);
    }
}
