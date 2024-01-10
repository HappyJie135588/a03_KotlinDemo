package com.example.a03_kotlindemo.dongnanxueyuan

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.databinding.ActivityDongNaoXueYuanBinding
import kotlinx.coroutines.*//协程业务框架层
import kotlin.coroutines.*//协程基础设施层

class DongNaoXueYuanActivity : AppCompatActivity(),CoroutineScope by MainScope() {
    private val TAG: String = "DongNaoXueYuanActivity"
    private lateinit var binding: ActivityDongNaoXueYuanBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDongNaoXueYuanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView();
    }

    private fun initView() {
        //异步任务
        binding.btn1.setOnClickListener {
            object : AsyncTask<Void, Void, String>() {
                override fun doInBackground(vararg params: Void?): String {
                    return getXXX1()
                }

                override fun onPostExecute(result: String?) {
                    binding.tv1.text = result
                }
            }.execute()
        }
        //携程请求
        binding.btn2.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                binding.tv2.text = withContext(Dispatchers.IO) {
                    getXXX1()//Thread.sleep(12000)目前没发现两个有啥区别
                    getXXX2()//delay(12000)目前没发现两个有啥区别
                }
            }

        }
        //协程基础设施层
        binding.btn3.setOnClickListener {
            //协程体
            var continuation = suspend {//创建协程
                //耗时操作
                "sssss"
            }.createCoroutine(object : Continuation<String> {
                override val context: CoroutineContext = EmptyCoroutineContext

                override fun resumeWith(result: Result<String>) {//这里就是一个回调
                    Log.d(TAG, "resumeWith: ${Thread.currentThread().name}")
                    binding.tv3.text = result.getOrNull()
                }
            })
            //启动协程
            continuation.resume(Unit);
        }

        //携程作用域
        binding.btn4.setOnClickListener {
            val mainScope = MainScope()//大写开头的方法属于工厂模式
            mainScope.launch {
                binding.tv2.text = withContext(Dispatchers.IO) {
                    getXXX1()//Thread.sleep(12000)目前没发现两个有啥区别
                    getXXX2()//delay(12000)目前没发现两个有啥区别
                }
            }
            //mainScope.cancel()//携程可以取消

            //Activity:CoroutineScope by MainScope()//通过MainScope实现CoroutineScope接口
            launch {
                binding.tv2.text = withContext(Dispatchers.IO) {
                    getXXX1()//Thread.sleep(12000)目前没发现两个有啥区别
                    getXXX2()//delay(12000)目前没发现两个有啥区别
                }
            }
            //cancel()//cancel就可以直接调用
        }

    }

    private fun getXXX1(): String {
        Thread.sleep(12000)
        Log.d(TAG, "getXXX1: ${Thread.currentThread().name}")
        return "sssss"
    }

    private suspend fun getXXX2(): String {
        delay(12000)
        Log.d(TAG, "getXXX2: ${Thread.currentThread().name}")
        return "sssss"
    }
}