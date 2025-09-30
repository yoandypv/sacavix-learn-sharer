package com.sacavix.learn.sharerer.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String dateToString(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(formatter);
    }

}
