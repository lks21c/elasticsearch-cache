package com.elasticsearchcache.util;

import org.joda.time.DateTime;

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
}
