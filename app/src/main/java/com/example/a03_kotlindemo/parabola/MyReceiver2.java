package com.example.a03_kotlindemo.parabola;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.a03_kotlindemo.utils.ToastUtils;

public class MyReceiver2 extends BroadcastReceiver {
    private static final String TAG = "MyReceiver2";
    public static final String ACTION = "com.example.a03_kotlindemo.parabola.intent.action.MyReceiver2";
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        Log.d(TAG, "onReceive: 广播2收到消息并中断"+data);
        ToastUtils.show("onReceive: 广播2收到消息并中断："+data);
        abortBroadcast();//中断广播，需要发有序广播：sendOrderedBroadcast
    }
}