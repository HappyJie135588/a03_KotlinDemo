package com.example.a03_kotlindemo.custom;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a03_kotlindemo.databinding.ActivityCustomViewBinding;

public class CustomViewActivity extends AppCompatActivity {
    public static final String TAG = "CustomViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCustomViewBinding binding = ActivityCustomViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.slideView.setListener(new SlideView.OnActionClickListener() {
            @Override
            public void onTopClick() {
                Log.d(TAG, "onTopClick: ");
                Toast.makeText(CustomViewActivity.this, "onTopClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReadClick() {
                Log.d(TAG, "onReadClick: ");
                Toast.makeText(CustomViewActivity.this, "onReadClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick() {
                Log.d(TAG, "onDeleteClick: ");
                Toast.makeText(CustomViewActivity.this, "onDeleteClick", Toast.LENGTH_SHORT).show();

            }
        });
    }
}