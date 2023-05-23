package com.example.a03_kotlindemo.parabola;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a03_kotlindemo.utils.ToastUtils;

/**
 * 子线程Handler,参考HandlerThread;
 */
public class MyHandlerThread extends Thread {
    private static final String TAG = "MyHandlerThread";
    Looper mLooper;

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Looper.loop();
        Log.d(TAG, "run: 子线程Handler结束");
        ToastUtils.show("子线程Handler结束");
    }

    public Looper getmLooper() {
        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return mLooper;
    }
}

class XXX {
    public void xxx() {
        MyHandlerThread handlerThread = new MyHandlerThread();
        handlerThread.start();
        Handler myHandler = new Handler(handlerThread.getmLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2:
                        Log.d("XXX", "handleMessage: " + msg);
                        break;
                }
            }
        };
        Message message = Message.obtain();
        message.what = 2;
        myHandler.sendMessage(message);
    }
}