package com.example.a03_kotlindemo.kt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a03_kotlindemo.databinding.ActivityKotlinMainBinding
import com.example.a03_kotlindemo.kt.fragment.Fragment1
import com.example.a03_kotlindemo.kt.fragment.Fragment2
import kotlinx.coroutines.*
import java.util.*

class KotlinMainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    companion object {
        private const val TAG = "KotlinMainActivity"
    }

    private lateinit var binding: ActivityKotlinMainBinding
    private lateinit var model: MainViewModel
    private lateinit var stack: Stack<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotlinMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycle.addObserver(MainLifecycleObserver())
        model = ViewModelProvider(this).get(MainViewModel::class.java)
        stack = Stack<Fragment>()
        initView()
    }

    private fun initView() {
        //延迟10秒本地请求
        binding.btn1.setOnClickListener {
            launch {
                var xxx = getXXX()
                println(Thread.currentThread().name + "OnClick()")
                binding.tv1.text = xxx
            }
        }
        //远程无效请求
        model.register.observe(this) {
            binding.tvViewModel.text = it.msg
        }
        binding.btnViewModel.setOnClickListener {
            model.toRegister("HappyJie135588")
        }
        //远程获取名言警句
        model.wisdom.observe(this) {
            binding.tvViewModel2.text = it.content
        }
        binding.btnViewModel2.setOnClickListener {
            model.getWisdom()
        }
        //被捕获的异常
        binding.btnException.setOnClickListener {
            val exHandler = CoroutineExceptionHandler { _, throwable ->
                Log.d(TAG, "CoroutineExceptionHandler: 异常被捕获$throwable")
                throwable.printStackTrace()
                binding.tvException.text = "异常被捕获$throwable"
            }
            launch(exHandler) {
                "".get(10)
            }
        }
        //没有被捕获的异常
        binding.btnException2.setOnClickListener {
            launch {
                "".get(10)
            }
        }

        binding.btnFragment1.setOnClickListener {
            replaceFragment(Fragment1())
        }
        binding.btnFragment2.setOnClickListener {
            replaceFragment(Fragment2())
        }
    }

    private suspend fun getXXX(): String {
        return withContext(Dispatchers.IO) {
            println(Thread.currentThread().name + "getXXX()")
            try {
                delay(10000)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            "sssss"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }


    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(binding.fragment2.id, fragment).commit()
        stack.push(fragment)
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.fragment2.id, fragment).commit()
        stack.clear()
        stack.push(fragment)
    }

    fun backFragment() {
        supportFragmentManager.beginTransaction().remove(stack.pop()).commit()
    }

    override fun onBackPressed() {
        if (stack.size > 1) {
            backFragment()
        } else {
            super.onBackPressed()
        }
    }
}