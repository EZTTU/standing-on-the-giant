package org.cuit.echo.seon.utils.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.cuit.echo.seon.utils.exception.BusinessException;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author EtyMx
 * @date 2020-07-11
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String format(long timestamp, String pattern) {
        return format(new Date(timestamp), pattern);
    }

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static String format(TemporalAccessor date, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(date);
    }

    public static long parseToTimestamp(final String date, final String... parsePatterns) {
        return parseToDate(date, parsePatterns).getTime();
    }

    public static Date parseToDate(final String date, final String... parsePatterns) {
        try {
            return parseDate(date, parsePatterns);
        } catch (ParseException e) {
            throw new BusinessException("日期格式错误，无法解析");
        }
    }

    public static LocalDate parseToLocalDate(final String date, final String... parsePatterns) {
        for (String parsePattern : parsePatterns) {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(parsePattern));
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new BusinessException("日期格式错误，无法解析");
    }

    public static LocalDateTime parseToLocalDateTime(final String date, final String... parsePatterns) {
        for (String parsePattern : parsePatterns) {
            try {
                return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(parsePattern));
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new BusinessException("日期格式错误，无法解析");
    }

    public static long toTimestamp(LocalDateTime datetime) {
        return datetime.toInstant(ZoneOffset.systemDefault().getRules().getOffset(Instant.now())).toEpochMilli();
    }

    public static long toTimestamp(LocalDate date) {
        return toTimestamp(date.atStartOfDay());
    }

    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}
