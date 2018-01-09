package com.elasticsearchcache.util;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * @author lks21c
 */
public class PeriodUtil {

    public static final int MILLS_DAY = 3600 * 24 * 1000;

    public static final int MILLS_HOUR = 3600 * 1000;

    public static final int MILLS_MINUTE = 60 * 1000;

    public static int periodBetween(DateTime startDt, DateTime endDt, long interval) {
        int periodBetween = (int) ((endDt.getMillis() - startDt.getMillis()) / interval);
        return periodBetween;
    }

    public static long getRestMills(DateTime dt, int periodUnit) {
        long localHourDiff = getLocalTimeDiff() * PeriodUtil.MILLS_HOUR;
        long modifiedMills = dt.getMillis() + localHourDiff;
        long restMills = (modifiedMills) % periodUnit;
        return restMills;
    }

    public static DateTime getNewStartDt(DateTime dt, int periodUnit) {
        long restMills = getRestMills(dt, periodUnit);
        return dt.plus(PeriodUtil.MILLS_DAY - restMills);
    }

    public static int getLocalTimeDiff() {
        Calendar cal = Calendar.getInstance();
        int offset = (int) ((cal.getTimeZone().getRawOffset()) * (2.77777778 / 10000000));
        return offset;
    }
}
