package com.example.a03_kotlindemo.maniu.blockcanary;

import android.os.Looper;

/**
 * 抄的com.github.markzhai:blockcanary-android:1.5.0
 * 只保留日志部分功能
 * https://github.com/markzhai/AndroidPerformanceMonitor/blob/master/README_CN.md
 * 感谢作者
 */
public class BlockCanary {
    //替换主线程Looper中的Printer
    public static void install(){
        LogMonitor logMonitor = new LogMonitor();
        Looper.getMainLooper().setMessageLogging(logMonitor);
    }
}
