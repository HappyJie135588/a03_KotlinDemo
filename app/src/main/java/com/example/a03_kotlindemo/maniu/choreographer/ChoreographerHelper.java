package com.example.a03_kotlindemo.maniu.choreographer;

import android.util.Log;
import android.view.Choreographer;

/**
 * 编舞者doFrame调用与垂直同步信号VSYNC同步，16.6ms为流畅,当超过16.6ms会掉帧，用户感知卡顿
 * 编舞者打印卡顿时长与丢帧信息
 */
public class ChoreographerHelper {
    private static final String TAG = "ChoreographerHelper";
    static long lastFrameTimeNanos = 0;

    public static void start() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                //上次回调时间
                if (lastFrameTimeNanos == 0) {
                    lastFrameTimeNanos = frameTimeNanos;
                    Choreographer.getInstance().postFrameCallback(this);
                    return;
                }
                //纳秒转毫秒
                long diff = (frameTimeNanos - lastFrameTimeNanos) / 1_000_000;
                if (diff > 16.6f) {
                    //掉帧数
                    int droppedCount = (int) (diff / 16.6);
                    if (droppedCount > 2) {
                        Log.w(TAG, "UI线程超时(超过16ms)当前:" + diff + "ms" + ",丢帧：" + droppedCount);
                    }
                }
                lastFrameTimeNanos = frameTimeNanos;
                Choreographer.getInstance().postFrameCallback(this);
            }
        });

    }
}
