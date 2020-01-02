package com.file.manage.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String yyyy = "yyyy";
    public static final String yM = "yyyyMM";
    public static final String yMd = "yyyyMMdd";

    public static String getFormatStr(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static Long getTimeStamp(){
        return Instant.now().getEpochSecond();
    }

}
