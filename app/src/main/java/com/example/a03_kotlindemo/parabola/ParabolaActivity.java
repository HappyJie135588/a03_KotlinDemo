package com.example.a03_kotlindemo.parabola;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParabolaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = this;
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
                binding.tvCreateThread.setText("onCreate子线程更新Ui更新后");
            }
        }).start();
        initHandler();
        initBinder();
        initBroadcastReceiver();
        initMultiprocess();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //返回页面会抱错
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                binding.tvOnResumeThread.setText("onResume子线程更新Ui更新后");
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

    private MyReceiver1 myReceiver1 = null;

    private void initBroadcastReceiver() {
        binding.btnSendReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClass = new Intent(mContext, MyReceiver1.class);
                intentClass.putExtra("data", "发送显示广播");
                Intent intentAction = new Intent(MyReceiver1.ACTION);
                intentAction.putExtra("data", "发送隐示广播");
                sendBroadcast(intentAction);
            }
        });
        binding.btnRegistReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myReceiver1 == null) {
                    myReceiver1 = new MyReceiver1();
                    registerReceiver(myReceiver1, new IntentFilter(MyReceiver1.ACTION));
                }
            }
        });
        binding.btnUnRegistReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myReceiver1 != null) {
                    unregisterReceiver(myReceiver1);
                    myReceiver1 = null;
                }
            }
        });
        binding.btnSendOrderReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOrder = new Intent(MyReceiver2.ACTION);
                intentOrder.putExtra("data", "默认最新注册最先收到，除非设置priority");
                //由于Andrid8对隐式广播的限制，需要加上包名变成内部广播指定app接收
                intentOrder.setPackage("com.example.a03_kotlindemo");
//                sendBroadcast(intentOrder);//发送无序广播
                sendOrderedBroadcast(intentOrder, null);//发送有序广播才能被中断
            }
        });
    }

    private void initMultiprocess() {
        Intent multiprocessServiceIntent = new Intent(this, MultiprocessService.class);
        binding.btnMultiprocessServiceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(multiprocessServiceIntent);
            }
        });
        binding.btnMultiprocessServiceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(multiprocessServiceIntent);
            }
        });
        binding.btnMultiprocessActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MultiprocessActivity.class));
            }
        });
    }
}