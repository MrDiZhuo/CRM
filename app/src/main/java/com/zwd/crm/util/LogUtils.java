package com.zwd.crm.util;

import android.util.Log;

/**
 * Created by asus-pc on 2017/3/8.
 */

public class LogUtils {
    public static void v(String className, String methodName, String msg) {
        Log.v(className, '#' + methodName + "：" + msg);
    }

    public static void d(String className, String methodName, String msg) {
        Log.d(className, '#' + methodName + "：" + msg);
    }

    public static void i(String className, String methodName, String msg) {
        Log.i(className, '#' + methodName + "：" + msg);
    }

    public static void e(String className, String methodName, String msg) {
        Log.e(className, '#' + methodName + "：" + msg);
    }

    public static void w(String className, String methodName, String msg) {
        Log.w(className, '#' + methodName + "：" + msg);
    }
}
