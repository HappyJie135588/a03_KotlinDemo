package com.example.a03_kotlindemo;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityJavaMainBinding;
import com.example.a03_kotlindemo.model.PriseArticleData;
import com.example.a03_kotlindemo.network.okhttp.OkHelper;
import com.example.a03_kotlindemo.network.okhttp.OkListener;
import com.example.a03_kotlindemo.network.retrofit.exception.ApiException;
import com.example.a03_kotlindemo.network.retrofit.exception.ErrorConsumer;
import com.example.a03_kotlindemo.network.retrofit.RetrofitHelper;

import io.reactivex.rxjava3.functions.Consumer;

public class JavaMainActivity extends AppCompatActivity {

    private ActivityJavaMainBinding binding;
    private String TAG = "JavaMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHelper.getInstance().getJoke("2", new OkListener() {
                    @Override
                    protected void onSuccess(String str) {
                        binding.tvGet.setText(str);
                    }
                });
            }
        });
        binding.btnPostForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHelper.getInstance().postRegister("HappyJie135588", new OkListener() {
                    @Override
                    protected void onSuccess(String str) {
                        binding.tvPostForm.setText(str);
                    }
                });

            }
        });
        binding.btnPostJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PriseArticleData priseArticleData = new PriseArticleData("1403262826952323074", 2);
                OkHelper.getInstance().postPrise(priseArticleData, new OkListener() {
                    @Override
                    protected void onSuccess(String str) {
                        binding.tvPostJson.setText(str);
                    }
                });
            }
        });
        binding.btnPostFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHelper.getInstance().postFile("/sdcard/text1.png", new OkListener() {
                    @Override
                    protected void onSuccess(String str) {
                        binding.tvPostFile.setText(str);
                    }
                });
            }
        });
    }
}