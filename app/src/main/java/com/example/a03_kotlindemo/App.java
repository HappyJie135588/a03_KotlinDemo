package com.example.a03_kotlindemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
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
        if (Config.IS_RECODE_LAUNCH_TIME) {
            Debug.startMethodTracing(MyFileUtils.getOuterPublicPath(PATH, FILENAME));
            Log.d(TAG, "App: 开始方法追踪");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainHandler = new Handler(Looper.getMainLooper());
        context = this;

        //RxJava取消订阅异常处理
        initRxjava();
        //多进程判断是哪条进程
        initMultiProcess();
        //严格模式
        initStrictMode();
    }

    /**
     * RxJava取消订阅异常处理
     */
    private void initRxjava() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                Log.i("TAG", "setErrorHandler accept: throwable=" + throwable.toString());
            }
        });
    }

    /**
     * 多进程判断是哪条进程
     */
    private void initMultiProcess() {
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

    /**
     * 设置严格模式检测优化点,真的很严格
     */
    private void initStrictMode() {
        if (Config.IS_STRICT_MODE) {
            //线程检测策略
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()//检测磁盘读操作
                    .detectDiskWrites()//检测磁盘写操作
                    .detectNetwork()
                    .penaltyLog()//违规则打印日志
                    .penaltyFlashScreen()//违规则闪屏
//                    .penaltyDeath()//违规则崩溃
                    .build());
            //虚拟机检测策略
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()//Sqlite对象泄漏
                    .detectLeakedClosableObjects()//未关闭的Closable对象泄漏
                    .penaltyLog()//违规则打印日志
//                    .penaltyDeath()//违规则崩溃
                    .build());
        }
    }

    /**
     * 回到主线程执行
     * @param runnable
     */
    public static void post(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }
}