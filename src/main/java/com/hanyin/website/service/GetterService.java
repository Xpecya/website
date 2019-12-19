package com.hanyin.website.service;

import java.util.Date;

/**
 * 为了避免NullPointerException
 */
public class GetterService {

    private static final Date DEFAULT_DATE = new Date(0);
    private static final String DEFAULT_STRING = "";
    private static final Integer DEFAULT_INTEGER = 0;

    private GetterService() {}

    public static String getString(String input) {
        return input == null ? DEFAULT_STRING : input;
    }

    public static Integer getInteger(Integer input) {
        return input == null ? DEFAULT_INTEGER : input;
    }

    public static Date getDate(Date date) {
        return date == null ? DEFAULT_DATE : date;
    }
}
