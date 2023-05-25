package com.example.a03_kotlindemo.parabola;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityParabolaBinding;
import com.example.a03_kotlindemo.utils.ToastUtils;
import com.example.service.IAppServiceRemoteBinder;

public class ParabolaActivity extends AppCompatActivity {
    private static final String TAG = "ParabolaActivity";
    private ActivityParabolaBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParabolaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(TAG, "onCreate: 正常拿宽高" + binding.tvBinderGet.getWidth());
        binding.tvBinderGet.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onCreate: post拿宽高" + binding.tvBinderGet.getWidth());
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                binding.tvCreateThread.setText("onCreate子线程更新Ui");
            }
        }).start();
        initHandler();
        initBinder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //返回页面会保存
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                binding.tvOnResumeThread.setText("onResume子线程更新Ui");
//            }
//        }).start();
        Log.d(TAG, "onResume: 正常拿宽高" + binding.tvBinderGet.getWidth());
        binding.tvBinderGet.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onResume: post拿宽高" + binding.tvBinderGet.getWidth());
            }
        });
    }

    private void initHandler() {
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

    IAppServiceRemoteBinder myService;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = IAppServiceRemoteBinder.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            myService = null;
        }
    };

    private void initBinder() {
        intent = new Intent();
        intent.setAction("com.example.aidlserver");
        intent.setPackage("com.example.service");
        binding.btnBinderBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Bind_OnClick: ");
                bindService(intent, connection, BIND_AUTO_CREATE);

            }
        });
        binding.btnBinderUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Unbind_OnClick: ");
                unbindService(connection);
            }
        });
        binding.btnBinderGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Get_OnClick: ");
                if (myService != null) {
                    try {
                        String str = myService.getString();
                        Log.d(TAG, "getString: " + str);
                        if (!TextUtils.isEmpty(str)) {
                            binding.tvBinderGet.setText(str);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}