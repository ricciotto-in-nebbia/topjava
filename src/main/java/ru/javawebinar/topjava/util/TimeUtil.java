package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter OUTPUTFORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String outputFormat(LocalDateTime localDateTime) {
        return localDateTime.format(OUTPUTFORMATTER);
    }

    public static LocalTime parseTime(String string) {
        return LocalTime.parse(string, timeFormatter);
    }

    public static LocalDate parseDate(String string) {
        return LocalDate.parse(string, dateFormatter);
    }
}
