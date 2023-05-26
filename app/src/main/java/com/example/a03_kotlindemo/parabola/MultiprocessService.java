package com.example.a03_kotlindemo.parabola;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.a03_kotlindemo.utils.ToastUtils;

public class MultiprocessService extends Service {

    private static final String TAG = "MultiprocessService";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: 多进程服务启动");
        ToastUtils.show("onCreate: 多进程服务启动");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: 多进程服务再次启动");
        ToastUtils.show("onStartCommand: 多进程服务再次启动");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 多进程服务销毁");
        ToastUtils.show("onDestroy: 多进程服务销毁");
    }
}