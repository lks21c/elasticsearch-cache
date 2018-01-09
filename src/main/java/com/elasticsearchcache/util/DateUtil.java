package com.elasticsearchcache.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lks21c
 */
public class DateUtil {

    public static DateTime convertToDatetime(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new DateTime(dateFormat.parse(dateString));
    }

    public static Date getDate(int year, int month, Integer day, Integer hour, Integer minute) {
        return new Date(year - 1900, month - 1, day, hour, minute);
    }

    public static DateTime getDateTime(int year, int month, Integer day, Integer hour, Integer minute) {
        return new DateTime(new Date(year - 1900, month - 1, day, hour, minute));
    }

    public static DateTime getDateTime(int year, int month, Integer day) {
        return new DateTime(new Date(year - 1900, month - 1, day));
    }

}
