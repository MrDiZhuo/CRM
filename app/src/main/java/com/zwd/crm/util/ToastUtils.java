package com.zwd.crm.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by asus-pc on 2016/11/5.
 */

public class ToastUtils {
    public static void showShort(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context,String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
