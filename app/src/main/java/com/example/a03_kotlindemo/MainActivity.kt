package com.example.a03_kotlindemo

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.androidsimple.AndroidSimpleActivity
import com.example.a03_kotlindemo.car.CarActivity
import com.example.a03_kotlindemo.custom.CustomViewActivity
import com.example.a03_kotlindemo.databinding.ActivityMainBinding
import com.example.a03_kotlindemo.dongnanxueyuan.DongNaoXueYuanActivity
import com.example.a03_kotlindemo.kt.KotlinMainActivity
import com.example.a03_kotlindemo.maniu.MaNiuActivity
import com.example.a03_kotlindemo.network.JavaNetWorkActivity
import com.example.a03_kotlindemo.parabola.ParabolaActivity
import com.example.a03_kotlindemo.rxjava.RxjavaActivity
import com.example.a03_kotlindemo.xiangxue.XiangXueActivity

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var isFirst = true//用于判断是否首次加载
    override fun onCreate(savedInstanceState: Bundle?) {
        //由于黑白屏优化在Manifest中设置了windowBackground主题，这里重新设置回来
        setTheme(R.style.Theme_A03_KotlinDemo)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //可以用第三方AsyncLayoutInflater异步执行xml加载（不到万不得已，谨慎使用）
        setContentView(binding.root)
        initButton()
    }

    private fun initButton() {
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
        binding.btnMaNiu.setOnClickListener {
            startActivity(Intent(this, MaNiuActivity::class.java))
        }
        binding.btnXiangXue.setOnClickListener {
            startActivity(Intent(this, XiangXueActivity::class.java))
        }
        binding.btnDongNaoXueYuan.setOnClickListener {
            startActivity(Intent(this, DongNaoXueYuanActivity::class.java))
        }
    }

    //可以做延时操作，View绘制好之后调用，但是要判断是否首次执行
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (isFirst) {
            isFirst = false
            //检测方法的执行时间,停止方法追踪
            if (Config.IS_RECODE_LAUNCH_TIME) {
                Debug.stopMethodTracing()
                Log.d(TAG, "onCreate: 停止方法追踪")
            }
            Log.d(TAG, "onWindowFocusChanged: 执行延时操作")
            Thread.sleep(2000)
        }

    }
}