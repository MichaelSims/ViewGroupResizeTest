package com.example;

public class Util {

    private static final String APP_LOGGING_TAG = "ViewGroupResizeTest/";

    public static String getLoggingTag(Class clazz) {
        return APP_LOGGING_TAG + clazz.getSimpleName();
    }
}
