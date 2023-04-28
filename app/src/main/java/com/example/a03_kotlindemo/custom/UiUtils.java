package com.example.a03_kotlindemo.custom;

import com.example.a03_kotlindemo.App;

/**
 * UI工具类
 */
public class UiUtils {

    public static int px2dp(float pxValue) {
        final float scale = App.context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int dp2px(float dpValue) {
        float scale = App.context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
