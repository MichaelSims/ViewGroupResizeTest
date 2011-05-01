package com.example;

import android.view.View;

public class Util {

    private static final String APP_LOGGING_TAG = "ViewGroupResizeTest/";

    public static String getLoggingTag(Class clazz) {
        return APP_LOGGING_TAG + clazz.getSimpleName();
    }

    public static String measureSpecModeToString(int mode) {
        String retVal;
        switch (mode) {
            case View.MeasureSpec.AT_MOST:
                retVal = "AT_MOST";
                break;
            case View.MeasureSpec.EXACTLY:
                retVal = "EXACTLY";
                break;
            case View.MeasureSpec.UNSPECIFIED:
                retVal = "UNSPECIFIED";
                break;
            default:
                throw new IllegalArgumentException("Unrecognized mode " + mode);
        }
        return retVal;
    }
}
