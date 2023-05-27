package com.example.a03_kotlindemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.databinding.ActivityTestBinding
import com.example.a03_kotlindemo.utils.ToastUtils

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val test = intent.getStringExtra("test")
        if (!test.isNullOrBlank()) {
            binding.tvTest.text = test
            ToastUtils.show("onCreate: $test")
        }
    }
}