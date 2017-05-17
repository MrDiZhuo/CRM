package com.zwd.crm.util;

import android.content.Context;

/**
 * Created by asus-pc on 2016/11/20.
 */

public class dip2pxUtils {
    public static int dip2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
}
