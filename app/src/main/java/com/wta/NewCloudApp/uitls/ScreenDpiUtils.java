package com.wta.NewCloudApp.uitls;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by 李平 on 2017/8/16.
 * dp和px转换
 */

public class ScreenDpiUtils {
    public static float px2dp(Context context,float pxValue){
        return TypedValue.applyDimension(COMPLEX_UNIT_PX,pxValue,context.getResources().getDisplayMetrics());
    }

    public static float dp2px(Context context,float dpValue){
        return TypedValue.applyDimension(COMPLEX_UNIT_DIP,dpValue,context.getResources().getDisplayMetrics());
    }

    public static float px2sp(Context context,float pxValue){
        return TypedValue.applyDimension(COMPLEX_UNIT_PX,pxValue,context.getResources().getDisplayMetrics());
    }

    public static float sp2px(Context context,float spValue){
        return TypedValue.applyDimension(COMPLEX_UNIT_SP,spValue,context.getResources().getDisplayMetrics());
    }

    public static int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return 0;
        }
        int statusBarHeight;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        else
            statusBarHeight = (int) ScreenDpiUtils.dp2px(context, 24);
        return statusBarHeight;
    }

}
