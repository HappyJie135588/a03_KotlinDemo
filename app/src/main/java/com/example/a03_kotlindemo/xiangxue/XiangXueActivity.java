package com.example.a03_kotlindemo.xiangxue;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityXiangXueBinding;

public class XiangXueActivity extends AppCompatActivity {
    private static final String TAG = "XiangXueActivity";
    private ActivityXiangXueBinding binding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityXiangXueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = this;
    }
}