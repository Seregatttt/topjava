package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static LocalDateTime toLocalDateTime(String str) {
        return LocalDateTime.parse(str, FORMATTER);
    }

    public static String formatLocalDateTime(LocalDateTime ldt) {
        return ldt != null ?  ldt.format(FORMATTER) : "";
    }
}
