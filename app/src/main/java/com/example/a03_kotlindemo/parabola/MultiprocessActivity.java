package com.example.a03_kotlindemo.parabola;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.R;
import com.example.a03_kotlindemo.utils.ToastUtils;

public class MultiprocessActivity extends AppCompatActivity {

    private static final String TAG = "MultiprocessActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiprocess);
        Log.d(TAG, "onCreate: 多进程Activity创建成功");
        ToastUtils.show("onCreate: 多进程Activity创建成功");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 多进程Activity销毁");
        ToastUtils.show("onDestroy: 多进程Activity销毁");
    }
}