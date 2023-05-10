package com.example.a03_kotlindemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.custom.CustomViewActivity
import com.example.a03_kotlindemo.databinding.ActivityMainBinding
import com.example.a03_kotlindemo.network.JavaNetWorkActivity
import com.example.a03_kotlindemo.rxjava.RxjavaActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnKotlin.setOnClickListener {
            startActivity(Intent(this, KotlinMainActivity::class.java))
        }
        binding.btnRxJava.setOnClickListener{
            startActivity(Intent(this,RxjavaActivity::class.java))
        }
        binding.btnJava.setOnClickListener {
            startActivity(Intent(this, JavaNetWorkActivity::class.java))
        }
        binding.btnParabola.setOnClickListener {
            startActivity(Intent(this, ParabolaActivity::class.java))
        }
        binding.btnCustomView.setOnClickListener {
            startActivity(Intent(this, CustomViewActivity::class.java))
        }
    }
}