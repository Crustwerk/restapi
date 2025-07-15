package com.crustwerk.restapi;

import java.time.format.DateTimeFormatter;

public class Utils {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

}
