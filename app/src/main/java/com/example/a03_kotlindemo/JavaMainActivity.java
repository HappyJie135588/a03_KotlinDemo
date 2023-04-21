package com.example.a03_kotlindemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityJavaMainBinding;

public class JavaMainActivity extends AppCompatActivity {

    private ActivityJavaMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}