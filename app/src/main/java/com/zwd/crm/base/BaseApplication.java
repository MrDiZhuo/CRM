package com.zwd.crm.base;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * android5.0以上转场动画有优化
 *
 * Created by XRY on 2016/11/4.
 */

public class BaseApplication extends Application {
    public static final String baseUrl = "http://139.224.164.183:8012/";
    @SuppressLint("SdCardPath")
    public static final String SD_LOCAL = "/sdcard/CRMExhibition";
}
