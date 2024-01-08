package com.example.a03_kotlindemo.dongnanxueyuan

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a03_kotlindemo.databinding.ActivityDongNaoXueYuanBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DongNaoXueYuanActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
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

        binding.btn2.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                binding.tv2.text = getXXX2()
            }
        }
    }

    private fun getXXX1(): String {
        Thread.sleep(2000)
        return "sssss"
    }

    private suspend fun getXXX2(): String = withContext(Dispatchers.IO) {
        Thread.sleep(2000)
        "sssss"
    }
}