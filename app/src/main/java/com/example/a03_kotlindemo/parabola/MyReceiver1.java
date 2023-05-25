package com.example.a03_kotlindemo.parabola;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.a03_kotlindemo.utils.ToastUtils;

public class MyReceiver1 extends BroadcastReceiver {
    private static final String TAG = "MyReceiver1";
    public static final String ACTION = "com.example.a03_kotlindemo.parabola.intent.action.MyReceiver1";

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        Log.d(TAG, "onReceive: 广播1收到消息" + data);
        ToastUtils.show("onReceive: 广播1收到消息：" + data);
    }
}