package com.example.a03_kotlindemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.a03_kotlindemo.utils.MyFileUtils;
import com.example.a03_kotlindemo.utils.ToastUtils;

import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class App extends Application {
    private static final String TAG = "APP";
    public static Handler mainHandler;
    public static Context context;
    private int i = 1;//用于验证多进程是不共享数据的

    private final static String PATH = "hj/cq";
    private final static String FILENAME = "hj_start" + System.currentTimeMillis() + ".trace";

    public App() {
        //检测方法的执行时间,开始方法追踪,会在指定目录输出.trace文件，使用Profiler分析
        //和直接使用Profiling启动类似
        if(Config.IS_RECODE_LAUNCH_TIME){
            Debug.startMethodTracing(MyFileUtils.getOuterPublicPath(PATH, FILENAME));
            Log.d(TAG, "App: 开始方法追踪");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainHandler = new Handler(Looper.getMainLooper());
        context = this;
        /**
         * RxJava取消订阅异常处理
         */
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                Log.i("TAG", "setErrorHandler accept: throwable=" + throwable.toString());
            }
        });

        int pid = android.os.Process.myPid();
        Log.e(TAG, "Application.onCreate ==== pid: " + pid);
        String processNameString = "";
        ActivityManager mActivityManager =
                (ActivityManager) this.getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            Log.e(TAG, "onCreate: appProcess.pid = " + appProcess.pid);
            if (appProcess.pid == pid) {
                processNameString = appProcess.processName;
            }
        }

        if ("com.example.a03_kotlindemo".equals(processNameString)) {
            Log.e(TAG, "onCreate: processName = " + processNameString + " ===work");
            ToastUtils.show("onCreate: processName = " + processNameString + " ===work" + i++);
        } else {
            Log.e(TAG, "onCreate: processName = " + processNameString + " ===work");
            ToastUtils.show("onCreate: processName = " + processNameString + " ===work" + i++);
        }
    }

    public static void post(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }
}