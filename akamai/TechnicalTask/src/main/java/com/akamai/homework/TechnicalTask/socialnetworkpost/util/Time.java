package com.akamai.homework.TechnicalTask.socialnetworkpost.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(PATTERN);

    public static String map(LocalDateTime value) {
        return value.toString().replace("T", " ");
    }

    public static LocalDateTime map(String value) {
        return LocalDateTime.parse(value, formatter);
    }

}
