package com.example.a03_kotlindemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.androidsimple.AndroidSimpleActivity
import com.example.a03_kotlindemo.car.CarActivity
import com.example.a03_kotlindemo.custom.CustomViewActivity
import com.example.a03_kotlindemo.databinding.ActivityMainBinding
import com.example.a03_kotlindemo.kt.KotlinMainActivity
import com.example.a03_kotlindemo.maniu.MaNiuActivity
import com.example.a03_kotlindemo.network.JavaNetWorkActivity
import com.example.a03_kotlindemo.parabola.ParabolaActivity
import com.example.a03_kotlindemo.rxjava.RxjavaActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //由于黑白屏优化在Manifest中设置了windowBackground主题，这里重新设置回来
        setTheme(R.style.Theme_A03_KotlinDemo)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAndroidSimple.setOnClickListener {
            startActivity(Intent(this, AndroidSimpleActivity::class.java))
        }
        binding.btnKotlin.setOnClickListener {
            startActivity(Intent(this, KotlinMainActivity::class.java))
        }
        binding.btnRxJava.setOnClickListener {
            startActivity(Intent(this, RxjavaActivity::class.java))
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
        binding.btnCar.setOnClickListener {
            startActivity(Intent(this, CarActivity::class.java))
        }
        binding.btnManiu.setOnClickListener {
            startActivity(Intent(this, MaNiuActivity::class.java))
        }
    }
}