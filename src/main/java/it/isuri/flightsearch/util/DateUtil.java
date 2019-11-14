package it.isuri.flightsearch.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {

    public static LocalDate getDate(long date) {

        return Instant.ofEpochSecond(date).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime getDateTime(long date) {

        return Instant.ofEpochSecond(date).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
