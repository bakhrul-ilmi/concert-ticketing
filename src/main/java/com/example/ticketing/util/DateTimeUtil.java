package com.example.ticketing.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateTimeUtil {
    public static final ZoneId ZONE_JAKARTA = ZoneId.of("UTC+07:00");

    public static LocalTime getCurrentTime() {
        ZonedDateTime zoned = ZonedDateTime.ofInstant(Instant.now(), ZONE_JAKARTA);
        return zoned.toLocalTime();
    }

    public static LocalDateTime getCurrentDateTime() {
        ZonedDateTime zoned = ZonedDateTime.ofInstant(Instant.now(), ZONE_JAKARTA);
        return zoned.toLocalDateTime();
    }
}
