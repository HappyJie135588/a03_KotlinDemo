package com.example.a03_kotlindemo.maniu.blockcanary;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Printer;

import com.example.a03_kotlindemo.utils.ToastUtils;

import java.util.List;

/**
 * 自定义Printer,用于监视主线程卡顿并输出卡顿堆栈
 */
public class LogMonitor implements Printer {
    private static final String TAG = "LogMonitor";
    private StackSampler mStackSampler;
    private boolean mPrinteingStarted = false;
    //事件开始时间
    private long mStartTimestamp;
    //卡顿阈值
    private long mBlockThresholdMillis = 2001;
    //采样频率
    private long mSampleInterval = 1000;
    //子线程Handler,不影响主线程业务
    private Handler mLogHandler;

    public LogMonitor() {
        //创建子线程Handler
        HandlerThread handlerThread = new HandlerThread("block-canary-io");
        handlerThread.start();
        mLogHandler = new Handler(handlerThread.getLooper());
        //初始化堆栈采样器
        mStackSampler = new StackSampler(mLogHandler, mSampleInterval);
    }

    @Override
    public void println(String x) {
        //从if到else会执行 dispatchMessage, 如果执行耗时超过阈值，输出卡顿信息
        if (!mPrinteingStarted) {
            //记录开始时间
            mStartTimestamp = System.currentTimeMillis();
            mPrinteingStarted = true;
            //堆栈采样器开始采样
            mStackSampler.startDump();
        } else {
            final long endTime = System.currentTimeMillis();
            mPrinteingStarted = false;
            //出现卡顿
            if (isBlock(endTime)) {
                notifyBlockEvent(mStartTimestamp, endTime);
            }
            //堆栈采样器停止采样
            mStackSampler.stopDump();
        }
    }

    /**
     * 输入卡顿时间段的主线程堆栈信息
     *
     * @param startTime
     * @param endTime
     */
    private void notifyBlockEvent(final long startTime, final long endTime) {
        mLogHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show("主线程卡顿，请查看日志：TAG:" + TAG);
                //获得卡顿时间段主线程堆栈
                List<String> stacks = mStackSampler.getStacks(startTime, endTime);
                for (String stack : stacks) {
                    Log.e(TAG, stack);
                }
            }
        });
    }

    private boolean isBlock(long endTime) {
        return endTime - mStartTimestamp > mBlockThresholdMillis;
    }
}