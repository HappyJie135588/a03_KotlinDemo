package com.example.a03_kotlindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.a03_kotlindemo.databinding.ActivityParabolaBinding;

public class ParabolaActivity extends AppCompatActivity {
    private ActivityParabolaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParabolaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivTest.animate().translationX(200);
            }
        });
    }
}