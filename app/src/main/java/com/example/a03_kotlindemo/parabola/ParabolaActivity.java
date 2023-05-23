package com.example.a03_kotlindemo.parabola;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityParabolaBinding;
import com.example.a03_kotlindemo.utils.ToastUtils;

public class ParabolaActivity extends AppCompatActivity {
    private static final String TAG = "ParabolaActivity";
    private ActivityParabolaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParabolaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.btnAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivTest.animate().translationX(200);
            }
        });
        MyHandlerThread myHandlerThread = new MyHandlerThread();
        myHandlerThread.start();
        Handler myHandler = new Handler(myHandlerThread.getmLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2:
                        Log.d(TAG, "MyHandlerThread: 子线程Handeler打印消息");
                        ToastUtils.show("MyHandlerThread子线程Handeler打印消息");
                        break;
                }
            }
        };
        binding.btnMyHandlerThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = 2;
                myHandler.sendMessage(message);
            }
        });
        binding.btnMyHandlerThreadStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHandler.getLooper().quitSafely();
            }
        });

        HandlerThread handlerThread = new HandlerThread("Thread1");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2:
                        Log.d(TAG, "HandlerThread: 子线程Handeler打印消息");
                        ToastUtils.show("HandlerThread子线程Handeler打印消息");
                        break;
                }
            }
        };
        binding.btnHandlerThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = 2;
                handler.sendMessage(message);
            }
        });
        binding.btnHandlerThreadStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.getLooper().quitSafely();
            }
        });
    }
}