package com.example.a03_kotlindemo.maniu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.TestActivity;
import com.example.a03_kotlindemo.databinding.ActivityMaNiuBinding;
import com.example.a03_kotlindemo.maniu.blockcanary.BlockCanary;
import com.example.a03_kotlindemo.maniu.choreographer.ChoreographerHelper;

import java.util.concurrent.locks.LockSupport;

public class MaNiuActivity extends AppCompatActivity {
    private ActivityMaNiuBinding binding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaNiuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = this;
        initPrinter();
        initCrash();
    }

    /**
     * 检测主线程卡顿测试代码
     */
    private void initPrinter() {
        //自定义Looper.Printer打印卡顿堆栈TAG:LogMonitor
        BlockCanary.install();
        //编舞者打印卡顿时长TAG:ChoreographerHelper
        ChoreographerHelper.start();
        binding.btnPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(mContext, TestActivity.class));
            }
        });
    }

    /**
     * 卡顿优化
     */
    private void initCrash() {
        binding.btnTestANR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockSupport.park();
            }
        });

    }
}