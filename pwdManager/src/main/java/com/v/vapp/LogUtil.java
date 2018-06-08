package com.v.vapp;

import android.util.Log;

/**
 * @author V
 * @since 2018/5/25
 */

public class LogUtil {

    private static final String LOG_TAG = "VASsdf";

    public static void d(String msg) {
        Log.d(LOG_TAG, msg);
    }

    public static void w(String msg) {
        Log.w(LOG_TAG, msg);
    }

    public static void e(String msg) {
        Log.e(LOG_TAG, msg);
    }

    public static void e(Exception e) {
        Log.e(LOG_TAG, Log.getStackTraceString(e));
    }


}
