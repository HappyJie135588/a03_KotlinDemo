package com.example.a03_kotlindemo.maniu.blockcanary;

import android.os.Handler;
import android.os.Looper;

import com.github.moduth.blockcanary.internal.BlockInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 主线程堆栈采样器，在子线程中运行，不影响主线程业务
 */
public class StackSampler {
    //默认采样间隔
    private static final int DEFAULT_SAMPLE_INTERVAL = 300;
    //默认样品数
    private static final int DEFAULT_MAX_ENTRY_COUNT = 100;
    //子线程Handler
    private final Handler mLogHandler;
    //采样频率
    protected long mSampleInterval;
    //堆栈队列
    private final LinkedHashMap<Long, String> sStackMap;
    private int mMaxEntryCount = DEFAULT_MAX_ENTRY_COUNT;

    /**
     *
     * @param logHandler    子线程Handler
     * @param sampleInterval    采样频率
     */
    public StackSampler(Handler logHandler, long sampleInterval) {
        mLogHandler = logHandler;
        if (0 == sampleInterval) {
            sampleInterval = DEFAULT_SAMPLE_INTERVAL;
        }
        mSampleInterval = sampleInterval;
        //初始化为指定长度的1.4倍，防止扩容影响性能HashMap负载因子0.75
        sStackMap = new LinkedHashMap<>((int)(DEFAULT_MAX_ENTRY_COUNT*1.4));
    }

    /**
     * 子线程Handler循环采样
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder stringBuilder = new StringBuilder();
            //获取主线程堆栈信息
            for (StackTraceElement stackTraceElement : Looper.getMainLooper().getThread().getStackTrace()) {
                stringBuilder.append(stackTraceElement.toString())
                        .append(BlockInfo.SEPARATOR);
            }
            //同一个线程可以不加锁
//            synchronized (sStackMap) {
                //超过队列最大长度移除头部再添加
                if (sStackMap.size() == mMaxEntryCount && mMaxEntryCount > 0) {
                    sStackMap.remove(sStackMap.keySet().iterator().next());
                }
                sStackMap.put(System.currentTimeMillis(), stringBuilder.toString());
//            }
            //循环采样
            mLogHandler.postDelayed(mRunnable, mSampleInterval);
        }
    };

    /**
     * 开始采样
     */
    public void startDump() {
        mLogHandler.postDelayed(mRunnable,mSampleInterval);
    }

    /**
     * 停止采样
     */
    public void stopDump() {
        mLogHandler.removeCallbacks(mRunnable);
    }

    /**
     * 返回卡顿时间段主线程堆栈
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getStacks(long startTime, long endTime) {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
        //同一个线程可以不加锁，源代码此处是主线程调用所以要加锁
//        synchronized (sStackMap) {
            for (Long entryTime : sStackMap.keySet()) {
                if (startTime < entryTime && entryTime < endTime) {
//                    result.add(BlockInfo.TIME_FORMATTER.format(entryTime)
                    //妈的，BlockInfo.TIME_FORMATTER明明是静态居然返回null
                    result.add(sdf.format(entryTime)
                            + BlockInfo.SEPARATOR
                            + BlockInfo.SEPARATOR
                            + sStackMap.get(entryTime));
                }
            }
            return result;
//        }
    }
}
