package com.example.a03_kotlindemo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class App extends Application {
    public static Handler mainHandler;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        mainHandler = new Handler(Looper.getMainLooper());
        context = this;
    }

    public static void post(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }
}