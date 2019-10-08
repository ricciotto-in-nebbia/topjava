package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER1 = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String outputFormat(LocalDateTime localDateTime) {
        return localDateTime.format(OUTPUT_FORMATTER);
    }

    public static LocalTime parseTime(String string) {
        return LocalTime.parse(string, DATE_TIME_FORMATTER);
    }

    public static LocalDate parseDate(String string) {
        return LocalDate.parse(string, DATE_TIME_FORMATTER1);
    }
}
