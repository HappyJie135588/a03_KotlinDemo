package com.example.a03_kotlindemo.car

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.databinding.ActivitySecondDisplayBinding

class SecondDisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondDisplayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}