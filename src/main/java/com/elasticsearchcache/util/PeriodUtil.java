package com.elasticsearchcache.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * @author lks21c
 */
public class PeriodUtil {
    private static final Logger logger = LogManager.getLogger(PeriodUtil.class);

    public static final int MILLS_SECOND = 1000;

    public static final int MILLS_MINUTE = 60 * MILLS_SECOND;

    public static final int MILLS_HOUR = 60 * MILLS_MINUTE;

    public static final int MILLS_DAY = 24 * MILLS_HOUR;

    public static final int MILLS_WEEK = 7 * MILLS_DAY;

    public static final int MILLS_MONTH = 31 * MILLS_DAY;

    public static final int MILLS_YEAR = 365 * MILLS_DAY;

    public static int periodBetween(DateTime startDt, DateTime endDt, long interval) {
        int periodBetween = (int) ((endDt.getMillis() - startDt.getMillis()) / interval);
        return periodBetween;
    }

    public static long getRestMills(DateTime dt, int periodUnit) {
        long localHourDiff = getLocalTimeDiff() * PeriodUtil.MILLS_HOUR;
        long modifiedMills = dt.getMillis() + localHourDiff;
        long restMills = (modifiedMills) % periodUnit;
        logger.debug("restMills = " + restMills);
        return restMills;
    }

    public static DateTime getNewStartDt(DateTime dt, int periodUnit) {
        long restMills = getRestMills(dt, periodUnit);
        return dt.plus(periodUnit - restMills);
    }

    public static int getLocalTimeDiff() {
        Calendar cal = Calendar.getInstance();
        int offset = (int) ((cal.getTimeZone().getRawOffset()) * (2.77777778 / 10000000));
        return offset;
    }

    public static String getTimeZone() {
        int localTimeDiff = getLocalTimeDiff();
        if (localTimeDiff > 0 && localTimeDiff < 10) {
            return "+0" + localTimeDiff + ":00";
        } else if (localTimeDiff > 10) {
            return "+" + localTimeDiff + ":00";
        } else if (localTimeDiff < 0 && localTimeDiff > -10) {
            return "-0" + localTimeDiff + ":00";
        } else if (localTimeDiff < -10) {
            return "-" + localTimeDiff + ":00";
        }
        return "00:00";
    }

    public static int getPeriodUnit(String interval) {
        int intervalNum = parseIntervalNum(interval);
        int periodUnit = -1;
        if (interval.contains("y")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_YEAR;
        } else if (interval.contains("M")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_MONTH;
        } else if (interval.contains("w")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_WEEK;
        } else if (interval.contains("d")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_DAY;
        } else if (interval.contains("h")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_HOUR;
        } else if (interval.contains("m")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_MINUTE;
        } else if (interval.contains("s")) {
            periodUnit = intervalNum * PeriodUtil.MILLS_SECOND;
        }
        return periodUnit;
    }

    public static int parseIntervalNum(String interval) {
        int intervalNum = -1;
        if (interval.contains("y")) {
            intervalNum = Integer.parseInt(interval.replace("y", ""));
        } else if (interval.contains("M")) {
            intervalNum = Integer.parseInt(interval.replace("M", ""));
        } else if (interval.contains("w")) {
            intervalNum = Integer.parseInt(interval.replace("w", ""));
        } else if (interval.contains("d")) {
            intervalNum = Integer.parseInt(interval.replace("d", ""));
        } else if (interval.contains("h")) {
            intervalNum = Integer.parseInt(interval.replace("h", ""));
        } else if (interval.contains("m")) {
            intervalNum = Integer.parseInt(interval.replace("m", ""));
        } else if (interval.contains("s")) {
            intervalNum = Integer.parseInt(interval.replace("s", ""));
        }
        return intervalNum;
    }
}
